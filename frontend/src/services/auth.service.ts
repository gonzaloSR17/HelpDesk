import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginRequest, LoginResponse, Rol } from '../interfaces/auth';

const CLAVE_SESION = 'helpdesk.sesion';

/**
 * Servicio de autenticación: centraliza el login y la sesión del usuario
 * en el navegador.
 *
 * Qué hace:
 * - Llama a POST /api/auth/login y guarda la sesión ({ token, rol, nombre }).
 * - Responde en cualquier momento si hay alguien logueado, con qué rol y
 *   con qué token.
 * - Cierra la sesión e indica a qué panel corresponde cada rol.
 *
 * Dónde se guarda la sesión:
 * - localStorage si el usuario marca "Recordarme" (sobrevive al cerrar
 *   el navegador).
 * - sessionStorage si no (se pierde al cerrar la pestaña).
 *
 * Notas:
 * - Con SSR, localStorage no existe en el servidor, por eso todo acceso
 *   al almacenamiento pasa por enNavegador().
 * - La caducidad del token se comprueba aquí solo para la interfaz; quien
 *   valida de verdad el token es el backend - Jhon
 */
@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(
        private http: HttpClient,
        @Inject(PLATFORM_ID) private platformId: object
    ) { }


    /**
    * Inicia sesión en el backend.
    *
    * @param datos usuario y contraseña
    * @param recordarme true guarda la sesión en localStorage; false, en sessionStorage
    * @returns la respuesta del backend ({ token, rol, nombre }). Si las
    *          credenciales son incorrectas, el Observable falla con un error 401
    */
    login(datos: LoginRequest, recordarme: boolean): Observable<LoginResponse> {
        return this.http.post<LoginResponse>('/api/auth/login', datos).pipe(
            tap(respuesta => this.guardarSesion(respuesta, recordarme))
        );
    }

    // Borra la sesion de los dos almacenes
    logout(): void {
        if (!this.enNavegador()) return;
        localStorage.removeItem(CLAVE_SESION);
        sessionStorage.removeItem(CLAVE_SESION);
    }

    // Hay sesion guardada y el token no ha caducado
    estaLogueado(): boolean {
        const sesion = this.obtenerSesion();
        return sesion !== null && !this.tokenCaducado(sesion.token);
    }

    token(): string | null {
        return this.obtenerSesion()?.token ?? null;
    }

    rol(): Rol | null {
        return this.obtenerSesion()?.rol ?? null;
    }

    nombre(): string | null {
        return this.obtenerSesion()?.nombre ?? null;
    }

    // Ruta del panel que corresponde a cada rol
    rutaPorRol(rol: Rol): string {
        switch (rol) {
            case 'ADMINISTRADOR': return '/admin';
            case 'TECNICO': return '/tecnico';
            case 'CLIENTE': return '/cliente';
        }
    }

    private guardarSesion(sesion: LoginResponse, recordarme: boolean): void {
        if (!this.enNavegador()) return;
        this.logout();
        const almacen = recordarme ? localStorage : sessionStorage;
        almacen.setItem(CLAVE_SESION, JSON.stringify(sesion));
    }

    private obtenerSesion(): LoginResponse | null {
        if (!this.enNavegador()) return null;
        const texto = localStorage.getItem(CLAVE_SESION) ?? sessionStorage.getItem(CLAVE_SESION);
        if (!texto) return null;
        try {
            return JSON.parse(texto) as LoginResponse;
        } catch {
            return null;
        }
    }

    // Lee la fecha de caducidad ("exp") del token, sin comprobar la firma
    private tokenCaducado(token: string): boolean {
        try {
            const base64 = token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
            const payload = JSON.parse(atob(base64));
            return payload.exp * 1000 < Date.now();
        } catch {
            return true;
        }
    }

    private enNavegador(): boolean {
        return isPlatformBrowser(this.platformId);
    }
}
