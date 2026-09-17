package com.arelance.helpdesk.servicios;


import javax.management.monitor.Monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.arelance.helpdesk.modelo.Ticket;
import com.arelance.helpdesk.repositorio.TicketRepo;

@Service
public class TicketServices {

    @Autowired 
    private TicketRepo tr;

    public Page<Ticket> obtenerTickets(int pagina) {
        Pageable pageable = PageRequest.of(pagina, 8);

        return tr.findByEstadoNotOrderByFechaAperturaDesc(
            Ticket.Estado.CERRADO,
            pageable
        );
    }
}
