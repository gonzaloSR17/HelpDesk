package com.arelance.helpdesk.dto;

import java.time.LocalDateTime;

import com.arelance.helpdesk.modelo.Ticket;

public record TicketDto(Integer idTicket, String asunto, String descripcion, Ticket.Canal canal, Ticket.Estado estado, Ticket.Prioridad prioridad,
    LocalDateTime fechaApertura, LocalDateTime fechaCierre, Integer idCliente, Integer idTecnico, Integer idCategoria, Integer idContrato
) {

}
