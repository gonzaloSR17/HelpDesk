package com.arelance.helpdesk.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.arelance.helpdesk.modelo.Tecnico;

public interface TecnicoRepo extends JpaRepository<Tecnico, Long> {

    // Repo para buscar especialidad
    // Si quieres buscar por coincidencia parcial (LIKE %especialidad%):
    List<Tecnico> findByEspecialidadContainingIgnoreCase(String especialidad);

    // Búsqueda paginada por nombre (listado de técnicos del admin) - Rubén
    // Busca en "nombre apellido", así "ana gar" encuentra a Ana García.
    @Query("""
            SELECT t FROM Tecnico t
            WHERE LOWER(CONCAT(t.nombre, ' ', COALESCE(t.apellido, ''))) LIKE LOWER(CONCAT('%', :nombre, '%'))
            """)
    Page<Tecnico> buscarPorNombre(@Param("nombre") String nombre, Pageable pageable);
}
