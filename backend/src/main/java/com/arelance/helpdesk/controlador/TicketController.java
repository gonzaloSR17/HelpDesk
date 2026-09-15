package com.arelance.helpdesk.controlador;

import java.math.BigDecimal;
import java.util.List;

import org.aspectj.internal.lang.annotation.ajcITD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.modelo.Ticket;
import com.arelance.helpdesk.repositorio.TecnicoRepo;
import com.arelance.helpdesk.repositorio.TicketRepo;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Definimos que es un controlador + ruta
@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    private final TicketRepo ticketRepo;

    public TicketController(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    @PostMapping("/crear")
    public ResponseEntity<List<Ticket>> crearActividad(@RequestBody List<Ticket> a) {
        //TODO: process POST request
        
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketRepo.saveAll(a));
    };

    @GetMapping("/consultar")
    public Page<Ticket> consultarPagina(
            @RequestParam(required = false) Ticket.Estado estado,
            @RequestParam(required = false) Ticket.Prioridad prioridad,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return ticketRepo.filtrar(estado, prioridad, PageRequest.of(page, size));
    }
    

}
