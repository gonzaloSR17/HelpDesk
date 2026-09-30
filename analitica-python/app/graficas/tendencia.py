import plotly.graph_objects as go


def construir_figura_tendencia(datos: dict) -> go.Figure:
    """
    datos viene del backend con la forma:
        { "days": [...], "open": [...], "resolved": [...] }
    """
    fig = go.Figure()

    fig.add_trace(go.Scatter(
        x=datos["days"], y=datos["open"],
        mode="lines+markers", name="Abiertos",
        line=dict(color="#2f6fed"),
    ))
    fig.add_trace(go.Scatter(
        x=datos["days"], y=datos["resolved"],
        mode="lines+markers", name="Resueltos",
        line=dict(color="#1f9d55"),
    ))

    fig.update_layout(
        margin=dict(l=30, r=20, t=20, b=30),
        legend=dict(orientation="h", yanchor="bottom", y=1.02, xanchor="right", x=1),
    )
    return fig
