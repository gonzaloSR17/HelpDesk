package com.arelance.helpdesk.dto;

import java.time.LocalDate;

import com.arelance.helpdesk.modelo.Contrato;

public record ContratoDto(Integer idContrato, String codigo, Contrato.Nivel nivel, LocalDate fechaInicio, LocalDate fechaFin, Integer idCliente, SlaDto sla) {

}
