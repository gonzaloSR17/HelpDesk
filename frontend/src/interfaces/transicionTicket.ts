import { Ticket, Estado } from "./tickets";  

export interface TransicionTicket {
  idTransicion?: number;
  estado: Estado;
  fechaCambio: Date; // Formato ISO: YYYY-MM-DDTHH:mm:ss
  asunto: string;
  ticket: Ticket | null;
}

// Colores para cada icono del historial
export const COLOR_ESTADO: Record<string, string> = {
  ABIERTO:  '#0d6efd',
  ASIGNADO: '#20c997',
  EN_CURSO: '#fd7e14',
  ESCALADO: '#6f42c1',
  RESUELTO: '#198754',
  CERRADO:  '#6c757d',
};

export interface crearTransicionTicket {
  idTransicion?: number;
  estado: Estado;
// fechaCambio: string; // Formato ISO: YYYY-MM-DDTHH:mm:ss
  asunto: string;
  ticket: Ticket | null;
}