import { Component } from '@angular/core';
import { ListadoTickets } from './listado-tickets/listado-tickets';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ListadoTickets],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}