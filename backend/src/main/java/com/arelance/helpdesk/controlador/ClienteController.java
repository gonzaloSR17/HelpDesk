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
import com.arelance.helpdesk.modelo.Cliente;
import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.repositorio.CategoriaRepo;
import com.arelance.helpdesk.repositorio.ClienteRepo;
import com.arelance.helpdesk.repositorio.TecnicoRepo;
import com.arelance.helpdesk.servicios.ClienteServices;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Definimos que es un controlador + ruta
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteRepo clienterepo;
    private final ClienteServices clienteServices;

    public ClienteController(ClienteRepo clienterepo, ClienteServices clienteServices) {
        this.clienterepo = clienterepo;
        this.clienteServices = clienteServices;
    }

    @PostMapping("/crear")
    public ResponseEntity<List<Cliente>> crearActividad(@RequestBody List<Cliente> a) {
        //TODO: process POST request
        
        return ResponseEntity.status(HttpStatus.CREATED).body(clienterepo.saveAll(a));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Cliente>> obtenerCliente(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienterepo.findById(id));
    }

    @GetMapping("/consultar")
    @Operation(summary = "Consulta una cantidad de cliente", description = "Devuelve una lista limitada de clientes registrados en la base de datos")
    public ResponseEntity<Page<Cliente>> consultarCliente(@RequestParam(defaultValue = "0") int pagina) {
        return ResponseEntity.ok(clienteServices.obtenerClientes(pagina));
    }
}