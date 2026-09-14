package com.arelance.helpdesk.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arelance.helpdesk.dto.TotalCategoriasDto;
import com.arelance.helpdesk.repositorio.TicketRepo;

import io.swagger.v3.oas.annotations.Operation;

/**
 * NOTA: este controlador solo incluye el endpoint asignado a Rubén
 * (Analysis). Los demás endpoints bajo /api/v1/metrics/... (weekly-trend,
 * in-progress, sla-risk, resolved-month, sla-percentage, open,
 * count/red, count/hardware, count/software, count/accesos) están
 * asignados a Jhon, Oscar y Gonzalo respectivamente y no se tocan aquí.
 */
@RestController
@RequestMapping("/api/v1/metrics")
public class MetricsController {

    private final TicketRepo ticketRepo;

    public MetricsController(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @Operation(summary = "Suma total acumulada de tickets repartidos por categoría")
    @GetMapping("/count/categories-total")
    public ResponseEntity<TotalCategoriasDto> totalPorCategorias() {
        long total = ticketRepo.countByCategoriaIsNotNull();
        return ResponseEntity.ok(new TotalCategoriasDto(total));
    }

}
