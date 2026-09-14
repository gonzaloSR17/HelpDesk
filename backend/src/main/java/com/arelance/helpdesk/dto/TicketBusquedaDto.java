package com.arelance.helpdesk.dto;

import java.time.LocalDateTime;

/**
 * Representación resumida de un ticket dentro de los resultados de
 * GET /api/v1/tickets/search?q={query}.
 */
public record TicketBusquedaDto(
        Long idTicket,
        String asunto,
        String estado,
        String prioridad,
        String cliente,
        String categoria,
        String tecnico,
        String codigoContrato,
        LocalDateTime fechaApertura
) {
}
