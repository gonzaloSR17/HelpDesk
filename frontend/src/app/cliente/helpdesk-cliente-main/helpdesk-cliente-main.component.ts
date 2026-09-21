import { Component, inject } from '@angular/core';
import { MenuItem } from '../../../interfaces/menu-item';
import { SidebarUser } from '../../../interfaces/sidebar-user';
import { SidebarHelpdeskComponent } from '../../shared/sidebar-helpdesk/sidebar-helpdesk.component';
import { RouterOutlet } from '@angular/router';
import { LayoutService } from '../../shared/services/layout.service';
import { MobileTopbarComponent } from '../../shared/mobile-topbar/mobile-topbar.component';

@Component({
  selector: 'app-helpdesk-cliente-main',
  imports: [SidebarHelpdeskComponent, RouterOutlet, MobileTopbarComponent],
  templateUrl: './helpdesk-cliente-main.component.html',
  styleUrl: './helpdesk-cliente-main.component.css'
})
export class HelpdeskClienteMainComponent {

  layout = inject(LayoutService);
  
  adminMenu: MenuItem[] = [
  { label: 'Mis Tickets', route: '/cliente/mis-ticket' },
  { label: 'Nuevo ticket', route: '/cliente/nuevo-ticket' },
  { label: 'Perfil', route: '/cliente/perfil' },
];

  adminUser: SidebarUser = { name: 'Usuario', role: 'usuario', initial: 'A' };


}
