import { Ticket, Estado } from "./tickets";  

export interface TransicionTicket {
  idTransicion?: number;
  estado: Estado;
  fechaCambio: string; // Formato ISO: YYYY-MM-DDTHH:mm:ss
  ticket: Ticket;
}