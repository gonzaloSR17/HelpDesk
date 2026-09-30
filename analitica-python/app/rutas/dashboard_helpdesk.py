"""
Dashboard HelpDesk: llama al backend Spring Boot y reproduce
los dos gráficos de la captura (línea semanal + donut por categoría).

Requisitos:
    pip install requests plotly

Antes de ejecutar:
    - El backend debe estar corriendo en localhost:8080.
    - Necesitas un token JWT con rol ADMINISTRADOR (pégalo abajo en TOKEN,
      o mejor, cárgalo desde una variable de entorno).
"""

# * Importamos la graficas (plotly) y el servivio http (request)
import requests
import plotly.graph_objects as go
from plotly.subplots import make_subplots

# *  No tenemos proxy, asi que apuntamos al link donde tenemos los endpoint
BASE_URL = "http://localhost:8080"

# Pega aquí tu token de prueba (* tiene 1 hora de caducidad)
# *  Creamos otra variable y asignamos dicho token
TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbjEiLCJyb2wiOiJBRE1JTklTVFJBRE9SIiwiaWF0IjoxNzkwMDg5MjM3LCJleHAiOjE3OTAwOTI4Mzd9.SHSGQvsQkwb69y_RuRJQ2x63t9TSIGnvutonlb7PCzzcesWVTQhTZ7D5DW_8yuUGntO53IJ5B4kKC4xRBDjxQyKMHRQJjfFUwZiO5JfOiX3XJMC1H3uV811CbDWv99An60mtVlAzIJzEM4azhCE0kJ73kQgOcjSW2fz43Y919-Qcm3TBFzEbeu-k5sgswda_Gg1KPEYKQuquHuu8MtK-BA0yaAl8qpkxWws35dc7e5ADaDQliBv-8PnfZxB4wrtnkMSX7ySHkQil-ecAGGaBZFrUf1I_KWWF5ih2wHvxBUGjgB0O158Qhw1fkzYoFIWGkhxzHMq00ZUQ0NrzL5e9UA"

HEADERS = {"Authorization": f"Bearer {TOKEN}"}

# Las 4 categorías tal como están en categoria.grupo en la BD.
# Si alguna falla (404/500), revisa el valor exacto en tu tabla `categoria`.
CATEGORIAS = ["REDES", "SOFTWARE", "HARDWARE", "ACCESOS"]


# Funcion que llama al endpoint con request.get hacemos la consulta
# resp.raise_for_status() -> comprueba si la respuesta HTTP fue correcta. El programa para si recibe un 404 
def obtener_weekly_trend() -> dict:
    """GET /api/v1/metrics/weekly-trend -> {"days": [...], "open": [...], "resolved": [...]}"""
    resp = requests.get(f"{BASE_URL}/api/v1/metrics/weekly-trend", headers=HEADERS)
    resp.raise_for_status()
    return resp.json()


def obtener_conteo_categorias() -> dict:
    """
    Llama a GET /api/v1/tickets/contar/{categoria} una vez por cada categoría.
    Devuelve algo como {-FTWARE": 132, "HARDWARE": 96, "ACCESOS": 74}
    """
    conteos = {}
    for categoria in CATEGORIAS:
        resp = requests.get(f"{BASE_URL}/api/v1/tickets/contar/{categoria}", headers=HEADERS)
        resp.raise_for_status()
        conteos[categoria] = resp.json()  # el endpoint devuelve un Long "pelado"
    return conteos


def construir_dashboard(trend: dict, categorias: dict) -> go.Figure:
    """Arma la misma disposición de la captura: línea a la izquierda, donut a la derecha."""

    fig = make_subplots(
        rows=1, cols=2,
        specs=[[{"type": "xy"}, {"type": "domain"}]],
        subplot_titles=("Tickets abiertos vs. resueltos", "Reparto por categoría"),
    )

    # --- Gráfico de líneas (izquierda) ---
    fig.add_trace(
        go.Scatter(
            x=trend["days"], y=trend["open"],
            mode="lines+markers", name="Abiertos",
            line=dict(color="#2f6fed"),
        ),
        row=1, col=1,
    )
    fig.add_trace(
        go.Scatter(
            x=trend["days"], y=trend["resolved"],
            mode="lines+markers", name="Resueltos",
            line=dict(color="#1f9d55"),
        ),
        row=1, col=1,
    )

    # --- Donut (derecha) ---
    total = sum(categorias.values())
    fig.add_trace(
        go.Pie(
            labels=[f"{k.capitalize()} ({v})" for k, v in categorias.items()],
            values=list(categorias.values()),
            hole=0.6,
            marker=dict(colors=["#2f6fed", "#1f9d55", "#f5a623", "#7b4fd6"]),
        ),
        row=1, col=2,
    )

    fig.update_layout(
        title_text="Dashboard HelpDesk",
        annotations=[
            dict(text=f"<b>{total}</b><br>Total", x=0.82, y=0.5,
                 font_size=18, showarrow=False)
        ],
        showlegend=True,
    )

    return fig

# Ciclo de vida de este programa

if __name__ == "__main__":
    trend = obtener_weekly_trend()
    print("Weekly trend:", trend)

    categorias = obtener_conteo_categorias()
    print("Categorías:", categorias)

    fig = construir_dashboard(trend, categorias)
    fig.write_html("dashboard_helpdesk.html", auto_open=True)
    print("Listo: se generó dashboard_helpdesk.html en esta misma carpeta.")