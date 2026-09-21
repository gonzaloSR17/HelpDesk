package com.arelance.helpdesk.dto;

import java.util.List;

/**
 * Respuesta de GET /api/v1/metrics/weekly-trend.
 * Contrato exigido: { days: [...], open: [...], resolved: [...] } - Jhon (Backend)
 *
 * @param days     etiquetas de los últimos 7 días (ej. "Lun", "Mar"...)
 * @param open     tickets abiertos ese día, en el mismo orden que days
 * @param resolved tickets resueltos/cerrados ese día, en el mismo orden que days
 */
public record WeeklyTrendDto(
        List<String> days,
        List<Long> open,
        List<Long> resolved
) {
}
