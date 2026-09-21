import { Component, OnInit } from '@angular/core';
import { Cliente } from '../../../../../interfaces/cliente';
import { ClienteService } from '../../../../../services/cliente.service';

@Component({
  selector: 'app-crear-ticket',
  imports: [],
  templateUrl: './crear-ticket.component.html',
  styleUrl: './crear-ticket.component.css'
})
export class CrearTicketComponent implements OnInit {

  constructor (private clienteServices: ClienteService) {}

  // Creación básica
  cliente: Cliente = {
    idCliente: 0,
    nombre: '',
    apellido: '',
    cif: '',
    sector: '',
    contacto: '',
    email: ''
  };

  ngOnInit(): void {
    this.imprimirCliente();
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

  imprimirCliente() {
    // Devolver el cliente que esta usando la cuenta
    this.clienteServices.obtenerDatosCliente(8).subscribe(cliente => {
      this.cliente = cliente
      console.log(this.cliente)
    })
  }

}
