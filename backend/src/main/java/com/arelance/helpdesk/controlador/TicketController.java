package com.arelance.helpdesk.controlador;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arelance.helpdesk.dto.CrearTicketDto;
import com.arelance.helpdesk.dto.ResultadosBusquedaDto;
import com.arelance.helpdesk.dto.TicketBusquedaDto;
import com.arelance.helpdesk.dto.TicketCreadoDto;
import com.arelance.helpdesk.modelo.Categoria;
import com.arelance.helpdesk.modelo.Cliente;
import com.arelance.helpdesk.modelo.Contrato;
import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.modelo.Ticket;
import com.arelance.helpdesk.repositorio.CategoriaRepo;
import com.arelance.helpdesk.repositorio.ClienteRepo;
import com.arelance.helpdesk.repositorio.ContratoRepo;
import com.arelance.helpdesk.repositorio.TecnicoRepo;
import com.arelance.helpdesk.repositorio.TicketRepo;
import com.arelance.helpdesk.servicios.TicketServices;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketRepo ticketRepo;
    private final TicketServices ticketServices;
    private final ClienteRepo clienteRepo;
    private final TecnicoRepo tecnicoRepo;
    private final CategoriaRepo categoriaRepo;
    private final ContratoRepo contratoRepo;

    public TicketController(TicketRepo ticketRepo, TicketServices ticketServices, ClienteRepo clienteRepo,
                            TecnicoRepo tecnicoRepo, CategoriaRepo categoriaRepo, ContratoRepo contratoRepo) {
        this.ticketRepo = ticketRepo;
        this.ticketServices = ticketServices;
        this.clienteRepo = clienteRepo;
        this.tecnicoRepo = tecnicoRepo;
        this.categoriaRepo = categoriaRepo;
        this.contratoRepo = contratoRepo;
    }

    // --- Endpoints Gonzalo / Main ---

    @PostMapping("/crear")
    public ResponseEntity<List<Ticket>> crearActividad(@RequestBody List<Ticket> a) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketRepo.saveAll(a));
    }

    @GetMapping("/consultar")
    @Operation(summary = "Consulta una cantidad de ticket", description = "Devuelve una lista limitada de tickets registrados en la base de datos")
    public ResponseEntity<Page<Ticket>> consultarTicket(@RequestParam(defaultValue = "0") int pagina) {
        return ResponseEntity.ok(ticketServices.obtenerTickets(pagina));
    }

    @GetMapping("/contar/hoy")
    public ResponseEntity<Long> contarTicketDeHoy(@RequestParam("estado") Ticket.Estado estado) {
        return ResponseEntity.ok(ticketRepo.recuentoTicketHoy(estado));
    }

    @GetMapping("/contar/{categoria}")
    public ResponseEntity<Long> contarTicketCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(ticketRepo.recuentoTicketTipo(categoria));
    }

    // --- Endpoints Rubén (Analysis) ---

    @Operation(summary = "Crea un nuevo ticket (botón '+ Nuevo')")
    @PostMapping
    public ResponseEntity<?> crearTicket(@RequestBody CrearTicketDto datos) {

        if (datos.asunto() == null || datos.asunto().isBlank()) {
            return ResponseEntity.badRequest().body("El asunto es obligatorio.");
        }
        if (datos.canal() == null) {
            return ResponseEntity.badRequest().body("El canal es obligatorio.");
        }
        if (datos.prioridad() == null) {
            return ResponseEntity.badRequest().body("La prioridad es obligatoria.");
        }
        if (datos.idCliente() == null) {
            return ResponseEntity.badRequest().body("idCliente es obligatorio.");
        }
        if (datos.idCategoria() == null) {
            return ResponseEntity.badRequest().body("idCategoria es obligatorio.");
        }

        Optional<Cliente> cliente = clienteRepo.findById(datos.idCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.badRequest().body("No existe el cliente con id " + datos.idCliente());
        }

        Optional<Categoria> categoria = categoriaRepo.findById(datos.idCategoria());
        if (categoria.isEmpty()) {
            return ResponseEntity.badRequest().body("No existe la categoría con id " + datos.idCategoria());
        }

        Tecnico tecnico = null;
        if (datos.idTecnico() != null) {
            Optional<Tecnico> encontrado = tecnicoRepo.findById(datos.idTecnico());
            if (encontrado.isEmpty()) {
                return ResponseEntity.badRequest().body("No existe el técnico con id " + datos.idTecnico());
            }
            tecnico = encontrado.get();
        }

        Contrato contrato = null;
        if (datos.idContrato() != null) {
            Optional<Contrato> encontrado = contratoRepo.findById(datos.idContrato());
            if (encontrado.isEmpty()) {
                return ResponseEntity.badRequest().body("No existe el contrato con id " + datos.idContrato());
            }
            contrato = encontrado.get();
        }

        Ticket ticket = new Ticket();
        ticket.setAsunto(datos.asunto());
        ticket.setDescripcion(datos.descripcion());
        ticket.setCanal(datos.canal());
        ticket.setPrioridad(datos.prioridad());
        ticket.setEstado(Ticket.Estado.EN_ABIERTO);
        ticket.setFechaApertura(datos.fechaApertura() != null ? datos.fechaApertura() : LocalDateTime.now());
        ticket.setCliente(cliente.get());
        ticket.setCategoria(categoria.get());
        ticket.setTecnico(tecnico);
        ticket.setContrato(contrato);

        Ticket guardado = ticketRepo.save(ticket);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TicketCreadoDto("created", String.valueOf(guardado.getIdTicket())));
    }

    @Operation(summary = "Búsqueda global de tickets por palabra clave (cliente, código, asunto...)")
    @GetMapping("/search")
    public ResponseEntity<ResultadosBusquedaDto> buscarTickets(@RequestParam("q") String q) {

        if (q == null || q.isBlank()) {
            return ResponseEntity.ok(new ResultadosBusquedaDto(List.of()));
        }

        List<TicketBusquedaDto> resultados = ticketRepo.buscarPorPalabraClave(q.trim())
                .stream()
                .map(t -> new TicketBusquedaDto(
                        t.getIdTicket(),
                        t.getAsunto(),
                        t.getEstado() != null ? t.getEstado().name() : null,
                        t.getPrioridad() != null ? t.getPrioridad().name() : null,
                        t.getCliente() != null ? (t.getCliente().getNombre() + " " + t.getCliente().getApellido()) : null,
                        t.getCategoria() != null ? t.getCategoria().getNombre() : null,
                        t.getTecnico() != null ? t.getTecnico().getNombre() : null,
                        t.getContrato() != null ? t.getContrato().getCodigo() : null,
                        t.getFechaApertura()))
                .toList();

        return ResponseEntity.ok(new ResultadosBusquedaDto(resultados));

    }

    @GetMapping("/listado")
    public Page<Ticket> consultarPagina(
            @RequestParam(required = false) Ticket.Estado estado,
            @RequestParam(required = false) Ticket.Prioridad prioridad,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        return ticketRepo.filtrar(estado, prioridad, PageRequest.of(page, size));
    }
}