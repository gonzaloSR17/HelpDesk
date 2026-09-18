package com.arelance.helpdesk.dto;

import java.time.LocalDateTime;

public record ComentarioDto(Integer idComentario, String texto, String autor, LocalDateTime fecha, Integer idTicket) {

}
