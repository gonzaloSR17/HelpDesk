import { HttpClient } from '@angular/common/http';
import { crearTransicionTicket } from '../interfaces/transicionTicket';
import { TransicionTicket } from '../interfaces/transicionTicket';
import { Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionTicketService {

  constructor(private http: HttpClient) {}

  // Variable global para almacenar el listado de transacciones que haya tenido un ticket
  historial = signal<TransicionTicket[] | null>(null);

  // agregar marca de tiempo
  setTransactionTicketService(tt: crearTransicionTicket): Observable<crearTransicionTicket> {
        return this.http.post<crearTransicionTicket>('/api/transicionticket/pasar', tt)
  }

  // Obtener listado
  getHistorialTransactionTicket(id: number): Observable<TransicionTicket[]> {
     return this.http.get<TransicionTicket[]>(`/api/transicionticket/devolver/${id}`)
  }
}
