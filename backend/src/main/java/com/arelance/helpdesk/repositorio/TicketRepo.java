package com.arelance.helpdesk.repositorio;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arelance.helpdesk.modelo.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Long>{


    // Servicios para filtrar las primeras 8 paginas en el panel principal
    Page<Ticket> findByEstadoNotOrderByFechaAperturaDesc(
        Ticket.Estado estado,
        Pageable pageable
    );

    // Servicio para contar los tickets creado en el dia de hoy
    @Query("""
        SELECT COUNT(t)
        FROM Ticket t
        WHERE FUNCTION('DATE', t.fechaApertura) = CURRENT_DATE
        AND t.estado = :estado
        """)
    Long recuentoTicketHoy(@Param("estado") Ticket.Estado estado);
    
    @Query("""
            SELECT COUNT(t)
            FROM Ticket t
            WHERE t.categoria.grupo = :categoria
            """)
    Long recuentoTicketTipo(@Param("categoria") String categoria);

}
