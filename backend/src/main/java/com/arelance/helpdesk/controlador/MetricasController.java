package com.arelance.helpdesk.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Endpoints de MÉTRICAS (encargo de Oscar - Architect):
 * http://localhost:8080/api/v1/metrics
 * - GET R01 → /api/v1/metrics/open → Tickets abiertos ahora mismo
 * - GET R02 → /api/v1/metrics/resolved-month → Tickets resueltos este mes
 * - GET R03 → /api/v1/metrics/sla-percentage → % de cumplimiento de SLA
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

    // GET R02 → /api/v1/metrics/resolved-month
    @GetMapping("/api/v1/metrics/resolved-month")
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
    @GetMapping(" /api/v1/metrics/sla-percentage")
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

        // Si ningún ticket se pudo evaluar (todos sin fecha o sin SLA), evitamos dividir por 0
        if (totalTickets == 0) {
            return new PorcentajeSla(100.0);
        }

        double porcentaje = (ticketsDentroDePlazo * 100.0) / totalTickets;

        return new PorcentajeSla(porcentaje);
    }

    // GET R01 → /api/v1/metrics/open
    @GetMapping("/api/v1/metrics/open")
    @Operation(summary = " Métrica de tickets abiertos actualmente en el sistema (KPI directo).")
    public TicketsAbiertos abierto() {
        List<Ticket.Estado> cerrados = List.of(Ticket.Estado.RESUELTO, Ticket.Estado.CERRADO);
        long abiertos = ticketRepo.countByEstadoNotIn(cerrados);

        return new TicketsAbiertos(abiertos);
    }


    public record TicketsAbiertos(@JsonProperty("open_tickets") long abiertos) {
    }

    public record TicketsResueltosMes(@JsonProperty("resolved_month") long resueltos) {
    }

    public record PorcentajeSla(@JsonProperty("sla_compliance_pct") double porcentaje) {
    }

}