import { Component } from '@angular/core';
import { SidebarHelpdeskComponent } from '../sidebar-helpdesk/sidebar-helpdesk.component';
import { PanelTicketComponent } from '../panel-ticket/panel-ticket.component';
import { ListadoTicketComponent } from '../listado-ticket/listado-ticket.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-helpdesk-main',
  imports: [SidebarHelpdeskComponent, PanelTicketComponent, ListadoTicketComponent, RouterOutlet],
  templateUrl: './helpdesk-main.component.html',
  styleUrl: './helpdesk-main.component.css'
})
export class HelpdeskMainComponent {

}
