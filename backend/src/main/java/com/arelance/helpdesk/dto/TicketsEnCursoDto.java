package com.arelance.helpdesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta de GET /api/v1/metrics/in-progress.
 * Contrato exigido: { in_progress: 94 }
 */
public record TicketsEnCursoDto(
        @JsonProperty("in_progress") long enCurso
) {
}
