import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';

// Cliente HTTP de Angular: activarlo (provideHttpClient), que use fetch por
// compatibilidad con SSR (withFetch) y engancharle interceptores (withInterceptors) - Jhon
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';

// Nuestro interceptor: añade el token a las llamadas a la API y cierra la
// sesión si el backend responde 401 - Jhon
import { authInterceptor } from '../interceptors/auth.interceptor';


/**
 * Configuración global de la aplicación, compartida por el navegador y el
 * servidor (SSR).
 *
 * Ojo: el interceptor solo añade el token a las llamadas a `/api/`. Si un
 * día se llama a otro servicio (como el microservicio de Python), habrá que
 * ampliarlo.
 *
 * @see {@link authInterceptor}
 * - Jhon
 */
export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideClientHydration(withEventReplay()),
    provideHttpClient(withFetch(), withInterceptors([authInterceptor]))
  ]
};



