package com.arelance.helpdesk.servicios;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.arelance.helpdesk.dto.CrearTecnicoDto;
import com.arelance.helpdesk.dto.EditarTecnicoDto;
import com.arelance.helpdesk.dto.TecnicoDetalleDto;
import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.repositorio.TecnicoRepo;
import com.arelance.helpdesk.repositorio.UsuarioRepo;

/**
 * Lógica del listado de técnicos del admin: consultar (paginado + búsqueda),
 * ver, crear, editar y eliminar - Rubén
 *
 * Los métodos son @Transactional porque el DTO cuenta los tickets asignados
 * (relación LAZY) y con open-in-view=false la sesión se cierra al salir del servicio.
 */
@Service
public class TecnicoServices {

    // Mismo tamaño de página que el listado de clientes
    private static final int TAMANO_PAGINA = 8;

    private final TecnicoRepo tecnicoRepo;
    private final UsuarioRepo usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public TecnicoServices(TecnicoRepo tecnicoRepo, UsuarioRepo usuarioRepo, PasswordEncoder passwordEncoder) {
        this.tecnicoRepo = tecnicoRepo;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<TecnicoDetalleDto> obtenerTecnicos(int pagina, String nombre) {
        Pageable pageable = PageRequest.of(Math.max(pagina, 0), TAMANO_PAGINA, Sort.by("id"));

        Page<Tecnico> resultado = (nombre == null || nombre.isBlank())
                ? tecnicoRepo.findAll(pageable)
                : tecnicoRepo.buscarPorNombre(nombre.trim(), pageable);

        return resultado.map(TecnicoDetalleDto::desde);
    }

    @Transactional(readOnly = true)
    public TecnicoDetalleDto obtenerTecnico(Long id) {
        return TecnicoDetalleDto.desde(buscarOFallar(id));
    }

    @Transactional
    public TecnicoDetalleDto crearTecnico(CrearTecnicoDto datos) {
        exigir(datos.username(), "El usuario es obligatorio");
        exigir(datos.password(), "La contraseña es obligatoria");
        exigir(datos.nombre(), "El nombre es obligatorio");
        exigir(datos.apellido(), "El apellido es obligatorio");
        exigir(datos.email(), "El email es obligatorio");
        exigir(datos.especialidad(), "La especialidad es obligatoria");

        if (datos.password().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña debe tener al menos 6 caracteres");
        }
        if (usuarioRepo.existsByUsername(datos.username().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese nombre de usuario");
        }
        if (usuarioRepo.existsByEmailIgnoreCase(datos.email().trim())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }

        Tecnico t = new Tecnico();
        t.setUsername(datos.username().trim());
        t.setPasswordHash(passwordEncoder.encode(datos.password()));
        t.setNombre(datos.nombre().trim());
        t.setApellido(datos.apellido().trim());
        t.setEmail(datos.email().trim());
        t.setEspecialidad(datos.especialidad().trim());
        t.setDisponible(datos.disponible() == null || datos.disponible());
        t.setActivo(true);
        t.setFechaAlta(LocalDateTime.now());

        return TecnicoDetalleDto.desde(tecnicoRepo.save(t));
    }

    @Transactional
    public TecnicoDetalleDto editarTecnico(Long id, EditarTecnicoDto datos) {
        Tecnico t = buscarOFallar(id);

        // Solo se tocan los campos editables; id y datos personales no cambian
        if (datos.especialidad() != null) {
            exigir(datos.especialidad(), "La especialidad no puede estar vacía");
            t.setEspecialidad(datos.especialidad().trim());
        }
        if (datos.disponible() != null) {
            t.setDisponible(datos.disponible());
        }
        if (datos.activo() != null) {
            t.setActivo(datos.activo());
        }

        return TecnicoDetalleDto.desde(tecnicoRepo.save(t));
    }

    @Transactional
    public void eliminarTecnico(Long id) {
        Tecnico t = buscarOFallar(id);

        // Si tiene tickets, borrarlo rompería la clave foránea de ticket.id_tecnico
        // (y perderíamos el histórico). En ese caso mejor desactivarlo desde Editar.
        int tickets = t.getTickets().size();
        if (tickets > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar: el técnico tiene " + tickets
                            + " ticket(s) asignado(s). Desactívalo desde Editar.");
        }

        tecnicoRepo.delete(t);
    }

    private Tecnico buscarOFallar(Long id) {
        return tecnicoRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Técnico no encontrado"));
    }

    private static void exigir(String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje);
        }
    }
}
