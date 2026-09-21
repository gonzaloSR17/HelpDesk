import { Injectable, signal, inject } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {

  // Injectamos el servicios Router
  private router = inject(Router);

  //signal es una forma de almacenar un valor reactivo. Angular puede detectar automáticamente cuándo ese valor cambia y actualizar las partes de la interfaz
  sidebarOpen = signal(false);

  // Si el router tiene un evento como iniciar el path, se activara y dentro el sub evento La navegación ha terminado correctamente.
  // asi cerramos el sidebar si cambiamos la opcion
  constructor() {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.closeSidebar();
      });
  }


  // Cada vez que ejecutemos false -> true -> false -> true
  toggleSidebar() {
    this.sidebarOpen.update(v => !v);
  }

  closeSidebar() {
    this.sidebarOpen.set(false);
  }

}
