package com.arelance.helpdesk.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arelance.helpdesk.modelo.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Long>{
    long countByEstadoNotIn(List<Ticket.Estado> estados);

    long countByEstadoInAndFechaCierreBetween(List<Ticket.Estado> estados, LocalDateTime desde, LocalDateTime hasta);

    List<Ticket> findByEstadoInAndFechaCierreBetween(List<Ticket.Estado> estados, LocalDateTime desde, LocalDateTime hasta);

    // Busca tickets filtrando por estado y prioridad, pero si alguno de los
    // dos llega vacío (null), simplemente no filtra por ese campo.
    // Pageable trae la página y el tamaño desde el controlador.
    @Query("SELECT t FROM Ticket t WHERE " +
           "(:estado IS NULL OR t.estado = :estado) AND " +
           "(:prioridad IS NULL OR t.prioridad = :prioridad)")
    Page<Ticket> filtrar(@Param("estado") Ticket.Estado estado,
                          @Param("prioridad") Ticket.Prioridad prioridad,
                          Pageable pageable);
    
}
