import { Injectable } from '@angular/core';
import { Tecnico } from '../interfaces/tecnico';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../interfaces/page';

@Injectable({
  providedIn: 'root'
})
export class TecnicoService {

  especialidad: string = '';
  constructor(private http: HttpClient) { }

  // Metodo para imprimir a los tecnicos segun su especialidad
  getTecnicoEspecialidad(especialidad: String): Observable<Tecnico[]> {
    return this.http.get<Tecnico[]>(`/api/tecnico/devolver/${especialidad}`);
  }
}
