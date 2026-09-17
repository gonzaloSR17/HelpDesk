import { Cliente } from "./cliente";
import { Tecnico } from "./tecnico";
import { Categoria } from "./categoria";
import { Contrato } from "./contrato";

export type Prioridad = 'BAJA' | 'MEDIA' | 'ALTA' | 'CRITICA';

export type Estado = 'EN_ABIERTO' | 'EN_CURSO' | 'ESCALADO' | 'RESUELTO' | 'CERRADO' | 'CADUCADO' | 'CANCELADO';

export type Canal = 'Teléfono' | 'Email';

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