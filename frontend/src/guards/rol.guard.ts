import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Rol } from '../interfaces/auth';
import { AuthService } from '../services/auth.service';

/**
 * Protege una ruta según el rol. El rol permitido se indica en la propia
 * ruta, con data: { rol: 'ADMINISTRADOR' }.
 * - Sin sesión o con el token caducado: manda al login.
 * - Con sesión pero de otro rol: manda al panel de su propio rol.
 * - Con el rol correcto: deja pasar - Jhon
 */
export const rolGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.estaLogueado()) {
    return router.parseUrl('/');
  }

  const rolUsuario = authService.rol() as Rol;
  const rolPermitido = route.data['rol'] as Rol;

  if (rolUsuario !== rolPermitido) {
    return router.parseUrl(authService.rutaPorRol(rolUsuario));
  }

  return true;
};
