import { Ticket } from "./tickets";

export interface Comentario {
  idComentario?: number;
  texto: string;
  autor: string;
  fecha?: string; // Formato ISO: YYYY-MM-DDTHH:mm:ss
  ticket?: Ticket;
}