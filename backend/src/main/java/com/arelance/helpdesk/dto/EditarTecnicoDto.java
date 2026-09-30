package com.arelance.helpdesk.dto;

/**
 * Campos editables de un técnico. El id y los datos personales (username,
 * nombre, apellido, email) NO se pueden cambiar desde el formulario de
 * edición: en el front van en inputs disabled y aquí ni siquiera se reciben - Rubén
 */
public record EditarTecnicoDto(
        String especialidad,
        Boolean disponible,
        Boolean activo) {
}
