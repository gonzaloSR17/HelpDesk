import { Cliente } from "./cliente";
import { Tecnico } from "./tecnico";
import { Categoria } from "./categoria";
import { Contrato } from "./contrato";

export type Prioridad = 'BAJA' | 'MEDIA' | 'ALTA' | 'CRITICA';

export type Estado = 'EN_ABIERTO' | 'EN_CURSO' | 'ASIGNADO' | 'ESCALADO' | 'RESUELTO' | 'CERRADO' | 'CADUCADO' | 'CANCELADO';

export type Canal = 'Teléfono' | 'Email';

export const PRIORIDADES: Record<string, string> = {
  CRITICA: '#dc3545',
  ALTA:    '#ffc107',
  MEDIA:   '#0d6efd',
  BAJA:    '#198754',
};

export interface Ticket {
  idTicket?: number;
  asunto: string;
  descripcion?: string;
  fechaApertura?: string; // Formato ISO: YYYY-MM-DDTHH:mm:ss
  fechaCierre?: string | null;
  canal: Canal;
  prioridad: Prioridad;
  estado: Estado;
  cliente?: Cliente;
  tecnico?: Tecnico | null;
  categoria?: Categoria;
  contrato?: Contrato;
}