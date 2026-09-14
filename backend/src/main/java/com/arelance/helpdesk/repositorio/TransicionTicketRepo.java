package com.arelance.helpdesk.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.TransicionTicket;

public interface TransicionTicketRepo extends JpaRepository<TransicionTicket, Long> {

}
