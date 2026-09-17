import { Prioridad } from "./tickets";

export interface SLA {
  idSla?: number;
  prioridad: Prioridad;
  tiempoRespuestaObjetivoMin: number;
  tiempoResolucionObjetivoMin: number;
}