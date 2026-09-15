package com.arelance.helpdesk.dto;

import java.util.List;

/**
 * Respuesta de GET /api/v1/tickets/search?q={query}.
 * Contrato exigido: { results: [...] }
 */
public record ResultadosBusquedaDto(
        List<TicketBusquedaDto> results
) {
}
