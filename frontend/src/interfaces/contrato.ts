import { Cliente } from "./cliente";

export type NivelContrato = 'BASICO' | 'ESTANDAR' | 'PREMIUM';

export interface Contrato {
  idContrato?: number;
  codigo: string;
  nivel: NivelContrato;
  fechaInicio?: string; // Formato YYYY-MM-DD
  fechaFin?: string;    // Formato YYYY-MM-DD
  cliente?: Cliente;
}