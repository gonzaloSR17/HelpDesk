import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Ticket } from '../interfaces/tickets';
import { Page } from '../interfaces/page';

@Injectable({
  providedIn: 'root'
})
export class TicketServicesService {
  consultar(filtroEstado: string, filtroPrioridad: string, pagina: number, tamanoPagina: number) {
    throw new Error('Method not implemented.');
  }

  constructor(private http: HttpClient) { }

  // URL de la API


  // Para actualizar el controlador y recargas
  private actualizarContadores = new BehaviorSubject<void>(undefined);
  actualizarContadores$ = this.actualizarContadores.asObservable();

  // Metodo para imprimir 8 Tickets
  imprimirTicket(page: number): Observable<Page<Ticket>> {
    return this.http.get<Page<Ticket>>(`/api/v1/tickets/consultar?pagina=${page}`);
  }

  
  // Pide una página de tickets al backend, con los filtros que le pasemos
  listar(estado: string, prioridad: string, pagina: number, tamanoPagina: number) {
  let params = new HttpParams()
    .set('page', pagina.toString())           // <--- 'page' debe coincidir con el backend
    .set('size', tamanoPagina.toString());     // <--- 'size' debe coincidir con el backend

  if (estado) params = params.set('estado', estado);
  if (prioridad) params = params.set('prioridad', prioridad);

  return this.http.get<any>(`/api/v1/tickets/listado`, { params });
}



}
