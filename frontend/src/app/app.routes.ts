import { Routes } from '@angular/router';
import { PanelTicketComponent } from './admin/helpdesk-main/components-admin/panel-ticket/panel-ticket.component';
import { ListadoTicketComponent } from './admin/helpdesk-main/components-admin/listado-ticket/listado-ticket.component';
import { HelpdeskMainComponent } from './admin/helpdesk-main/helpdesk-main.component';
import { LoginHelpdeskComponent } from './login-helpdesk/login-helpdesk.component';

import { HelpdeskClienteMainComponent } from './cliente/helpdesk-cliente-main/helpdesk-cliente-main.component';
import { MisTicketComponent } from './cliente/helpdesk-cliente-main/components-cliente/mis-ticket/mis-ticket.component';
import { CrearTicketComponent } from './cliente/helpdesk-cliente-main/components-cliente/crear-ticket/crear-ticket.component';
import { PerfilUsuarioComponent } from './cliente/helpdesk-cliente-main/components-cliente/perfil-usuario/perfil-usuario.component';

export const routes: Routes = [
   {
    path: 'admin',
    component: HelpdeskMainComponent,
    children: [
      { path: '', redirectTo: 'panel', pathMatch: 'full' },
      { path: 'panel', component: PanelTicketComponent },
      { path: 'listado', component: ListadoTicketComponent }
    ]
  },
  {
    path: 'cliente',
    component: HelpdeskClienteMainComponent,
    children: [
      { path: '', redirectTo: 'mis-ticket', pathMatch: 'full' },
      { path: 'mis-ticket', component: MisTicketComponent },
      { path: 'nuevo-ticket', component: CrearTicketComponent },
      { path: 'perfil', component: PerfilUsuarioComponent }
    ]
  },
  {
    path: '',
    component: LoginHelpdeskComponent
  }
];
