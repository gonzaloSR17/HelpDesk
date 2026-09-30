import { Component, inject, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { TecnicoService } from '../../../../../services/tecnico.service';
import { CrearTecnico, EditarTecnico, TecnicoAdmin } from '../../../../../interfaces/tecnico-admin';

type ModalAbierto = 'ver' | 'crear' | 'editar' | null;

/**
 * Listado de técnicos del admin - Rubén
 * - Búsqueda por nombre en cada tecla
 * - Paginación de 8 en 8 (mismo esquema que clientes)
 * - Ver (modal de detalles), Crear, Editar (id y datos personales disabled) y Eliminar
 */
@Component({
  selector: 'app-listado-tecnicos',
  imports: [FormsModule, DatePipe],
  templateUrl: './listado-tecnicos.component.html',
  styleUrl: './listado-tecnicos.component.css'
})
export class ListadoTecnicosComponent implements OnInit {

  tecnicoServices = inject(TecnicoService);

  // Opciones del desplegable (las mismas que usan los datos de ejemplo)
  especialidades = ['Accesos', 'Hardware', 'Red', 'Software'];

  modal = signal<ModalAbierto>(null);
  seleccionado = signal<TecnicoAdmin | null>(null);
  guardando = signal(false);
  errorModal = signal<string | null>(null);
  aviso = signal<string | null>(null);

  // Modelos de los formularios
  nuevo: CrearTecnico = this.nuevoVacio();
  edicion: EditarTecnico = { especialidad: '', disponible: true, activo: true };

  ngOnInit() {
    this.tecnicoServices.cargar(0, '');
  }

  // Se ejecuta en cada carácter: siempre vuelve a la primera página
  buscar(texto: string) {
    this.tecnicoServices.cargar(0, texto);
  }

  irAPagina(p: number) {
    this.tecnicoServices.cargar(p);
  }

  // ─── Ver ───────────────────────────────────────────────
  abrirVer(t: TecnicoAdmin) {
    this.prepararModal('ver', t);
    // Pedimos el detalle al backend por si ha cambiado desde que se cargó la lista
    this.tecnicoServices.obtenerTecnico(t.id).subscribe({
      next: detalle => this.seleccionado.set(detalle),
      error: e => this.errorModal.set(this.mensajeDe(e, 'No se pudo cargar el técnico')),
    });
  }

  // ─── Crear ─────────────────────────────────────────────
  abrirCrear() {
    this.nuevo = this.nuevoVacio();
    this.prepararModal('crear', null);
  }

  guardarNuevo(form: NgForm) {
    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }
    this.guardando.set(true);
    this.tecnicoServices.crearTecnico(this.nuevo).subscribe({
      next: t => {
        this.cerrarModal();
        this.mostrarAviso(`Técnico ${t.nombre} ${t.apellido} creado`);
        this.tecnicoServices.recargar();
      },
      error: e => {
        this.errorModal.set(this.mensajeDe(e, 'No se pudo crear el técnico'));
        this.guardando.set(false);
      },
    });
  }

  // ─── Editar ────────────────────────────────────────────
  abrirEditar(t: TecnicoAdmin) {
    this.edicion = { especialidad: t.especialidad ?? '', disponible: t.disponible, activo: t.activo };
    this.prepararModal('editar', t);
  }

  guardarEdicion(form: NgForm) {
    const t = this.seleccionado();
    if (!t || form.invalid) {
      form.control.markAllAsTouched();
      return;
    }
    this.guardando.set(true);
    this.tecnicoServices.editarTecnico(t.id, this.edicion).subscribe({
      next: () => {
        this.cerrarModal();
        this.mostrarAviso('Cambios guardados');
        this.tecnicoServices.recargar();
      },
      error: e => {
        this.errorModal.set(this.mensajeDe(e, 'No se pudieron guardar los cambios'));
        this.guardando.set(false);
      },
    });
  }

  // ─── Eliminar ──────────────────────────────────────────
  eliminar(t: TecnicoAdmin) {
    if (!confirm(`¿Eliminar al técnico ${t.nombre} ${t.apellido}? Esta acción no se puede deshacer.`)) {
      return;
    }
    this.tecnicoServices.eliminarTecnico(t.id).subscribe({
      next: () => {
        this.mostrarAviso('Técnico eliminado');
        // Si era el último de la página, volvemos a la anterior
        const pagina = this.tecnicoServices.paginaActual();
        const quedaVacia = this.tecnicoServices.tecnicos().length === 1 && pagina > 0;
        this.tecnicoServices.cargar(quedaVacia ? pagina - 1 : pagina);
      },
      error: e => this.tecnicoServices.error.set(this.mensajeDe(e, 'No se pudo eliminar el técnico')),
    });
  }

  cerrarModal() {
    this.modal.set(null);
    this.seleccionado.set(null);
    this.guardando.set(false);
    this.errorModal.set(null);
  }

  // Si el técnico tiene una especialidad que no está en la lista, la añadimos al desplegable
  opcionesEspecialidad(actual: string): string[] {
    return actual && !this.especialidades.includes(actual)
      ? [actual, ...this.especialidades]
      : this.especialidades;
  }

  private prepararModal(tipo: ModalAbierto, t: TecnicoAdmin | null) {
    this.errorModal.set(null);
    this.guardando.set(false);
    this.seleccionado.set(t);
    this.modal.set(tipo);
  }

  private nuevoVacio(): CrearTecnico {
    return { username: '', password: '', nombre: '', apellido: '', email: '', especialidad: '', disponible: true };
  }

  private mostrarAviso(msg: string) {
    this.aviso.set(msg);
    setTimeout(() => this.aviso.set(null), 3000);
  }

  // El backend manda { mensaje: "..." } en los errores de este controlador
  private mensajeDe(e: HttpErrorResponse, porDefecto: string): string {
    if (e.status === 403) return 'No tienes permisos para esta acción';
    return e.error?.mensaje ?? porDefecto;
  }
}
