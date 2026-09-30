package com.arelance.helpdesk.dto;

import java.time.LocalDateTime;

import com.arelance.helpdesk.modelo.Tecnico;

/**
 * Lo que el backend devuelve de un técnico en la pantalla de admin.
 * No incluye el passwordHash (por eso no devolvemos la entidad directamente) - Rubén
 */
public record TecnicoDetalleDto(
        Long id,
        String username,
        String nombre,
        String apellido,
        String email,
        String especialidad,
        boolean disponible,
        boolean activo,
        LocalDateTime fechaAlta,
        int ticketsAsignados) {

    public static TecnicoDetalleDto desde(Tecnico t) {
        return new TecnicoDetalleDto(
                t.getId(),
                t.getUsername(),
                t.getNombre(),
                t.getApellido(),
                t.getEmail(),
                t.getEspecialidad(),
                t.isDisponible(),
                t.isActivo(),
                t.getFechaAlta(),
                t.getTickets() == null ? 0 : t.getTickets().size());
    }
}
