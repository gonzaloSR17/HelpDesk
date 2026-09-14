package com.arelance.helpdesk.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arelance.helpdesk.modelo.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Long> {

    // --- Rubén (Analysis) ---------------------------------------------
    // GET /api/v1/tickets/search?q={query}
    // Búsqueda global por palabra clave: asunto, descripción, cliente,
    // categoría, técnico, código de contrato o id del propio ticket.
    @Query("""
            SELECT t FROM Ticket t
            LEFT JOIN t.cliente c
            LEFT JOIN t.categoria cat
            LEFT JOIN t.tecnico tec
            LEFT JOIN t.contrato con
            WHERE LOWER(t.asunto) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(t.descripcion) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(cat.nombre) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(tec.nombre) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(con.codigo) LIKE LOWER(CONCAT('%', :q, '%'))
               OR CAST(t.idTicket AS string) LIKE CONCAT('%', :q, '%')
            ORDER BY t.fechaApertura DESC
            """)
    List<Ticket> buscarPorPalabraClave(@Param("q") String q);

    // GET /api/v1/metrics/count/categories-total
    // Suma total acumulada de tickets repartidos por categoría.
    long countByCategoriaIsNotNull();

}
