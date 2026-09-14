package com.arelance.helpdesk.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Comentario;

public interface ComentarioRepo extends JpaRepository<Comentario, Long> {

}
