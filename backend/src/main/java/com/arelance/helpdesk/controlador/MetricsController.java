package com.arelance.helpdesk.controlador;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arelance.helpdesk.repositorio.TicketRepo;

// Controlador compartido de métricas del panel (/api/v1/metrics/...).
// Cada endpoint de este grupo lo implementa un miembro distinto del equipo
// (ver carpeta pdf-tareas/): aquí solo está el de Rubén (Analysis).
// Jhon, Oscar y Gonzalo pueden añadir sus @GetMapping en esta misma clase.
@RestController
@RequestMapping("/api/v1/metrics")
public class MetricsController {

    private final TicketRepo ticketRepo;

    public MetricsController(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    // ================================================================
    // GET /api/v1/metrics/count/categories-total
    // Suma total acumulada de tickets repartidos por categoría
    // Retorno esperado: { total_categories: 450 }
    // ================================================================
    @GetMapping("/count/categories-total")
    public ResponseEntity<Map<String, Object>> totalPorCategorias() {
        long total = ticketRepo.countByCategoriaIsNotNull();
        return ResponseEntity.ok(Map.of("total_categories", total));
    }
}
