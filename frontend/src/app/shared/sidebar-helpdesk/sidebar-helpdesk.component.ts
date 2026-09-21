import { Component, Input } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { MenuItem } from '../../../interfaces/menu-item';
import { SidebarUser } from '../../../interfaces/sidebar-user';
import { Rol } from '../../../interfaces/auth';
import { AuthService } from '../../../services/auth.service';

/**
 * Barra lateral compartida por los paneles de admin, cliente y técnico.
 * Muestra el menú que le pasa cada panel y, abajo, el nombre y el rol
 * del usuario de la sesión, con el botón de cerrar sesión - Jhon
 */
@Component({
  selector: 'app-sidebar-helpdesk',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './sidebar-helpdesk.component.html',
  styleUrl: './sidebar-helpdesk.component.css'
})
export class SidebarHelpdeskComponent {

  @Input() menuItems: MenuItem[] = [];
  @Input() user!: SidebarUser;

  constructor(private authService: AuthService, private router: Router) { }

  // Nombre del usuario de la sesión
  get nombre(): string {
    return this.authService.nombre() ?? '';
  }

  // Rol de la sesión, con un texto legible
  get rol(): string {
    const textos: Record<Rol, string> = {
      ADMINISTRADOR: 'Administrador',
      TECNICO: 'Técnico',
      CLIENTE: 'Cliente'
    };
    const rol = this.authService.rol();
    return rol ? textos[rol] : '';
  }

  // Primera letra del nombre, para el avatar
  get inicial(): string {
    return this.nombre.charAt(0).toUpperCase();
  }

  // Borra la sesión y vuelve a la pantalla de login
  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigateByUrl('/');
  }
}
