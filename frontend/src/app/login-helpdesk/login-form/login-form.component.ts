import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  imports: [FormsModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.css'
})
export class LoginFormComponent {

  usuario = ''
password = ''
recordarme = false

 // De momento solo recoge los datos del formulario.
  // Aquí es donde luego se llamará al AuthService para pedir el token JWT.
  iniciarSesion(): void {
    console.log('Usuario:', this.usuario);
    console.log('Password:', this.password);
    console.log('Recordarme:', this.recordarme);
  }
}

