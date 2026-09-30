import { computed, Injectable, signal } from '@angular/core';
import { Tecnico } from '../interfaces/tecnico';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, switchMap, catchError, of, tap } from 'rxjs';
import { Page } from '../interfaces/page';
import { CrearTecnico, EditarTecnico, TecnicoAdmin } from '../interfaces/tecnico-admin';

@Injectable({
  providedIn: 'root'
})
export class TecnicoService {

  especialidad: string = '';
  constructor(private http: HttpClient) {
    // switchMap cancela la petición anterior si llega otra antes de que
    // responda: al buscar en cada tecla, así nunca se pinta un resultado viejo - Rubén
    this.peticiones.pipe(
      tap(() => { this.cargando.set(true); this.error.set(null); }),
      switchMap(({ pagina, nombre }) => {
        const params: Record<string, string | number> = { pagina };
        if (nombre.trim()) params['nombre'] = nombre.trim();
        return this.http.get<Page<TecnicoAdmin>>('/api/tecnico/consultar', { params }).pipe(
          catchError(() => {
            this.error.set('No se pudieron cargar los técnicos');
            return of(null);
          })
        );
      })
    ).subscribe(page => {
      if (page) this._pageTecnicos.set(page);
      this.cargando.set(false);
    });
  }

  // Metodo para imprimir a los tecnicos segun su especialidad
  getTecnicoEspecialidad(especialidad: String): Observable<Tecnico[]> {
    return this.http.get<Tecnico[]>(`/api/tecnico/devolver/${especialidad}`);
  }

  // ═══════════════════════════════════════════════════════════
  //  Listado de técnicos del admin - Rubén
  //  Mismo esquema que ClienteService: la página completa en un signal
  // ═══════════════════════════════════════════════════════════

  private peticiones = new Subject<{ pagina: number; nombre: string }>();

  private _pageTecnicos = signal<Page<TecnicoAdmin> | null>(null);
  pageTecnicos = this._pageTecnicos.asReadonly();

  tecnicos = computed(() => this._pageTecnicos()?.content ?? []);
  totalElementos = computed(() => this._pageTecnicos()?.totalElements ?? 0);
  totalPaginas = computed(() => this._pageTecnicos()?.totalPages ?? 0);
  paginaActual = computed(() => this._pageTecnicos()?.number ?? 0);

  // Texto del buscador (se recuerda al cambiar de página)
  filtroNombre = signal('');
  cargando = signal(false);
  error = signal<string | null>(null);

  cargar(pagina = 0, nombre = this.filtroNombre()) {
    this.filtroNombre.set(nombre);
    this.peticiones.next({ pagina, nombre });
  }

  // Recarga la página actual (después de crear/editar/eliminar)
  recargar() {
    this.cargar(this.paginaActual());
  }

  obtenerTecnico(id: number): Observable<TecnicoAdmin> {
    return this.http.get<TecnicoAdmin>(`/api/tecnico/buscar/${id}`);
  }

  crearTecnico(datos: CrearTecnico): Observable<TecnicoAdmin> {
    return this.http.post<TecnicoAdmin>('/api/tecnico/nuevo', datos);
  }

  editarTecnico(id: number, datos: EditarTecnico): Observable<TecnicoAdmin> {
    return this.http.put<TecnicoAdmin>(`/api/tecnico/editar/${id}`, datos);
  }

  eliminarTecnico(id: number): Observable<void> {
    return this.http.delete<void>(`/api/tecnico/eliminar/${id}`);
  }
}
