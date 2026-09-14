package com.arelance.helpdesk.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Adjunto;

public interface AdjuntoRepo  extends JpaRepository<Adjunto, Long> {

}
