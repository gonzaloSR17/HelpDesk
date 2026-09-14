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

import com.arelance.helpdesk.modelo.Adjunto;
import com.arelance.helpdesk.modelo.SLA;
import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.repositorio.AdjuntoRepo;
import com.arelance.helpdesk.repositorio.TecnicoRepo;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Definimos que es un controlador + ruta
@RestController
@RequestMapping("/api/adjunto")
public class AdjuntoController {

    private final AdjuntoRepo adjuRepo;

    public AdjuntoController(AdjuntoRepo adjuRepo) {
        this.adjuRepo = adjuRepo;
    }

    @PostMapping("/crear")
    public ResponseEntity<List<Adjunto>> crearActividad(@RequestBody List<Adjunto> a) {
        //TODO: process POST request
        
        return ResponseEntity.status(HttpStatus.CREATED).body(adjuRepo.saveAll(a));
    };
    

}
