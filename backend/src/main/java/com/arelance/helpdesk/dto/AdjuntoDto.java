package com.arelance.helpdesk.dto;

import java.time.LocalDateTime;

/**
 * Metadatos del adjunto. El campo "contenido" (LONGBLOB) queda fuera a
 * proposito: mandarlo en cada listado inflaria el JSON sin necesidad.
 * Para descargarlo, un endpoint dedicado (ej. GET /api/adjuntos/{id}/contenido)
 * devuelve el binario directamente con su tipoMime como Content-Type.
 */

public record AdjuntoDto(Long id, String nombreArchivo, String tipoMime, LocalDateTime fechaSubida, Integer idTicket) {

}
