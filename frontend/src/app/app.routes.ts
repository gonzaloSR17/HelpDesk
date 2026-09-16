import { Routes } from '@angular/router';
import { PanelTicketComponent } from './panel-ticket/panel-ticket.component';
import { ListadoTicketComponent } from './listado-ticket/listado-ticket.component';
import { HelpdeskMainComponent } from './helpdesk-main/helpdesk-main.component';

export const routes: Routes = [
  {
    path: 'admin',
    component: HelpdeskMainComponent,
    children: [
      {
        path: 'panel',
        component: PanelTicketComponent
      },
      {
        path: 'listado',
        component: ListadoTicketComponent
      }
    ]
  }
];
