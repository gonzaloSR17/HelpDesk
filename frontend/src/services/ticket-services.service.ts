import { Injectable, signal, computed } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ticket } from '../interfaces/tickets';
import { AnalyticsData } from '../interfaces/metrics';
import { Page } from '../interfaces/page';

@Injectable({
  providedIn: 'root'
})
export class TicketServicesService {

  // Guardamos el Ticket Seleccionado
  ticketSeleccionado = signal<Ticket | null>(null);

  // --- Estado centralizado: la página completa, no solo el array ---
  private _pageTickets = signal<Page<Ticket> | null>(null);
  pageTickets = this._pageTickets.asReadonly();

  // Derivados cómodos para los componentes
  tickets = computed(() => this._pageTickets()?.content ?? []);
  totalElementos = computed(() => this._pageTickets()?.totalElements ?? 0);
  totalPaginas = computed(() => this._pageTickets()?.totalPages ?? 0);

  // Recordamos dónde está el usuario (filtro/página actuales)
  private _paginaActual = signal(0);
  private _filtroEstado = signal('');
  private _filtroPrioridad = signal('');
  paginaActual = this._paginaActual.asReadonly();
  filtroEstado = this._filtroEstado.asReadonly();
  filtroPrioridad = this._filtroPrioridad.asReadonly();

  constructor(private http: HttpClient) {}

  setTicketSeleccionado(ticket: Ticket) {
    this.ticketSeleccionado.set(ticket);
  }

  limpiarSeleccion() {
    this.ticketSeleccionado.set(null);
  }

  getTicketById(id: number): Observable<Ticket> {
    return this.http.get<Ticket>(`/api/v1/tickets/buscar/${id}`);
  }

  // --- Único método de carga, usado por panel y listado por igual ---
  cargarTickets(
    pagina: number = this._paginaActual(),
    estado: string = this._filtroEstado(),
    prioridad: string = this._filtroPrioridad(),
    tamanoPagina: number = 8
  ) {
    this._paginaActual.set(pagina);
    this._filtroEstado.set(estado);
    this._filtroPrioridad.set(prioridad);

    this.listar(estado, prioridad, pagina, tamanoPagina).subscribe({
      next: (page: Page<Ticket>) => this._pageTickets.set(page),
      error: (err) => console.error('Error al cargar tickets:', err)
    });
  }

  // --- Parchea un ticket ya cargado, SIN pedir nada al backend ---
  // Esto es lo que evita que se mueva la página/filtro al asignar técnico
  actualizarTicketEnLista(ticketActualizado: Ticket) {
    const pageActual = this._pageTickets();
    if (!pageActual) return;

    this._pageTickets.set({
      ...pageActual,
      content: pageActual.content.map(t =>
        t.idTicket === ticketActualizado.idTicket ? ticketActualizado : t
      )
    });
  }

  // --- Contadores del panel de administración (dashboard) ---
  obtenerTicketsAbiertos(): Observable<{ open_tickets: number }> {
    return this.http.get<{ open_tickets: number }>(`/api/v1/metrics/open`);
  }
  obtenerTicketsEnCurso(): Observable<{ in_progress: number }> {
    return this.http.get<{ in_progress: number }>(`/api/v1/metrics/in-progress`);
  }
  obtenerTicketsResueltosMes(): Observable<{ resolved_month: number }> {
    return this.http.get<{ resolved_month: number }>(`/api/v1/metrics/resolved-month`);
  }
  obtenerTicketsAbiertosHoy(): Observable<number> {
    const params = new HttpParams().set('estado', 'EN_ABIERTO');
    return this.http.get<number>(`/api/v1/tickets/contar/hoy`, { params });
  }

  listar(estado: string, prioridad: string, pagina: number, tamanoPagina: number): Observable<Page<Ticket>> {
    let params = new HttpParams()
      .set('page', pagina.toString())
      .set('size', tamanoPagina.toString());

    if (estado) params = params.set('estado', estado);
    if (prioridad) params = params.set('prioridad', prioridad);

    return this.http.get<Page<Ticket>>(`/api/v1/tickets/listado`, { params });
  }

  imprimirTicketDeUsuarios(page: number, id: number): Observable<Page<Ticket>> {
    const params = new HttpParams().set('pagina', page.toString());
    return this.http.get<Page<Ticket>>(`/api/v1/tickets/cliente/${id}`, { params });
  }

  getTendeciaSemanal(): Observable<AnalyticsData> {
    return this.http.get<AnalyticsData>(`/api/v1/metrics/weekly-trend`);
  }

  obtenerConteoCategorias(): Observable<{ [key: string]: number }> {
    return this.http.get<{ [key: string]: number }>(`/api/v1/tickets/count/categorias`);
  }

  obtenerGraficoTendencia(): Observable<any> {
    return this.http.get<any>(`/graficas/tendencia-semanal`);
  }

  obtenerGraficoCategorias(): Observable<any> {
    return this.http.get<any>(`/graficas/categorias`);
  }

  actualizarLista(id: number, t: Ticket): Observable<Ticket> {
    return this.http.put<Ticket>(`/api/v1/tickets/actualizar/${id}`, t);
  }


}