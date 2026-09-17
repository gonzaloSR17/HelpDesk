package com.arelance.helpdesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta de GET /api/v1/metrics/count/categories-total.
 * Contrato exigido: { total_categories: 450 }
 */
public record TotalCategoriasDto(
        @JsonProperty("total_categories") long totalCategories
) {
}
