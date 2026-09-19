/**
 * Roles que devuelve el backend en el login. Coinciden con los textos
 * de AuthController.obtenerRol() - Jhon
 */
export type Rol = 'CLIENTE' | 'TECNICO' | 'ADMINISTRADOR';

/** Lo que se envía a POST /api/auth/login - Jhon */
export interface LoginRequest {
  username: string;
  password: string;
}

/** Lo que responde POST /api/auth/login - Jhon */
export interface LoginResponse {
  token: string;
  rol: Rol;
  nombre: string;
}
