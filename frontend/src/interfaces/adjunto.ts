import { Ticket } from "./tickets";

export interface Adjunto {
  id?: number;
  nombreArchivo?: string;
  tipoMime?: string;
  rutaAlmacenamiento: string;
  fechaSubida?: string; // Formato ISO: YYYY-MM-DDTHH:mm:ss
  ticket?: Ticket;
}