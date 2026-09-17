import { Component } from '@angular/core';
import { LoginFormComponent } from './login-form/login-form.component';
import { SidebarHelpdeskComponent } from '../sidebar-helpdesk/sidebar-helpdesk.component';
import { LoginSidebarComponent } from './login-sidebar/login-sidebar.component';

@Component({
  selector: 'app-login-helpdesk',
  imports: [LoginFormComponent, SidebarHelpdeskComponent, LoginSidebarComponent],
  templateUrl: './login-helpdesk.component.html',
  styleUrl: './login-helpdesk.component.css'
})
export class LoginHelpdeskComponent {

}
