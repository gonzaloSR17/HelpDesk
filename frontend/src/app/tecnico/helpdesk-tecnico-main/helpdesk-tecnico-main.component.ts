import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SidebarHelpdeskComponent } from '../../shared/sidebar-helpdesk/sidebar-helpdesk.component';
import { MenuItem } from '../../../interfaces/menu-item';
import { SidebarUser } from '../../../interfaces/sidebar-user';

/**
 * Marco del panel de técnico: sidebar a la izquierda y, a la derecha,
 * la página que corresponda a la ruta activa - Jhon
 */
@Component({
  selector: 'app-helpdesk-tecnico-main',
  imports: [SidebarHelpdeskComponent, RouterOutlet],
  templateUrl: './helpdesk-tecnico-main.component.html',
  styleUrl: './helpdesk-tecnico-main.component.css'
})
export class HelpdeskTecnicoMainComponent {

  tecnicoMenu: MenuItem[] = [
    { label: 'Mis asignados', route: '/tecnico/mis-asignados' },
  ];

  tecnicoUser: SidebarUser = { name: 'Técnico', role: 'Técnico', initial: 'T' };
}
