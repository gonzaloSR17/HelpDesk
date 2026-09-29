import plotly.graph_objects as go

COLORES = {
    "RED": "#2f6fed",
    "SOFTWARE": "#1f9d55",
    "HARDWARE": "#f5a623",
    "ACCESOS": "#7b4fd6",
}


def construir_figura_categorias(datos: dict) -> go.Figure:
    """
    datos viene del backend con la forma:
        { "REDES": 5, "SOFTWARE": 5, "HARDWARE": 6, "ACCESOS": 5 }
    """
    etiquetas = list(datos.keys())
    valores = list(datos.values())
    colores = [COLORES.get(e, "#999999") for e in etiquetas]

    fig = go.Figure(data=[go.Pie(
        labels=etiquetas,
        values=valores,
        hole=0.6,
        marker=dict(colors=colores),
    )])

    total = sum(valores)
    fig.update_layout(
        margin=dict(l=20, r=20, t=20, b=20),
        annotations=[dict(text=f"<b>{total}</b><br>Total", x=0.5, y=0.5,
                           font_size=18, showarrow=False)],
    )
    return fig
