import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * Interceptor de autenticación. Se ejecuta en cada petición HTTP:
 * - Añade la cabecera Authorization: Bearer <token> a las llamadas a
 *   nuestra API (/api/...), salvo las de login, que no la necesitan.
 * - Si el backend responde 401 (token caducado o inválido), cierra la
 *   sesión y lleva al login - Jhon
 */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const esApiPropia = req.url.startsWith('/api/');
  const esAutenticacion = req.url.startsWith('/api/auth/');
  const token = authService.token();

  const peticion = token && esApiPropia && !esAutenticacion
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(peticion).pipe(
    catchError(error => {
      if (error.status === 401 && !esAutenticacion) {
        authService.logout();
        router.navigateByUrl('/');
      }
      return throwError(() => error);
    })
  );
};
