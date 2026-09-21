import { Component, OnInit } from '@angular/core';
import { TicketServicesService } from '../../../../../services/ticket-services.service';
import { Ticket } from '../../../../../interfaces/tickets';

@Component({
  selector: 'app-panel-ticket',
  imports: [],
  templateUrl: './panel-ticket.component.html',
  styleUrl: './panel-ticket.component.css'
})
export class PanelTicketComponent implements OnInit {

  constructor(private ticketServices: TicketServicesService) { }

  // Lista de tickets para almacenar
  tickets: Ticket[] = [];

  // Contadores del panel (tarjetas KPI)
  ticketsAbiertos = 0;
  ticketsAbiertosHoy = 0;
  ticketsEnCurso = 0;
  ticketsResueltosMes = 0;

  ngOnInit(): void {
    this.imprimirTickets();

    // Cargamos los contadores al entrar en el panel
    this.cargarContadores();

    // Si en el futuro algo emite en este Subject (p.ej. crear un ticket nuevo), los contadores se recargan solos
    this.ticketServices.actualizarContadores$.subscribe(() => {
      this.cargarContadores();
    });
  }

  cargarContadores() {
    this.ticketServices.obtenerTicketsAbiertos().subscribe(res => {
      this.ticketsAbiertos = res.open_tickets;
    });

    this.ticketServices.obtenerTicketsEnCurso().subscribe(res => {
      this.ticketsEnCurso = res.in_progress;
    });

    this.ticketServices.obtenerTicketsResueltosMes().subscribe(res => {
      this.ticketsResueltosMes = res.resolved_month;
    });

    this.ticketServices.obtenerTicketsAbiertosHoy().subscribe(total => {
      this.ticketsAbiertosHoy = total;
    });
  }

  imprimirTickets() {
    // Devolver un listado de tickets
    this.ticketServices.imprimirTicket(0).subscribe(list => {
      this.tickets = list.content;
      console.log(this.tickets);
    });
  }

}