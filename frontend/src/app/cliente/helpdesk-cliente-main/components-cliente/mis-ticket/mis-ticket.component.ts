import { Component } from '@angular/core';
import { Ticket } from '../../../../../interfaces/tickets';
import { TicketServicesService } from '../../../../../services/ticket-services.service';

@Component({
  selector: 'app-mis-ticket',
  imports: [],
  templateUrl: './mis-ticket.component.html',
  styleUrl: './mis-ticket.component.css'
})
export class MisTicketComponent {

  constructor (private ticketServices: TicketServicesService ) {}
  
    // Lista de tickets para almacenar
    tickets: Ticket[] = [];

    // Almcenamos el id del usuario logeado
    idUser: number = 8;
  
    ngOnInit(): void {
      this.imprimirTickets();
    }
  
  
     imprimirTickets() {
      // Devolver una listado de socio
      this.ticketServices.imprimirTicketDeUsuarios(0, this.idUser).subscribe(list => {
        this.tickets = list.content;
        console.log(this.tickets)
      })
    }

}
