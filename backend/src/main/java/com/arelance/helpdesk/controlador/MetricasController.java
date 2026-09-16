package com.arelance.helpdesk.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arelance.helpdesk.dto.TotalCategoriasDto;
import com.arelance.helpdesk.modelo.SLA;
import com.arelance.helpdesk.modelo.Ticket;
import com.arelance.helpdesk.repositorio.SlaRepo;
import com.arelance.helpdesk.repositorio.TicketRepo;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

// imports nuevos - jhon
import com.arelance.helpdesk.dto.TicketsEnCursoDto;
import com.arelance.helpdesk.dto.SlaRiskDto;

import com.arelance.helpdesk.dto.WeeklyTrendDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Endpoints de MÉTRICAS del equipo:
 * http://localhost:8080/api/v1/metrics
 * - Si añades un endpoint aquí, indica tu nombre y qué hace en un comentario.
 * - GET /open, /resolved-month, /sla-percentage → Oscar (Architect)
 * - GET /count/categories-total → Rubén (Analysis)
 * - GET /in-progress, /sla-risk, /weekly-trend → Jhon (Backend)
 */

@RestController
@RequestMapping("/api/v1/metrics")
public class MetricasController {

    private final TicketRepo ticketRepo;
    private final SlaRepo slaRepo;

    public MetricasController(TicketRepo ticketRepo, SlaRepo slaRepo) {
        this.ticketRepo = ticketRepo;
        this.slaRepo = slaRepo;
    }

    // Rutas corregidas
    // GET R02 → /resolved-month

    @GetMapping("/resolved-month")
    @Operation(summary = "Total de tickets resueltos en el mes actual (KPI directo).")
    public TicketsResueltosMes resueltosMes() {
        List<Ticket.Estado> cerrados = List.of(Ticket.Estado.RESUELTO, Ticket.Estado.CERRADO);

        YearMonth mesActual = YearMonth.now();
        LocalDateTime inicioMes = mesActual.atDay(1).atStartOfDay();
        LocalDateTime ahora = LocalDateTime.now();

        long total = ticketRepo.countByEstadoInAndFechaCierreBetween(cerrados, inicioMes, ahora);

        return new TicketsResueltosMes(total);
    }

    // GET R03 → /api/v1/metrics/sla-percentage
    @GetMapping("/sla-percentage")
    @Operation(summary = " Porcentaje global de cumplimiento del SLA mensual (KPI directo).")
    public PorcentajeSla porcentajeSla() {
        List<Ticket.Estado> cerrados = List.of(Ticket.Estado.RESUELTO, Ticket.Estado.CERRADO);

        YearMonth mesActual = YearMonth.now();
        LocalDateTime inicioMes = mesActual.atDay(1).atStartOfDay();
        LocalDateTime ahora = LocalDateTime.now();

        // Aquí sí necesitamos los tickets completos (no solo el conteo), porque hay
        // que comparar la fecha de cada uno contra el SLA de su prioridad
        List<Ticket> tickets = ticketRepo.findByEstadoInAndFechaCierreBetween(cerrados, inicioMes, ahora);

        // Si no hay tickets resueltos este mes, no hay nada que se incumpla
        if (tickets.isEmpty()) {
            return new PorcentajeSla(100.0);
        }

        int totalTickets = 0;
        int ticketsDentroDePlazo = 0;

        // Recorremos ticket a ticket para compararlo contra el SLA de SU prioridad
        for (Ticket t : tickets) {

            // Si falta alguna de las dos fechas, no se puede calcular cuánto tardó
            if (t.getFechaApertura() == null || t.getFechaCierre() == null) {
                continue;
            }

            // Buscamos el SLA que corresponde a la prioridad de este ticket
            SLA sla = slaRepo.findByPrioridad(t.getPrioridad()).orElse(null);

            // Si no hay SLA definido para esa prioridad, no podemos evaluarlo
            if (sla == null) {
                continue;
            }

            // Minutos que tardó en resolverse: desde que se abrió hasta que se cerró
            long minutosQueTardo = ChronoUnit.MINUTES.between(t.getFechaApertura(), t.getFechaCierre());

            totalTickets++;

            // ¿Tardó menos (o igual) que el objetivo de resolución de su prioridad?
            if (minutosQueTardo <= sla.getTiempoResolucionObjetivoMin()) {
                ticketsDentroDePlazo++;
            }
        }

        // Si ningún ticket se pudo evaluar (todos sin fecha o sin SLA), evitamos
        // dividir por 0
        if (totalTickets == 0) {
            return new PorcentajeSla(100.0);
        }

        double porcentaje = (ticketsDentroDePlazo * 100.0) / totalTickets;

        return new PorcentajeSla(porcentaje);
    }

    // GET R01 → /api/v1/metrics/open
    @GetMapping("/open")
    @Operation(summary = " Métrica de tickets abiertos actualmente en el sistema (KPI directo).")
    public TicketsAbiertos abierto() {
        List<Ticket.Estado> cerrados = List.of(Ticket.Estado.RESUELTO, Ticket.Estado.CERRADO);
        long abiertos = ticketRepo.countByEstadoNotIn(cerrados);

        return new TicketsAbiertos(abiertos);
    }

