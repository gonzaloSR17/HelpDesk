package com.arelance.helpdesk.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.arelance.helpdesk.modelo.Cliente;
import com.arelance.helpdesk.repositorio.ClienteRepo;
import com.arelance.helpdesk.repositorio.TicketRepo;

@Service
public class ClienteServices {

    @Autowired 
    private ClienteRepo cr;

    public Page<Cliente> obtenerClientes(int pagina) {
        Pageable pageable = PageRequest.of(pagina, 8);

        return cr.findAll(pageable);
    }
}
