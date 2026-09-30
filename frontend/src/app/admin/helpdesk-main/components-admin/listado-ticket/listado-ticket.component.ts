import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TicketServicesService } from '../../../../../services/ticket-services.service';
import { RouterLink } from '@angular/router';
import { ModalEditarTransactionTicketAdminComponent } from '../modal-editar-transaction-ticket-admin/modal-editar-transaction-ticket-admin.component';
import { Ticket } from '../../../../../interfaces/tickets';

@Component({
  selector: 'app-listado-ticket',
  imports: [FormsModule, ModalEditarTransactionTicketAdminComponent, RouterLink],
  templateUrl: './listado-ticket.component.html',
  styleUrl: './listado-ticket.component.css'
})
export class ListadoTicketComponent {

  ticketServices = inject(TicketServicesService);

  // Signals directamente del servicio: la fuente única de verdad
  tickets = this.ticketServices.tickets;
  totalElementos = this.ticketServices.totalElementos;
  totalPaginas = this.ticketServices.totalPaginas;
  pagina = this.ticketServices.paginaActual;

  filtroEstado = '';
  filtroPrioridad = '';

  modalVisible = false;

  ngOnInit(): void {
    this.ticketServices.cargarTickets(0, '', '');
  }

  aplicarFiltros(): void {
    this.ticketServices.cargarTickets(0, this.filtroEstado, this.filtroPrioridad);
  }

  paginaAnterior(): void {
    const actual = this.pagina();
    if (actual > 0) {
      this.ticketServices.cargarTickets(actual - 1, this.filtroEstado, this.filtroPrioridad);
    }
  }

  paginaSiguiente(): void {
    if (this.pagina() + 1 < this.totalPaginas()) {
      this.ticketServices.cargarTickets(this.pagina() + 1, this.filtroEstado, this.filtroPrioridad);
    }
  }

  abrirModal(ticketId: number | undefined) {
    if (!ticketId) return;
    this.ticketServices.getTicketById(ticketId).subscribe(ticket => {
      this.ticketServices.setTicketSeleccionado(ticket);
      this.modalVisible = true;
    });
  }

  guardarTicket(t: Ticket) {
    this.ticketServices.setTicketSeleccionado(t);
  }
}