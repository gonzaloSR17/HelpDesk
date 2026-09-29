import { Component, effect, inject, Signal } from '@angular/core';
import { TecnicoService } from '../../../../../services/tecnico.service';
import { Tecnico } from '../../../../../interfaces/tecnico';
import { TicketServicesService } from '../../../../../services/ticket-services.service';
import { Ticket } from '../../../../../interfaces/tickets';
import { FormsModule } from '@angular/forms';
import { crearTransicionTicket } from '../../../../../interfaces/transicionTicket';
import { TransactionTicketService } from '../../../../../services/transaction-ticket.service';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-modal-editar-transaction-ticket-admin',
  imports: [FormsModule],
  templateUrl: './modal-editar-transaction-ticket-admin.component.html',
  styleUrl: './modal-editar-transaction-ticket-admin.component.css'
})
export class ModalEditarTransactionTicketAdminComponent {

  // Servicios inyectados (deben ir antes de cualquier campo que los use)
  private ticketServices = inject(TicketServicesService);
  private tecnicoServices = inject(TecnicoService);
  private transactionTicketService = inject(TransactionTicketService);

  // Recuperamos el ticket Seleccionado
  ticket: Signal<Ticket | null> = this.ticketServices.ticketSeleccionado;

  // Listado de tecnicos especializados
  tecnicos: Tecnico[] = [];

  // inicializar el trasanction ticket
  transicionTicket: crearTransicionTicket = { estado: 'EN_CURSO', asunto: 'Asignado a ', ticket: null };

  // tecnico seleccionado
  tecnico: Tecnico = { apellido: '', nombre: '', email: '', especialidad: '', idTecnico: 0 };

  constructor() {
    effect(() => {
      console.log('Ticket recibido en el modal:', this.ticket());
      this.getTecnicoEspecializados();
    });
  }

  getTecnicoEspecializados() {
    const grupo = this.ticket()?.categoria?.grupo;

    if (!grupo) {
      console.warn('No se puede buscar técnicos: el ticket no tiene grupo/categoría');
      return;
    }

    this.tecnicoServices.getTecnicoEspecialidad(grupo).subscribe(data => {
      this.tecnicos = data;
      console.log(this.tecnicos);
    });
  }

  pasarMarcaTiempo() {
  const ticketActual = this.ticket();

  if (!ticketActual?.idTicket) {
    console.warn('No hay ticket seleccionado, no se puede continuar');
    return;
  }

  this.transicionTicket.ticket = ticketActual;
  this.transicionTicket.asunto += this.tecnico.nombre + " " + this.tecnico.apellido;

  // SWITCH MAP = COMO TENEMOS QUE SUSCRIBIRNOS A 2 SERVICIOS EJECUTAMOS SWITCH MAP
  this.transactionTicketService.setTransactionTicketService(this.transicionTicket).pipe(
    switchMap((creada) => {
      console.log(`${creada.asunto}`);

      // Encadenamos el PUT para actualizar el estado del ticket
      const ticketActualizado: Ticket = {
        ...ticketActual,
        estado: this.transicionTicket.estado,
        tecnico: this.tecnico
      };

      // ! significa que este valor nunca sera nulo
      return this.ticketServices.actualizarLista(ticketActual.idTicket!, ticketActualizado);
    })
  ).subscribe({
    next: (ticketGuardado) => {
  this.transicionTicket = { estado: 'EN_CURSO', asunto: 'Asignado a ', ticket: null };
  this.tecnico = { apellido: '', nombre: '', email: '', especialidad: '', idTecnico: 0 };

  // en vez de: this.ticketServices.cargarTickets();
  this.ticketServices.actualizarTicketEnLista(ticketGuardado);
},
    error: (err) => {
      // Este error captura fallos tanto de la creación de la transacción como del PUT
      console.log(err.error?.mensaje ?? 'No se pudo guardar');
    }
  });
}

  // actualizar el ticket para añadir el
  actualizarTicket() {


  }
  // this.ticketServices
}