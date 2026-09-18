import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MenuItem } from '../../../interfaces/menu-item';
import { SidebarUser } from '../../../interfaces/sidebar-user';

@Component({
  selector: 'app-sidebar-helpdesk',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar-helpdesk.component.html',
  styleUrl: './sidebar-helpdesk.component.css'
})
export class SidebarHelpdeskComponent {

   @Input() menuItems: MenuItem[] = [];
   @Input() user!: SidebarUser;

}
