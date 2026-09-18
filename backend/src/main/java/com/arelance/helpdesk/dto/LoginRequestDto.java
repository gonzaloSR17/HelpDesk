package com.arelance.helpdesk.dto;

/**
 * Lo que manda el cliente (Angular) para hacer login - Jhon (Backend)
 */
public record LoginRequestDto(String username, String password) {
}
