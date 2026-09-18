import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Ticket } from '../../../../../interfaces/tickets';
import { TicketServicesService } from '../../../../../services/ticket-services.service';

@Component({
  selector: 'app-listado-ticket',
  imports: [FormsModule],
  templateUrl: './listado-ticket.component.html',
  styleUrl: './listado-ticket.component.css'
})
export class ListadoTicketComponent {

  tickets: Ticket[] = [];

  filtroEstado = '';
  filtroPrioridad = '';

  pagina = 0;
  tamanoPagina = 8;
  totalElementos = 0;
  totalPaginas = 0;

  constructor (private ticketServices: TicketServicesService ) {}

  ngOnInit(): void {
    this.cargarTickets();
  }

  // Pide los tickets al backend y guarda el resultado
  cargarTickets(): void {
    this.ticketServices.listar(this.filtroEstado, this.filtroPrioridad, this.pagina, this.tamanoPagina)
      .subscribe(respuesta => {
        this.tickets = respuesta.content;
        this.totalElementos = respuesta.totalElements;
        this.totalPaginas = respuesta.totalPages;
        console.log(respuesta)
      });
  }

  // Se llama cuando el usuario cambia un filtro: volvemos a la página 1
  aplicarFiltros(): void {
    this.pagina = 0;
    this.cargarTickets();
  }

  // Botón Anterior
  paginaAnterior(): void {
    if (this.pagina > 0) {
      this.pagina = this.pagina - 1;
      this.cargarTickets();
    }
  }

  // Botón Siguiente
  paginaSiguiente(): void {
    if (this.pagina + 1 < this.totalPaginas) {
      this.pagina = this.pagina + 1;
      this.cargarTickets();
    }
  }

}
