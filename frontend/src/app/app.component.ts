import { Component } from '@angular/core';
import { PanelTicketComponent } from './panel-ticket/panel-ticket.component';
import { SidebarHelpdeskComponent } from './sidebar-helpdesk/sidebar-helpdesk.component';
import { RouterOutlet } from '@angular/router';
import { HelpdeskMainComponent } from './helpdesk-main/helpdesk-main.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, PanelTicketComponent, SidebarHelpdeskComponent, HelpdeskMainComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
