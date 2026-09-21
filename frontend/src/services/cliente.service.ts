import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Cliente } from '../interfaces/cliente';
import { Page } from '../interfaces/page';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

    constructor(private http: HttpClient) { }

    // Obtener el cliente
    obtenerDatosCliente(id: number): Observable<Cliente> {
          return this.http.get<Cliente>(`/api/cliente/buscar/${id}`);
    }
}
