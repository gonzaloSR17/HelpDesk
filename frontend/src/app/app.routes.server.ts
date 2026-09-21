import { RenderMode, ServerRoute } from '@angular/ssr';

/**
 * Cómo se dibuja cada ruta cuando la aplicación se renderiza en el servidor (SSR).
 *
 * - Client: el servidor no dibuja la página; se dibuja solo en el navegador.
 * - Prerender: la página se genera una vez, al compilar, como HTML estático.
 *
 * Los paneles de admin, cliente y técnico dependen de la sesión, que está en
 * localStorage. El servidor no tiene localStorage, así que si los dibujara
 * creería que no hay sesión y el guard mandaría al login al recargar con F5.
 * Por eso se dibujan solo en el navegador (Client). Cada panel lleva dos
 * entradas: una para la raíz (/admin) y otra para todo lo que cuelga de ella
 * (/admin/panel, /admin/listado...) - Jhon
 */
export const serverRoutes: ServerRoute[] = [
  // Panel de administrador
  { path: 'admin', renderMode: RenderMode.Client },
  { path: 'admin/**', renderMode: RenderMode.Client },

  // Panel de cliente
  { path: 'cliente', renderMode: RenderMode.Client },
  { path: 'cliente/**', renderMode: RenderMode.Client },

  // Panel de técnico
  { path: 'tecnico', renderMode: RenderMode.Client },
  { path: 'tecnico/**', renderMode: RenderMode.Client },

  // Resto de rutas, como el login: se generan al compilar
  { path: '**', renderMode: RenderMode.Prerender }
];
