package com.arelance.helpdesk.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Ticket;
import com.arelance.helpdesk.modelo.TransicionTicket;

public interface TransicionTicketRepo extends JpaRepository<TransicionTicket, Long> {

    List<TransicionTicket> findByTicket_IdTicketOrderByFechaCambioAsc(Long ticketId);

}
