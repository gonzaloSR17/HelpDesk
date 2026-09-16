package com.arelance.helpdesk.dto;

/**
 * Lo que devuelve el backend tras un login correcto - Jhon (Backend)
 */
public record LoginResponseDto(String token, String rol, String nombre) {
}
