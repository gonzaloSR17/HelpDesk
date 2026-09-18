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

import com.arelance.helpdesk.modelo.Categoria;
import com.arelance.helpdesk.modelo.Cliente;
import com.arelance.helpdesk.modelo.Comentario;
import com.arelance.helpdesk.modelo.Contrato;
import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.repositorio.CategoriaRepo;
import com.arelance.helpdesk.repositorio.ClienteRepo;
import com.arelance.helpdesk.repositorio.ComentarioRepo;
import com.arelance.helpdesk.repositorio.TecnicoRepo;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Definimos que es un controlador + ruta
@RestController
@RequestMapping("/api/comentario")
public class ComentarioController {

    private final ComentarioRepo comentarioRepo;

    public ComentarioController(ComentarioRepo comentarioRepo) {
        this.comentarioRepo = comentarioRepo;
    }

    @PostMapping("/crear")
    public ResponseEntity<List<Comentario>> crearActividad(@RequestBody List<Comentario> a) {
        //TODO: process POST request
        
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioRepo.saveAll(a));
    };
}
