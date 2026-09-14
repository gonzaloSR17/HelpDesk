package com.arelance.helpdesk.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Long>{

}
