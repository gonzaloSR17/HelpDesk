package com.arelance.helpdesk.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.SLA;

public interface SlaRepo extends JpaRepository<SLA, Long> {

}
