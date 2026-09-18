DOCUMENTACIÓN: SEMANA 1  
Encargado de documentación: Rubén Palomo Mercado

Además de la asignación de roles, nos repartimos todo el trabajo a partes iguales. En este caso cada miembro trató endpoints que añadir y probar.  
Basados en las diapositivas, creamos las partes esenciales del proyecto. Cada miembro presentó una pantalla acorde a los mockups. Oscar creó la base de datos y yo construí el formulario de creación de tickets con HTML. Gonzalo hizo el frontend, es decir, la parte visible del proyecto. Se dedicó especialmente al frontend y a las pantallas, además de trabajar en la versión para móviles de esta herramienta.  
Tras la primera mentoría vimos claro el siguiente objetivo: Login. Crear usuarios clientes, técnicos y un administrador. Jhon y Gonzalo se están encargando de estos.  
Finalmente me asignó terminar la creación de tickets para que sea funcional, pues los números de ejemplo eran estáticos. Ahora siguen una lista de tickets que se irá llenando progresivamente.  
La primera semana de trabajo concluyó satisfactoriamente sin fallas notables.

| Jhon package com.arelance.helpdesk; import org.springframework.boot.SpringApplication; import org.springframework.boot.autoconfigure.SpringBootApplication; @SpringBootApplication public class HelpdeskApplication { 	public static void main(String\[\] args) { 		SpringApplication.run(HelpdeskApplication.class, args); 	} }  |
| :---- |
| Gonzalo package com.arelance.helpdesk; import org.junit.jupiter.api.Test; import org.springframework.boot.test.context.SpringBootTest; @SpringBootTest class HelpdeskApplicationTests { 	@Test 	void contextLoads() { 	} }  |
| Oscar // Definimos que es un controlador \+ ruta @RestController @RequestMapping("/api/ticket") public class TicketController {     private final TicketRepo ticketRepo;     public TicketController(TicketRepo ticketRepo) {         this.ticketRepo \= ticketRepo;     }     @PostMapping("/crear")     public ResponseEntity\<List\<Ticket\>\> crearActividad(@RequestBody List\<Ticket\> a) {         //TODO: process POST request                  return ResponseEntity.status(HttpStatus.CREATED).body(ticketRepo.saveAll(a));     };     @GetMapping("/consultar")     public Page\<Ticket\> consultarPagina(             @RequestParam(required \= false) Ticket.Estado estado,             @RequestParam(required \= false) Ticket.Prioridad prioridad,             @RequestParam(defaultValue \= "0") int page,             @RequestParam(defaultValue \= "8") int size) {         return ticketRepo.filtrar(estado, prioridad, PageRequest.of(page, size));     } }  |
| Rubén @RestController @RequestMapping("/api/v1/metrics") public class MetricsController {     private final TicketRepo ticketRepo;     public MetricsController(TicketRepo ticketRepo) {         this.ticketRepo \= ticketRepo;     }     @Operation(summary \= "Suma total acumulada de tickets repartidos por categoría")     @GetMapping("/count/categories-total")     public ResponseEntity\<TotalCategoriasDto\> totalPorCategorias() {         long total \= ticketRepo.countByCategoriaIsNotNull();         return ResponseEntity.ok(new TotalCategoriasDto(total));     } }  |
|  |

