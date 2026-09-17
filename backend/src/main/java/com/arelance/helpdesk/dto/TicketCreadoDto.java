package com.arelance.helpdesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta de POST /api/v1/tickets.
 * Contrato exigido: { status: 'created', ticket_id: string }
 */
public record TicketCreadoDto(
        String status,
        @JsonProperty("ticket_id") String ticketId
) {
}