    /**
     * Cuenta los tickets que están actualmente en curso (EN_CURSO),
     * es decir, ya asignados a un técnico y siendo trabajados.
     *
     * @return el total de tickets en estado EN_CURSO
     */
    // GET /api/v1/metrics/in-progress → Jhon (Backend)
    @GetMapping("/in-progress")
    @Operation(summary = "Tickets en curso asignados a técnicos (KPI directo).")
    public ResponseEntity<TicketsEnCursoDto> enCurso() {
        long total = ticketRepo.countByEstado(Ticket.Estado.EN_CURSO);
        return ResponseEntity.ok(new TicketsEnCursoDto(total));
    }

    /**
     * Cuenta los tickets abiertos que están a punto de incumplir su SLA
     * (menos de 2 horas restantes, pero todavía sin incumplir).
     *
     * @return el total de tickets en riesgo de SLA
     */
    // GET /api/v1/metrics/sla-risk → Jhon (Backend)
    @GetMapping("/sla-risk")
    @Operation(summary = "Tickets con menos de 2 horas restantes para incumplir su SLA (KPI directo).")
    public ResponseEntity<SlaRiskDto> slaRisk() {
        List<Ticket.Estado> cerrados = List.of(Ticket.Estado.RESUELTO, Ticket.Estado.CERRADO);
        List<Ticket> tickets = ticketRepo.findByEstadoNotIn(cerrados);

        LocalDateTime ahora = LocalDateTime.now();
        long enRiesgo = 0;

        for (Ticket t : tickets) {
            if (t.getFechaApertura() == null) {
                continue;
            }

            SLA sla = slaRepo.findByPrioridad(t.getPrioridad()).orElse(null);
            if (sla == null) {
                continue;
            }

            long minutosTranscurridos = ChronoUnit.MINUTES.between(t.getFechaApertura(), ahora);
            long minutosRestantes = sla.getTiempoResolucionObjetivoMin() - minutosTranscurridos;

            if (minutosRestantes > 0 && minutosRestantes < 120) {
                enRiesgo++;
            }
        }

        return ResponseEntity.ok(new SlaRiskDto(enRiesgo));
    }

    /**
     * Convierte el resultado en bruto de una consulta SQL agrupada por día
     * (fecha + total) en un mapa fácil de consultar (fecha -> total).
     * Usado por weekly-trend - Jhon (Backend)
     */
    private Map<LocalDate, Long> aMapaPorDia(List<Object[]> filas) {
        Map<LocalDate, Long> mapa = new HashMap<>();
        for (Object[] fila : filas) {
            LocalDate dia = (LocalDate) fila[0];
            long total = ((Number) fila[1]).longValue();
            mapa.put(dia, total);
        }
        return mapa;
    }






    private static final String[] NOMBRES_DIA = { "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom" };

    /**
     * Tendencia semanal: tickets abiertos vs resueltos por día,
     * en los últimos 7 días (hoy incluido).
     *
     * @return los 7 días con su conteo de abiertos y resueltos
     */
    // GET /api/v1/metrics/weekly-trend → Jhon (Backend)
    @GetMapping("/weekly-trend")
    @Operation(summary = "Tendencia semanal: tickets abiertos vs resueltos por día (últimos 7 días).")
    public ResponseEntity<WeeklyTrendDto> weeklyTrend() {
        LocalDate hoy = LocalDate.now();
        LocalDate desde = hoy.minusDays(6);

        Map<LocalDate, Long> abiertosPorDia = aMapaPorDia(
                ticketRepo.contarAbiertosPorDia(desde.atStartOfDay()));
        Map<LocalDate, Long> resueltosPorDia = aMapaPorDia(
                ticketRepo.contarResueltosPorDia(desde.atStartOfDay()));

        List<String> dias = new ArrayList<>();
        List<Long> abiertos = new ArrayList<>();
        List<Long> resueltos = new ArrayList<>();

        for (LocalDate dia = desde; !dia.isAfter(hoy); dia = dia.plusDays(1)) {
            int indiceDiaSemana = dia.getDayOfWeek().getValue() - 1; // Lunes=1 -> índice 0
            dias.add(NOMBRES_DIA[indiceDiaSemana]);
            abiertos.add(abiertosPorDia.getOrDefault(dia, 0L));
            resueltos.add(resueltosPorDia.getOrDefault(dia, 0L));
        }

        return ResponseEntity.ok(new WeeklyTrendDto(dias, abiertos, resueltos));
    }





    public record TicketsAbiertos(@JsonProperty("open_tickets") long abiertos) {
    }

    public record TicketsResueltosMes(@JsonProperty("resolved_month") long resueltos) {
    }

    public record PorcentajeSla(@JsonProperty("sla_compliance_pct") double porcentaje) {
    }

    /**
     * Suma total acumulada de tickets repartidos por categoría.
     * Migrado desde MetricsController (Rubén, Analysis).
     *
     * @return el total de tickets que tienen categoría asignada
     */
    @Operation(summary = "Suma total acumulada de tickets repartidos por categoría")
    @GetMapping("/count/categories-total")
    public ResponseEntity<TotalCategoriasDto> totalPorCategorias() {
        long total = ticketRepo.countByCategoriaIsNotNull();
        return ResponseEntity.ok(new TotalCategoriasDto(total));
    }

}