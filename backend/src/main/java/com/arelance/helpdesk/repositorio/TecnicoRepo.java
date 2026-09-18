package com.arelance.helpdesk.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Tecnico;

public interface TecnicoRepo extends JpaRepository<Tecnico, Long> {

}
