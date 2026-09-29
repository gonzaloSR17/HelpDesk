package com.arelance.helpdesk.controlador;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.aspectj.internal.lang.annotation.ajcITD;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arelance.helpdesk.modelo.Categoria;
import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.modelo.Ticket;
import com.arelance.helpdesk.repositorio.CategoriaRepo;
import com.arelance.helpdesk.repositorio.TecnicoRepo;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Definimos que es un controlador + ruta
@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    private final CategoriaRepo categoriaRepo;

    public CategoriaController(CategoriaRepo categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    @PostMapping("/crear")
    public ResponseEntity<List<Categoria>> crearActividad(@RequestBody List<Categoria> a) {
        //TODO: process POST request
        
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepo.saveAll(a));
    };

    @GetMapping("/consultar")
    @Operation(summary = "Busca todos las listas", description = "Devuelve una lista limitada de categorias registrados en la base de datos")
    public ResponseEntity<List<Categoria>> consultarCategoria() {
        return ResponseEntity.ok(categoriaRepo.findAllByOrderByGrupoAscNombreAsc());
    }



    

}
