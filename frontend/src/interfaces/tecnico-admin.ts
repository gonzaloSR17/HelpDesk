// Técnico tal y como lo devuelve /api/tecnico/consultar y /api/tecnico/buscar/{id}
// (TecnicoDetalleDto en el backend) - Rubén
export interface TecnicoAdmin {
  id: number;
  username: string;
  nombre: string;
  apellido: string;
  email: string;
  especialidad: string;
  disponible: boolean;
  activo: boolean;
  fechaAlta: string;
  ticketsAsignados: number;
}

// Cuerpo de POST /api/tecnico/nuevo
export interface CrearTecnico {
  username: string;
  password: string;
  nombre: string;
  apellido: string;
  email: string;
  especialidad: string;
  disponible: boolean;
}

// Cuerpo de PUT /api/tecnico/editar/{id} (solo los campos editables)
export interface EditarTecnico {
  especialidad: string;
  disponible: boolean;
  activo: boolean;
}
