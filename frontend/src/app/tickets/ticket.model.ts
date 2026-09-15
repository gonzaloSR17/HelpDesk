export interface Cliente {
    idCliente: number;
    nombre: string;
    apellido: string;
}

export interface Tecnico {
    idTecnico: number;
    nombre: string;
    apellido: string;
}

export interface Categoria {
    idCategoria: number;
    nombre: string;
}

export interface Ticket {
    idTicket: number;
    asunto: string;
    estado: string;
    prioridad: string;
    cliente: Cliente;
    tecnico: Tecnico | null;
    categoria: Categoria;
}

export interface PageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    number: number;
}