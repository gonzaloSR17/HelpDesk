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

// --- Contadores del panel de administración (dashboard) ---
// GET /api/v1/metrics/open -> { open_tickets: number }
obtenerTicketsAbiertos(): Observable<{ open_tickets: number }> {
 return this.http.get<{ open_tickets: number }>(`/api/v1/metrics/open`);
}
// GET /api/v1/metrics/in-progress -> { in_progress: number }
obtenerTicketsEnCurso(): Observable<{ in_progress: number }> {
 return this.http.get<{ in_progress: number }>(`/api/v1/metrics/in-progress`);
}
// GET /api/v1/metrics/resolved-month -> { resolved_month: number }
obtenerTicketsResueltosMes(): Observable<{ resolved_month: number }> {
 return this.http.get<{ resolved_month: number }>(`/api/v1/metrics/resolved-month`);
}
// GET /api/v1/tickets/contar/hoy?estado=EN_ABIERTO -> number
obtenerTicketsAbiertosHoy(): Observable<number> {
 const params = new HttpParams().set('estado', 'EN_ABIERTO');
 return this.http.get<number>(`/api/v1/tickets/contar/hoy`, { params });
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

  // Metodo para imprimir 8 Tickets
imprimirTicketDeUsuarios(page: number, id: number): Observable<Page<Ticket>> {
    const params = new HttpParams().set('pagina', page.toString());

    return this.http.get<Page<Ticket>>(`/api/v1/tickets/cliente/${id}`, { params });
  }
}
