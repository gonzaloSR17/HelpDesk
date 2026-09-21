import { Routes } from '@angular/router';
import { PanelTicketComponent } from './admin/helpdesk-main/components-admin/panel-ticket/panel-ticket.component';
import { ListadoTicketComponent } from './admin/helpdesk-main/components-admin/listado-ticket/listado-ticket.component';
import { HelpdeskMainComponent } from './admin/helpdesk-main/helpdesk-main.component';
import { LoginHelpdeskComponent } from './login-helpdesk/login-helpdesk.component';

import { HelpdeskClienteMainComponent } from './cliente/helpdesk-cliente-main/helpdesk-cliente-main.component';
import { MisTicketComponent } from './cliente/helpdesk-cliente-main/components-cliente/mis-ticket/mis-ticket.component';
import { CrearTicketComponent } from './cliente/helpdesk-cliente-main/components-cliente/crear-ticket/crear-ticket.component';
import { PerfilUsuarioComponent } from './cliente/helpdesk-cliente-main/components-cliente/perfil-usuario/perfil-usuario.component';
 
// import Jhon
import { HelpdeskTecnicoMainComponent } from './tecnico/helpdesk-tecnico-main/helpdesk-tecnico-main.component';
import { MisAsignadosComponent } from './tecnico/helpdesk-tecnico-main/components-tecnico/mis-asignados/mis-asignados.component';

// para proteger la ruta según el rol
import { rolGuard } from '../guards/rol.guard';



export const routes: Routes = [
   {
    path: 'admin',
    component: HelpdeskMainComponent,
    canActivate: [rolGuard],
    data: { rol: 'ADMINISTRADOR' },
    children: [
      { path: '', redirectTo: 'panel', pathMatch: 'full' },
      { path: 'panel', component: PanelTicketComponent },
      { path: 'listado', component: ListadoTicketComponent }
    ]
  },
  {
    path: 'cliente',
    component: HelpdeskClienteMainComponent,
    canActivate: [rolGuard],
    data: { rol: 'CLIENTE' },
    children: [
      { path: '', redirectTo: 'mis-ticket', pathMatch: 'full' },
      { path: 'mis-ticket', component: MisTicketComponent },
      { path: 'nuevo-ticket', component: CrearTicketComponent },
      { path: 'perfil', component: PerfilUsuarioComponent }
    ]
  },
    {
    path: 'tecnico',
    component: HelpdeskTecnicoMainComponent,
    canActivate: [rolGuard],
    data: { rol: 'TECNICO' },
    children: [
      { path: '', redirectTo: 'mis-asignados', pathMatch: 'full' },
      { path: 'mis-asignados', component: MisAsignadosComponent }
    ]
  },

  {
    path: '',
    component: LoginHelpdeskComponent
  }
];
