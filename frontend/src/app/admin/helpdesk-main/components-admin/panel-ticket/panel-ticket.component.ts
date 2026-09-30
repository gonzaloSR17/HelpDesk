import { Component, ElementRef, inject, OnInit, PLATFORM_ID, ViewChild } from '@angular/core';
import { TicketServicesService } from '../../../../../services/ticket-services.service';
import { Ticket } from '../../../../../interfaces/tickets';
import { AnalyticsData } from '../../../../../interfaces/metrics';
import { CategoryCounts } from '../../../../../interfaces/metrics';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData } from 'chart.js'
import { isPlatformBrowser } from '@angular/common';
import { ModalEditarTransactionTicketAdminComponent } from '../modal-editar-transaction-ticket-admin/modal-editar-transaction-ticket-admin.component';
import { TecnicoService } from '../../../../../services/tecnico.service';

@Component({
  selector: 'app-panel-ticket',
  imports: [BaseChartDirective, ModalEditarTransactionTicketAdminComponent],
  templateUrl: './panel-ticket.component.html',
  styleUrl: './panel-ticket.component.css'
})
export class PanelTicketComponent implements OnInit {

  private ticketServices = inject(TicketServicesService);
  public tecnicoServices = inject(TecnicoService);

   // Los gráficos solo se dibujan en el navegador, nunca en el servidor (SSR)
  private platformId = inject(PLATFORM_ID);
  esNavegador = isPlatformBrowser(this.platformId);

   // Referencias a los <div> del HTML donde Plotly va a dibujar
  @ViewChild('lineChartDiv') lineChartDiv?: ElementRef<HTMLDivElement>;
  @ViewChild('donutChartDiv') donutChartDiv?: ElementRef<HTMLDivElement>;

  // Almacenamos el signal ticket ya creado en el servicio
  tickets = this.ticketServices.tickets;

  // Controla la vista del modal
  modalVisible = false;

  // Variables para guardar los datos
  analyticData: AnalyticsData = { days: [], open: [], resolved: []}
  // Definición de las categorías
  categorias: string[] = ['RED', 'SOFTWARE', 'HARDWARE', 'ACCESOS'];
  
  // Objeto para guardar los recuentos
  conteoPorCategoria: { [key: string]: number } = {};

  // Contadores del panel (tarjetas KPI)
  ticketsAbiertos = 0;
  ticketsAbiertosHoy = 0;
  ticketsEnCurso = 0;
  ticketsResueltosMes = 0;



  // // --- Gráfico de línea: abiertos vs resueltos ---
  // lineChartData: ChartData<'line'> = {
  //   labels: [],
  //   datasets: [
  //     { data: [], label: 'Abiertos', borderColor: '#2f6fed', tension: 0.3 },
  //     { data: [], label: 'Resueltos', borderColor: '#1f9d55', tension: 0.3 }
  //   ]
  // };
  // lineChartOptions: ChartConfiguration['options'] = { responsive: true };

  //  // --- Donut: reparto por categoría ---
  // doughnutChartData: ChartData<'doughnut'> = {
  //   labels: ['Redes', 'Software', 'Hardware', 'Accesos'],
  //   datasets: [{ data: [0, 0, 0, 0], backgroundColor: ['#2f6fed', '#1f9d55', '#f5a623', '#7b4fd6'] }]
  // };
  // doughnutChartOptions: ChartConfiguration['options'] = { responsive: true };


  ngOnInit(): void {
    // Inicializamos la lista de entradas
    this.ticketServices.cargarTickets();

    // Cargamos los contadores al entrar en el panel
    this.cargarContadores();

    // Si en el futuro algo emite en este Subject (p.ej. crear un ticket nuevo), los contadores se recargan solos


       // Los gráficos solo se piden si estamos en el navegador (no en el render del servidor)
    if (this.esNavegador) {
      this.cargarGraficoCategorias();
      this.cargarGraficoTendencia();
    }

  }

  cargarContadores() {
    this.ticketServices.obtenerTicketsAbiertos().subscribe(res => {
      this.ticketsAbiertos = res.open_tickets;
    });

    this.ticketServices.obtenerTicketsEnCurso().subscribe(res => {
      this.ticketsEnCurso = res.in_progress;
    });

    this.ticketServices.obtenerTicketsResueltosMes().subscribe(res => {
      this.ticketsResueltosMes = res.resolved_month;
    });

    this.ticketServices.obtenerTicketsAbiertosHoy().subscribe(total => {
      this.ticketsAbiertosHoy = total;
    });
  }

  // guarda la especialidad de un tecnico para buscarlo ene l compoente del modal
  abrirModal(ticketId: number | undefined) {
  if (!ticketId) return;

  this.ticketServices.getTicketById(ticketId).subscribe(ticket => {
    this.ticketServices.setTicketSeleccionado(ticket);
    this.modalVisible = true;
  });
}

   async cargarGraficoTendencia() {
    // Import dinámico: plotly.js-dist-min solo se carga en el navegador
    const Plotly = await import('plotly.js-dist-min');
 
    this.ticketServices.obtenerGraficoTendencia().subscribe({
      next: (figura) => {
        if (this.lineChartDiv) {
          Plotly.newPlot(
            this.lineChartDiv.nativeElement,
            figura.data,
            figura.layout,
            { responsive: true }
          );
        }
      },
      error: (err) => console.error('Error al cargar el gráfico de tendencia:', err)
    });
  }
 
  async cargarGraficoCategorias() {
    const Plotly = await import('plotly.js-dist-min');
 
    this.ticketServices.obtenerGraficoCategorias().subscribe({
      next: (figura) => {
        if (this.donutChartDiv) {
          Plotly.newPlot(
            this.donutChartDiv.nativeElement,
            figura.data,
            figura.layout,
            { responsive: true }
          );
        }
      },
      error: (err) => console.error('Error al cargar el gráfico de categorías:', err)
    });
  }
  }
