package com.arelance.helpdesk.dto;

import java.time.LocalDateTime;

import com.arelance.helpdesk.modelo.Ticket;

public record TransicionTicketDto(Integer idTransicion, Ticket.Estado estado, LocalDateTime fechaCambio, Integer idTicket) {

}
