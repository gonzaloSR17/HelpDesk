import { Component, inject } from '@angular/core';
import { SidebarHelpdeskComponent } from '../../shared/sidebar-helpdesk/sidebar-helpdesk.component';
import { PanelTicketComponent } from './components-admin/panel-ticket/panel-ticket.component';
import { ListadoTicketComponent } from './components-admin/listado-ticket/listado-ticket.component';
import { RouterOutlet } from '@angular/router';
import { MenuItem } from '../../../interfaces/menu-item';
import { SidebarUser } from '../../../interfaces/sidebar-user';
import { LayoutService } from '../../shared/services/layout.service';
import { MobileTopbarComponent } from '../../shared/mobile-topbar/mobile-topbar.component';

@Component({
  selector: 'app-helpdesk-main',
  imports: [SidebarHelpdeskComponent, PanelTicketComponent, ListadoTicketComponent, RouterOutlet, MobileTopbarComponent],
  templateUrl: './helpdesk-main.component.html',
  styleUrl: './helpdesk-main.component.css'
})
export class HelpdeskMainComponent {

  layout = inject(LayoutService);
  
  adminMenu: MenuItem[] = [
  { label: 'Panel', route: '/admin/panel' },
  { label: 'Listado', route: '/admin/listado' },
  { label: 'Usuarios', route: '/admin/usuarios' },
  { label: 'Técnicos', route: '/admin/tecnicos' },
  { label: 'SLA', route: '/admin/sla' },
  { label: 'Informes', route: '/admin/informes' },
];

adminUser: SidebarUser = { name: 'Admin', role: 'Administrador', initial: 'A' };

}
