import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

/**
 * Formulario de inicio de sesión. Envía usuario y contraseña al backend a
 * través de AuthService; si son correctos, redirige al panel que
 * corresponde al rol del usuario y, si no, muestra el error - Jhon
 */
@Component({
  selector: 'app-login-form',
  imports: [FormsModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent {

  usuario = '';
  password = '';
  recordarme = false;

  // Mensaje de error que se muestra bajo el formulario
  error = '';

  // true mientras se espera la respuesta del backend
  cargando = false;

  constructor(private authService: AuthService, private router: Router) { }

  iniciarSesion(): void {
    this.error = '';
    this.cargando = true;

    this.authService
      .login({ username: this.usuario, password: this.password }, this.recordarme)
      .subscribe({
        next: respuesta => {
          this.cargando = false;
          this.router.navigateByUrl(this.authService.rutaPorRol(respuesta.rol));
        },
        error: err => {
          this.cargando = false;
          this.error = this.mensajeDeError(err.status);
        }
      });
  }

  private mensajeDeError(estado: number): string {
    if (estado === 401) return 'Usuario o contraseña incorrectos.';
    if (estado === 403) return 'Tu cuenta está desactivada. Contacta con el administrador.';
    return 'No se pudo conectar con el servidor. Inténtalo de nuevo.';
  }
}
