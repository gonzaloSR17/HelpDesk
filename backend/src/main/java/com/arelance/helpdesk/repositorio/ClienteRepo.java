package com.arelance.helpdesk.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Categoria;
import com.arelance.helpdesk.modelo.Cliente;

public interface ClienteRepo extends JpaRepository<Cliente, Long> {

}
