package com.arelance.helpdesk.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.SLA;
import com.arelance.helpdesk.modelo.Ticket;

public interface SlaRepo extends JpaRepository<SLA, Long> {
    Optional<SLA> findByPrioridad(Ticket.Prioridad prioridad);
}
