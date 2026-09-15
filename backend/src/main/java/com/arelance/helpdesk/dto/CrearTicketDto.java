package com.arelance.helpdesk.dto;

import java.time.LocalDateTime;

import com.arelance.helpdesk.modelo.Ticket;

/**
 * Payload de entrada para POST /api/v1/tickets (botón "+ Nuevo").
 * El estado no se recibe desde el cliente: todo ticket nuevo nace en EN_ABIERTO.
 */
public record CrearTicketDto(
        String asunto,
        String descripcion,
        Ticket.Canal canal,
        Ticket.Prioridad prioridad,
        LocalDateTime fechaApertura,
        Long idCliente,
        Long idTecnico,
        Long idCategoria,
        Long idContrato
) {
}
