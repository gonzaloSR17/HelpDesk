package com.arelance.helpdesk.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arelance.helpdesk.modelo.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Long> {

    long countByEstadoNotIn(List<Ticket.Estado> estados);

    // Servicios para filtrar las primeras 8 paginas en el panel principal
    Page<Ticket> findByEstadoNotOrderByFechaAperturaDesc(
            Ticket.Estado estado,
            Pageable pageable
    );

    // Servicio para imprimir los ticket de un cliente determinado
    Page<Ticket> findByClienteIdOrderByFechaAperturaDesc(
            Long clienteId,
            Pageable pageable
    );

    // Servicio para contar los tickets creados en el día de hoy
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

    long countByEstadoInAndFechaCierreBetween(
            List<Ticket.Estado> estados,
            LocalDateTime desde,
            LocalDateTime hasta
    );

    List<Ticket> findByEstadoInAndFechaCierreBetween(
            List<Ticket.Estado> estados,
            LocalDateTime desde,
            LocalDateTime hasta
    );

    // --- Rubén (Analysis) ------------------------------------
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

    // Busca tickets filtrando por estado y prioridad.
    // Si alguno llega vacío (null), simplemente no filtra por ese campo.
    @Query("""
        SELECT t FROM Ticket t WHERE
        (:estado IS NULL OR t.estado = :estado) AND
        (:prioridad IS NULL OR t.prioridad = :prioridad)
        """)
    Page<Ticket> filtrar(
            @Param("estado") Ticket.Estado estado,
            @Param("prioridad") Ticket.Prioridad prioridad,
            Pageable pageable
    );
}