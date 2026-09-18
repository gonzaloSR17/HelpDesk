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
  
  constructor (private ticketServices: TicketServicesService ) {}

  // Lista de tickets para almacenar
  tickets: Ticket[] = [];


  ngOnInit(): void {
    this.imprimirTickets();
    // // Primera funcion cuenta los contadores
    // this.cargarContadores();

    // // Segundo cargamos los socios
    // this.imprimirSocios();


    // // Notifica si hay una actualizacion para actualizar los contadores
    // this.apiService.actualizarContadores$.subscribe(() => {
    //   this.cargarContadores();
    //   this.imprimirSocios();
    // });
  }


   imprimirTickets() {
    // Devolver una listado de socio
    this.ticketServices.imprimirTicket(0).subscribe(list => {
      // Dentro de list en la parte de content contiene los 4 primeros socios es un observable pero al buscar dentro podemos asignar el array poo
      this.tickets = list.content
      console.log(this.tickets)
    })
  }

}
