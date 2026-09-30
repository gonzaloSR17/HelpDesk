package com.arelance.helpdesk.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Tecnico;

public interface TecnicoRepo extends JpaRepository<Tecnico, Long> {

    // Repo para buscar especialidad
    // Si quieres buscar por coincidencia parcial (LIKE %especialidad%):
    List<Tecnico> findByEspecialidadContainingIgnoreCase(String especialidad);

}
