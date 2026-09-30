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
import com.arelance.helpdesk.modelo.TransicionTicket;
import com.arelance.helpdesk.repositorio.TecnicoRepo;
import com.arelance.helpdesk.repositorio.TicketRepo;
import com.arelance.helpdesk.repositorio.TransicionTicketRepo;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Definimos que es un controlador + ruta
@RestController
@RequestMapping("/api/transicionticket")
public class TransicionTicketController {

    private final TransicionTicketRepo transicionTicketRepo;

    public TransicionTicketController(TransicionTicketRepo transicionTicketRepo) {
        this.transicionTicketRepo = transicionTicketRepo;
    }

    // Endpoint para devolver el historial de un ticket especifico 
    @GetMapping("/devolver/{id}")
    public ResponseEntity<List<TransicionTicket>> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transicionTicketRepo.findByTicket_IdTicketOrderByFechaCambioAsc(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<List<TransicionTicket>> crearActividad(@RequestBody List<TransicionTicket> a) {
        //TODO: process POST request
        
        return ResponseEntity.status(HttpStatus.CREATED).body(transicionTicketRepo.saveAll(a));
    };

    @PostMapping("/pasar")
    public ResponseEntity<TransicionTicket> pasarTransiccion(@RequestBody TransicionTicket a) {
         // Creamos nuevo objeto ticker, no añadimos fecha, se agregara automaticamente,l
        TransicionTicket transicion = new TransicionTicket();
        transicion.setEstado(a.getEstado());
        transicion.setAsunto(a.getAsunto());
        transicion.setTicket(a.getTicket());
        return ResponseEntity.status(HttpStatus.CREATED).body(transicionTicketRepo.save(a));
    }
}
