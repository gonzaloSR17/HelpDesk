export interface Cliente {
  idCliente?: number;
  empresa: string;
  nombre: string;
  apellido: string;
  cif: string;
  sector?: string;
  contacto?: string;
  email?: string;
}