package com.arelance.helpdesk.controlador;


import java.math.BigDecimal;
import java.util.List;

import org.aspectj.internal.lang.annotation.ajcITD;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.modelo.Ticket;
import com.arelance.helpdesk.repositorio.TecnicoRepo;
import com.arelance.helpdesk.repositorio.TicketRepo;
import com.arelance.helpdesk.servicios.TicketServices;

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
    private final TicketServices ticketServices;

    

    public TicketController(TicketRepo ticketRepo, TicketServices ticketServices) {
        this.ticketRepo = ticketRepo;
        this.ticketServices = ticketServices;
    }

    @PostMapping("/crear")
    public ResponseEntity<List<Ticket>> crearActividad(@RequestBody List<Ticket> a) {
        //TODO: process POST request
        
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketRepo.saveAll(a));
    };

    @GetMapping("/consultar")
    @Operation(summary = "Consulta una cantidad de ticket", description = "Devuelve una lista limitada de tickets registrados en la base de datos")
    public ResponseEntity<Page<Ticket>>  consultarTicket(
        @RequestParam(defaultValue = "0") int pagina
    ) {
        // Devulevo la primera pagina 0 para que me imprima los 4 primeros resultados
        return ResponseEntity.ok(ticketServices.obtenerTickets(pagina));
    }

    @GetMapping("/contar/hoy")
    // @Operation(summary = "Consulta una cantidad de ticket", description = "Devuelve una lista limitada de tickets registrados en la base de datos")
    public ResponseEntity<Long>  contarTicketDeHoy(@RequestParam("estado") Ticket.Estado estado) {
        return ResponseEntity.ok(ticketRepo.recuentoTicketHoy(estado));
    }

    @GetMapping("/contar/{categoria}")
    // @Operation(summary = "Consulta una cantidad de ticket", description = "Devuelve una lista limitada de tickets registrados en la base de datos")
    public ResponseEntity<Long>  contarTicketCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(ticketRepo.recuentoTicketTipo(categoria));
    }

    

}
