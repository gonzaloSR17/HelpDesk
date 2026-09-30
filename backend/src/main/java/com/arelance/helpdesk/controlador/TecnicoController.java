package com.arelance.helpdesk.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.arelance.helpdesk.dto.CrearTecnicoDto;
import com.arelance.helpdesk.dto.EditarTecnicoDto;
import com.arelance.helpdesk.dto.TecnicoDetalleDto;
import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.repositorio.TecnicoRepo;
import com.arelance.helpdesk.servicios.TecnicoServices;

import io.swagger.v3.oas.annotations.Operation;

// Definimos que es un controlador + ruta
// Todo /api/tecnico/** es solo para ADMINISTRADOR (ver SecurityConfig)
@RestController
@RequestMapping("/api/tecnico")
public class TecnicoController {

    private final TecnicoRepo tecnicoRepo;
    private final TecnicoServices tecnicoServices;

    public TecnicoController(TecnicoRepo tecnicoRepo, TecnicoServices tecnicoServices) {
        this.tecnicoRepo = tecnicoRepo;
        this.tecnicoServices = tecnicoServices;
    }

    // Carga masiva (plantilla original, se mantiene para cargar datos por Swagger)
    @PostMapping("/crear")
    public ResponseEntity<List<Tecnico>> crearActividad(@RequestBody List<Tecnico> a) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoRepo.saveAll(a));
    }

    // Endpoint para devolver a todlos los tecnicos de una especialidad
    @GetMapping("/devolver/{especialidad}")
    public ResponseEntity<List<Tecnico>> getTecnicoEspecialidad(@PathVariable String especialidad) {
        return ResponseEntity.ok(tecnicoRepo.findByEspecialidadContainingIgnoreCase(especialidad));
    }

    // ═══════════════════════════════════════════════════════════
    //  Listado de técnicos del admin - Rubén
    // ═══════════════════════════════════════════════════════════

    @GetMapping("/consultar")
    @Operation(summary = "Listado paginado de técnicos",
            description = "8 técnicos por página. 'nombre' (opcional) filtra por nombre y apellido.")
    public ResponseEntity<Page<TecnicoDetalleDto>> consultarTecnicos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(required = false) String nombre) {
        return ResponseEntity.ok(tecnicoServices.obtenerTecnicos(pagina, nombre));
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Ver los detalles de un técnico")
    public ResponseEntity<TecnicoDetalleDto> obtenerTecnico(@PathVariable Long id) {
        return ResponseEntity.ok(tecnicoServices.obtenerTecnico(id));
    }

    @PostMapping("/nuevo")
    @Operation(summary = "Crear un técnico", description = "La contraseña se guarda hasheada con BCrypt.")
    public ResponseEntity<TecnicoDetalleDto> crearTecnico(@RequestBody CrearTecnicoDto datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoServices.crearTecnico(datos));
    }

    @PutMapping("/editar/{id}")
    @Operation(summary = "Editar un técnico",
            description = "Solo cambia especialidad, disponible y activo. El id y los datos personales no se editan.")
    public ResponseEntity<TecnicoDetalleDto> editarTecnico(@PathVariable Long id, @RequestBody EditarTecnicoDto datos) {
        return ResponseEntity.ok(tecnicoServices.editarTecnico(id, datos));
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un técnico", description = "Devuelve 409 si tiene tickets asignados.")
    public ResponseEntity<Void> eliminarTecnico(@PathVariable Long id) {
        tecnicoServices.eliminarTecnico(id);
        return ResponseEntity.noContent().build();
    }

    // Devuelve el motivo del error en el cuerpo ({ "mensaje": "..." }) para que
    // Angular lo pueda enseñar (por defecto Spring Boot lo oculta)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> manejarError(ResponseStatusException e) {
        String mensaje = e.getReason() != null ? e.getReason() : "Error inesperado";
        return ResponseEntity.status(e.getStatusCode()).body(Map.of("mensaje", mensaje));
    }
}
