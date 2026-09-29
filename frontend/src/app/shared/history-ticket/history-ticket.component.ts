import { Component, inject, OnInit, signal, Signal } from '@angular/core';
import { TransactionTicketService } from '../../../services/transaction-ticket.service';
import { TicketServicesService } from '../../../services/ticket-services.service';
import { Ticket } from '../../../interfaces/tickets';
import { TransicionTicket } from '../../../interfaces/transicionTicket';
import { PRIORIDADES } from '../../../interfaces/tickets';
import { COLOR_ESTADO } from '../../../interfaces/transicionTicket';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-history-ticket',
  imports: [FormsModule],
  templateUrl: './history-ticket.component.html',
  styleUrl: './history-ticket.component.css'
})
export class HistoryTicketComponent implements OnInit {

  ngOnInit(): void {
    this.cargarHistorial();
  }

  private servicioTransaction = inject(TransactionTicketService);
  private servicioTicket = inject(TicketServicesService);

  // Recuperamos el ticket Seleccionado
  ticket: Signal<Ticket | null> = this.servicioTicket.ticketSeleccionado;

  // variable para almacenafr el historial
  historial = signal<TransicionTicket[] | null>(null);

  // Diccionario para los colores (Metemos los colores guardados en la interfaz)
  colorEstado = COLOR_ESTADO;
  colorPrioridad = PRIORIDADES;

  cargarHistorial() {
    const idTicket = this.ticket()?.idTicket;

    // Validación: solo consumimos la API si existe un ID válido
    if (!idTicket) {
      return;
    }

    // this.cargando.set(true);
    // this.error.set(null);

    this.servicioTransaction.getHistorialTransactionTicket(idTicket).subscribe({
      next: (list) => {
        this.historial.set(list);// Asignación correcta en Signal
        console.log("hola - "); 
        console.log(idTicket);
        console.log(this.historial());
        // this.cargando.set(false);
      },
      error: (err) => {
        console.error('Detalle del error:', err);
      },
    });

  }

}
