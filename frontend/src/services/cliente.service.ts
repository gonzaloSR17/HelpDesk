import { computed, Injectable, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Cliente } from '../interfaces/cliente';
import { Page } from '../interfaces/page';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

    constructor(private http: HttpClient) { }

  // --- Estado centralizado: la página completa, no solo el array ---
  private _pageClientes = signal<Page<Cliente> | null>(null);
  pageClientes = this._pageClientes.asReadonly();

  // Derivados cómodos para los componentes
  clientes = computed(() => this._pageClientes()?.content ?? []);
  totalElementos = computed(() => this._pageClientes()?.totalElements ?? 0);
  totalPaginas = computed(() => this._pageClientes()?.totalPages ?? 0);

  // Contadores y detalles menores
  paginaActual = computed(() => this._pageClientes()?.number ?? 0);
  cargando = signal(false);
  error = signal<string | null>(null);

    // Obtener el cliente
    obtenerDatosCliente(id: number): Observable<Cliente> {
          return this.http.get<Cliente>(`/api/cliente/buscar/${id}`);
    }

    // Consultar Cliente
    cargar(pagina = 0) {
  this.cargando.set(true);
  this.error.set(null);

  this.http.get<Page<Cliente>>(`/api/cliente/consultar`, { params: { pagina } })
    .subscribe({
      next: page => {
        this._pageClientes.set(page);
        this.cargando.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar los clientes');
        this.cargando.set(false);
      },
    });
}
}
