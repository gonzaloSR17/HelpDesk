package com.arelance.helpdesk.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Long>{
    long countByEstadoNotIn(List<Ticket.Estado> estados);

    long countByEstadoInAndFechaCierreBetween(List<Ticket.Estado> estados, LocalDateTime desde, LocalDateTime hasta);

    List<Ticket> findByEstadoInAndFechaCierreBetween(List<Ticket.Estado> estados, LocalDateTime desde, LocalDateTime hasta);
}
