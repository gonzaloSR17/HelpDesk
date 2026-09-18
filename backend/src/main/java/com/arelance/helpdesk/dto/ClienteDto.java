package com.arelance.helpdesk.dto;

public record ClienteDto(Integer idCliente,
        String nombre, String apellido, String cif, String sector, String contacto, String email) {

}
