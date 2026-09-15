import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TicketService } from '../tickets/ticket.service';
import { Ticket } from '../tickets/ticket.model';

@Component({
  selector: 'app-listado-tickets',
  imports: [FormsModule],
  templateUrl: './listado-tickets.html',
  styleUrl: './listado-tickets.css',
})
export class ListadoTickets implements OnInit {

  tickets: Ticket[] = [];

  filtroEstado = '';
  filtroPrioridad = '';

  pagina = 0;
  tamanoPagina = 8;
  totalElementos = 0;
  totalPaginas = 0;

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.cargarTickets();
  }

  // Pide los tickets al backend y guarda el resultado
  cargarTickets(): void {
    this.ticketService.consultar(this.filtroEstado, this.filtroPrioridad, this.pagina, this.tamanoPagina)
      .subscribe(respuesta => {
        this.tickets = respuesta.content;
        this.totalElementos = respuesta.totalElements;
        this.totalPaginas = respuesta.totalPages;
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