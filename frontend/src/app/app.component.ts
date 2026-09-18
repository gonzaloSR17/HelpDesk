import { Component } from '@angular/core';
import { PanelTicketComponent } from './admin/helpdesk-main/components-admin/panel-ticket/panel-ticket.component';
import { SidebarHelpdeskComponent } from './shared/sidebar-helpdesk/sidebar-helpdesk.component';
import { RouterOutlet } from '@angular/router';
import { HelpdeskMainComponent } from './admin/helpdesk-main/helpdesk-main.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, PanelTicketComponent, SidebarHelpdeskComponent, HelpdeskMainComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
