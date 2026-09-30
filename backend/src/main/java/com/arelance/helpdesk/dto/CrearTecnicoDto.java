package com.arelance.helpdesk.dto;

/**
 * Datos del formulario "Nuevo técnico" (pantalla de admin) - Rubén
 * La contraseña llega en claro y se guarda hasheada con BCrypt.
 */
public record CrearTecnicoDto(
        String username,
        String password,
        String nombre,
        String apellido,
        String email,
        String especialidad,
        Boolean disponible) {
}
