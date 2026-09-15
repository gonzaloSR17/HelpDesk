import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { PageResponse, Ticket } from "./ticket.model";

@Injectable({ providedIn: 'root' })
export class TicketService {

  private baseUrl = 'http://localhost:8080/api/ticket/consultar';

  constructor(private http: HttpClient) {}

  // Pide una página de tickets al backend, con los filtros que le pasemos
  consultar(estado: string, prioridad: string, pagina: number, tamano: number): Observable<PageResponse<Ticket>> {
    let params = new HttpParams();

    params = params.set('page', pagina);
    params = params.set('size', tamano);

    if (estado) {
      params = params.set('estado', estado);
    }

    if (prioridad) {
      params = params.set('prioridad', prioridad);
    }

    return this.http.get<PageResponse<Ticket>>(this.baseUrl, { params: params });
  }
}