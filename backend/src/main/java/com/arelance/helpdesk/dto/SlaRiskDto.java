package com.arelance.helpdesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Respuesta de GET /api/v1/metrics/sla-risk.
 * Contrato exigido: { sla_risk_count: 17 } - Jhon (Backend)
 *
 * @param count total de tickets con menos de 2 horas restantes de SLA
 */
public record SlaRiskDto(
        @JsonProperty("sla_risk_count") long count
) {
}
