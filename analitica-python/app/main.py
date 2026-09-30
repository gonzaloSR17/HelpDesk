"""
Microservicio de analítica para HelpDesk.

Arma las figuras de Plotly (consultando al backend Spring Boot) y las
expone como JSON para que Angular las pinte con plotly.js.

Correr con:
    uvicorn app.main:app --reload --port 5000
"""

from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from app.rutas.graficas_routes import router as graficas_router

app = FastAPI(title="HelpDesk Analítica")

# CORS: solo hace falta si llamas a este servicio directo desde el navegador
# (sin pasar por el proxy de Angular). Si usas el proxy.conf.json, esto es
# una capa extra de seguridad de todas formas.
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:4200"],
    allow_credentials=True,
    allow_methods=["GET"],
    allow_headers=["Authorization", "Content-Type"],
)

app.include_router(graficas_router, prefix="/graficas", tags=["graficas"])


@app.get("/health")
def health():
    """Endpoint simple para comprobar que el servicio está vivo."""
    return {"status": "ok"}
