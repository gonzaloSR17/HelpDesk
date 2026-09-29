import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../../../../services/cliente.service';
import { RouterLink } from '@angular/router';
import { Cliente } from '../../../../../interfaces/cliente';


@Component({
  selector: 'app-listado-clientes',
  imports: [FormsModule],
  templateUrl: './listado-clientes.component.html',
  styleUrl: './listado-clientes.component.css'
})
export class ListadoClientesComponent {

    clienteServices = inject(ClienteService);

    ngOnInit() {
    this.clienteServices.cargar(0);
   }

  irAPagina(p: number) {
    this.clienteServices.cargar(p);
  }

}
