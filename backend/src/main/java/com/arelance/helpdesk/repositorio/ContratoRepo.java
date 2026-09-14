package com.arelance.helpdesk.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Contrato;

public interface ContratoRepo extends JpaRepository<Contrato, Long> {

}
