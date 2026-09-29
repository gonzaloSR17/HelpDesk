package com.arelance.helpdesk.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Categoria;

public interface CategoriaRepo  extends JpaRepository<Categoria, Long>{

    List<Categoria> findAllByOrderByGrupoAscNombreAsc();

}
