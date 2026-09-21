import { Component, inject } from '@angular/core';
import { LayoutService } from '../services/layout.service';

@Component({
  selector: 'app-mobile-topbar',
  imports: [],
  template: `
    <nav class="navbar bg-white shadow-sm d-lg-none px-3">
      <button class="btn btn-outline-secondary" (click)="layout.toggleSidebar()">
        <i class="bi bi-list fs-4"></i>
      </button>
      <span class="fw-bold ms-2">Arelance HelpDesk</span>
    </nav>
  `,
  styleUrl: './mobile-topbar.component.css'
})
export class MobileTopbarComponent {

  // Implementamos la funcion del boton
  layout = inject(LayoutService);

}
