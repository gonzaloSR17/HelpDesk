import { Component } from '@angular/core';
import { MenuItem } from '../../../interfaces/menu-item';
import { SidebarUser } from '../../../interfaces/sidebar-user';
import { SidebarHelpdeskComponent } from '../../shared/sidebar-helpdesk/sidebar-helpdesk.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-helpdesk-cliente-main',
  imports: [SidebarHelpdeskComponent, RouterOutlet],
  templateUrl: './helpdesk-cliente-main.component.html',
  styleUrl: './helpdesk-cliente-main.component.css'
})
export class HelpdeskClienteMainComponent {

  adminMenu: MenuItem[] = [
  { label: 'Mis Tickets', route: '/cliente/mis-ticket' },
  { label: 'Nuevo ticket', route: '/cliente/nuevo-ticket' },
  { label: 'Perfil', route: '/cliente/perfil' },
];

  adminUser: SidebarUser = { name: 'Usuario', role: 'usuario', initial: 'A' };


}
