import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Categoria } from '../interfaces/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  constructor(private http: HttpClient) { }

  // Devolver las categorias
  imprimirCategorias(): Observable<Categoria[]> {
  return this.http.get<Categoria[]>(`/api/categoria/consultar`);
}

}
