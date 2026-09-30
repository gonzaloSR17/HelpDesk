"""
Endpoints que devuelven las figuras de Plotly en JSON.

Cada endpoint recibe el token JWT del usuario (el mismo que ya usa Angular)
en la cabecera Authorization, y lo reenvía al backend Spring Boot para
consultar los datos, antes de armar la figura.
"""

from fastapi import APIRouter, Header, HTTPException
import requests

from app.graficas.tendencia import construir_figura_tendencia
from app.graficas.categorias import construir_figura_categorias

router = APIRouter()

SPRING_BOOT_URL = "http://localhost:8080"


def _headers_reenvio(authorization: str | None) -> dict:
    """Arma las cabeceras para reenviar la petición al backend Java."""
    if not authorization:
        raise HTTPException(status_code=401, detail="Falta el token de autorización")
    return {"Authorization": authorization}


@router.get("/tendencia-semanal")
def tendencia_semanal(authorization: str | None = Header(default=None)):
    """
    Llama a GET /api/v1/metrics/weekly-trend en Spring Boot y devuelve
    la figura de línea (abiertos vs. resueltos) como JSON de Plotly.
    """
    resp = requests.get(
        f"{SPRING_BOOT_URL}/api/v1/metrics/weekly-trend",
        headers=_headers_reenvio(authorization),
    )
    if resp.status_code != 200:
        raise HTTPException(status_code=resp.status_code, detail=resp.text)

    datos = resp.json()
    figura = construir_figura_tendencia(datos)
    return figura.to_plotly_json()


@router.get("/categorias")
def categorias(authorization: str | None = Header(default=None)):
    """
    Llama a GET /api/v1/tickets/count/categorias en Spring Boot y devuelve
    la figura de donut (reparto por categoría) como JSON de Plotly.
    """
    resp = requests.get(
        f"{SPRING_BOOT_URL}/api/v1/tickets/count/categorias",
        headers=_headers_reenvio(authorization),
    )
    if resp.status_code != 200:
        raise HTTPException(status_code=resp.status_code, detail=resp.text)

    datos = resp.json()
    figura = construir_figura_categorias(datos)
    return figura.to_plotly_json()
