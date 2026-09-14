package com.arelance.helpdesk.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Categoria;

public interface CategoriaRepo  extends JpaRepository<Categoria, Long>{

}
