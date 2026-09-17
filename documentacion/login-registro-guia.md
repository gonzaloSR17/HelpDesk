# Guía: Login, Alta de Usuarios y Flujo de Pantallas

> Autor: Jhon (Backend), diseñado junto con Gonzalo (Frontend) el 17/09/2026.
> Objetivo: que el equipo entienda **qué ya está construido**, **qué falta por construir**, y **cómo se conecta todo** — para poder implementarlo sin perderse. Complementa a `usuarios-roles-login.md` (arquitectura de lo ya hecho) y `guia-tecnica-codigo.md` (referencia de código ya hecho).

---

## 0. Estado actual — qué existe hoy y qué no

| Pieza | Estado |
|---|---|
| Entidades `Usuario`/`Cliente`/`Tecnico`/`Administrador` | ✅ Hecho |
| Login (`POST /api/auth/login`) con JWT firmado en RSA | ✅ Hecho |
| Restricción de endpoints por rol (matriz de permisos) | ❌ Pendiente — hoy `anyRequest().permitAll()`, todo abierto |
| Alta de usuarios por el Administrador | ❌ Pendiente — hoy se crean a mano por SQL |
| Activación de cuenta por enlace (registro) | ❌ Pendiente — diseñado en este documento, sin construir |
| Pantalla de login en Angular | ❌ Pendiente — la vamos a construir ahora |
| Redirección por rol tras el login | ❌ Pendiente |
| Rutas separadas por rol en el frontend | ❌ Pendiente — hoy todo vive bajo `/admin/...` (a revisar con Gonzalo) |

**Este documento describe el diseño de las filas marcadas ❌.** No están implementadas todavía — es el plan a seguir, no una descripción de código ya escrito.

---

## 1. Por qué NO hay un formulario público de "Crear cuenta"

Este helpdesk es para **clientes con contrato** de una empresa de servicios gestionados, no una app pública tipo red social. Un `Cliente` está ligado a un `Contrato` real; un `Tecnico` es personal interno. Dejar que cualquiera se registre a sí mismo sería un agujero de seguridad y de negocio (alguien podría crearse una cuenta de "cliente" sin tener contrato).

**Decisión de equipo:** el alta de cualquier usuario la hace siempre un **Administrador**, desde un panel interno. Lo que el usuario final hace no es "registrarse", es **activar una cuenta que el Administrador ya creó por él**.

**Por qué no se manda una contraseña por email** (aunque el documento original de roles lo sugería así): una contraseña en texto plano en un email es un riesgo — los emails se reenvían, quedan en el historial, pueden acabar en una bandeja compartida. En su lugar, se manda un **enlace de un solo uso** con un token aleatorio, y es la propia persona quien elige su contraseña al activar la cuenta. Es el mismo patrón que usan Slack, GitHub o Google Workspace para invitar gente a una organización.

**Por qué NO usar el DNI/CIF como "clave" de activación** (alternativa que se planteó y se descartó): el DNI y el CIF son datos personales, pero **no son secretos** — aparecen en facturas, contratos, registros públicos, y a veces en filtraciones de datos. Usarlos como única barrera para reclamar una cuenta permite dos ataques: (a) alguien que consiga el DNI de otra persona podría robarle la cuenta antes que ella, y (b) sin límite de intentos, se podría "probar" DNIs contra el formulario para descubrir cuáles son clientes de la empresa (fuga de información). Un token aleatorio de alta entropía (imposible de adivinar) no tiene ninguno de estos dos problemas.

---

## 2. El flujo completo de alta de usuario

```
┌──────────────┐   1. Rellena: nombre, email, rol     ┌──────────────┐
│ Administrador│ ────────────────────────────────────▶│   Backend    │
└──────────────┘                                       └──────┬───────┘
                                                                │ 2. Crea el Usuario
                                                                │    (estado "pendiente",
                                                                │    sin contraseña usable)
                                                                │ 3. Genera un token
                                                                │    aleatorio (32 bytes),
                                                                │    caduca en 24h
                                                                ▼
                                                        ┌──────────────┐
                                                        │  Email real  │  (o log en consola,
                                                        │  al cliente  │   ver sección 7)
                                                        └──────┬───────┘
                                                                │ 4. Clic en el enlace
                                                                │    /activar?token=xxxx
                                                                ▼
                                                        ┌──────────────┐
                                                        │   Frontend   │  Pide SOLO la
                                                        │  (Angular)   │  contraseña (x2)
                                                        └──────┬───────┘
                                                                │ 5. POST /api/auth/activar
                                                                │    { token, password }
                                                                ▼
                                                        ┌──────────────┐
                                                        │   Backend    │  Valida token,
                                                        │              │  guarda hash BCrypt,
                                                        │              │  activa la cuenta
                                                        └──────┬───────┘
                                                                │ 6. Login automático
                                                                ▼
                                                     Pantalla según su ROL (sección 4)
```

**Reglas importantes del token:**
- Aleatorio y largo (nada derivado de datos personales — ni el DNI, ni el email, ni la fecha de alta).
- Caduca (24h es razonable).
- Se usa **una sola vez** — en cuanto se activa la cuenta, el token se invalida (se borra o se marca como usado), no vale para un segundo intento.
- Se guarda en la base de datos **hasheado**, igual que una contraseña — así, aunque alguien accediera a la base de datos, no podría leer tokens válidos en texto plano.

---

## 3. Pantallas necesarias (frontend)

### 3.1 Login (`/login`)
```
┌─────────────────────────┐
│      Arelance HelpDesk   │
│                          │
│   Usuario: [________]    │
│   Contraseña: [________] │
│                          │
│   [ Iniciar sesión ]     │
│                          │
│   ¿Olvidaste tu          │
│   contraseña?            │
└─────────────────────────┘
```
**No hay botón de "Registro" visible.** Nadie llega aquí a crear una cuenta por su cuenta.

### 3.2 Activación (`/activar?token=xxxx`)
Solo se llega aquí a través del enlace del email — no está enlazada desde ningún menú.
```
┌─────────────────────────┐
│   Activa tu cuenta       │
│                          │
│   Nueva contraseña:      │
│   [________]             │
│   Repite la contraseña:  │
│   [________]             │
│                          │
│   [ Activar cuenta ]     │
└─────────────────────────┘
```
Al confirmar: login automático (ya demostró quién es con el token), y redirección directa a su pantalla según el rol.

### 3.3 "¿Olvidaste tu contraseña?" (futuro, misma infraestructura)
Es el mismo mecanismo de token de un solo uso, pero disparado por el propio usuario en vez de por el Administrador. No es parte de este sprint, pero al construir la activación, esta pieza sale casi gratis — se puede dejar anotada como mejora siguiente.

---

## 4. Redirección tras el login, según el rol

El login ya devuelve `{ token, rol, nombre }`. El frontend debe leer `rol` y redirigir:

```
rol === "CLIENTE"        →  /cliente/mis-tickets     (ver sección 11.2 del doc de roles)
rol === "TECNICO"        →  /tecnico/mis-asignados   (sección 11.3)
rol === "ADMINISTRADOR"  →  /admin/panel             (sección 11.4)
```

**Un Cliente nunca ve la pantalla de Administrador**, ni por accidente ni escribiendo la URL a mano — cuando exista la matriz de permisos (pendiente), aunque alguien fuerce la URL, las llamadas a la API fallarían por no tener el rol adecuado.

⚠️ **Coordinación pendiente con Gonzalo:** las rutas que ya existen (`/admin/panel`, `/admin/listado`) están todas bajo `/admin/...`, pero esas pantallas (panel de tickets, listado) probablemente las usará también el **Técnico**, no solo el Administrador. Hay que hablarlo antes de seguir montando pantallas nuevas, para no tener que renombrar rutas después.

---

## 5. Piezas técnicas que hay que construir

### Backend (nuevo, sobre lo que ya existe)

| Pieza | Qué hace |
|---|---|
| Campo/estado "pendiente de activar" en `Usuario` | Distingue una cuenta recién creada de una ya activada |
| Entidad o campo para el token de activación | Guarda el token (hasheado) + su fecha de caducidad, asociado a un `Usuario` |
| `POST /api/usuarios` (solo Administrador) | Alta de usuario: crea el `Usuario`, genera el token, dispara el envío |
| Servicio de envío (email real o log, ver sección 7) | Manda el enlace de activación |
| `POST /api/auth/activar` | Recibe `{ token, password }`, valida, activa la cuenta, guarda el hash |

### Frontend (nuevo)

| Pieza | Qué hace |
|---|---|
| Componente de Login | Formulario, llama a `POST /api/auth/login`, guarda el token |
| Componente de Activación | Formulario de contraseña, llama a `POST /api/auth/activar` |
| Guardado del token | `localStorage` (o similar), para mandarlo en cada petición futura |
| Interceptor HTTP | Añade automáticamente `Authorization: Bearer <token>` a cada llamada a la API |
| Route guards por rol | Antes de mostrar una ruta protegida, comprobar que hay token y que el rol coincide |
| Redirección tras login | Según el `rol` de la respuesta (sección 4) |

### Cómo se conectan entre sí (de extremo a extremo)

```
Angular (Login component)
   │  POST /api/auth/login { username, password }
   ▼
AuthController.login()  →  UsuarioRepo.findByUsername()  →  BCrypt.matches()
   │  si es correcto
   ▼
JwtService.generarToken()  →  firma con clave privada RSA (JwtKeyConfig)
   │
   ▼
Angular guarda el token  →  lo manda en cada petición futura (Authorization: Bearer ...)
   │
   ▼
JwtAuthFilter (en cada petición)  →  JwtService.validarYObtenerClaims()  →  clave pública RSA
   │  si es válido
   ▼
SecurityContextHolder  →  Spring sabe quién hace la petición y con qué rol
   │
   ▼
(pendiente) SecurityConfig comprueba si ese rol puede acceder a la ruta pedida
```

---

## 6. Resumen de las decisiones de seguridad tomadas (y por qué)

| Decisión | Por qué |
|---|---|
| No hay registro público | Los usuarios están ligados a contratos reales, no es una app de registro libre |
| Activación por enlace con token aleatorio, no por DNI/CIF | El DNI/CIF no son secretos, se pueden filtrar o conseguir; un token aleatorio no |
| No se manda la contraseña por email | Una contraseña en texto plano en un email es un riesgo innecesario; mejor que la elija el propio usuario |
| El token de activación se guarda hasheado | Igual que una contraseña — si alguien accede a la BD, no puede reutilizar tokens |
| El token caduca y se usa una sola vez | Limita la ventana de ataque si el email se intercepta |
| JWT firmado con RSA (no una clave compartida) | Ver `usuarios-roles-login.md`, sección 3 — para no compartir secretos con el futuro microservicio Python |

---

## 7. Decisiones pendientes (a confirmar antes de programar)

1. **Envío de email**: ¿SMTP real (necesita configurar un proveedor tipo Mailtrap/Gmail con credenciales por variable de entorno) o simulado por consola (`log.info("Enlace: ...")`) para poder probarlo ya y dejar el envío real como mejora posterior?
2. **Naming de rutas del frontend**: hablar con Gonzalo sobre separar `/cliente/...`, `/tecnico/...`, `/admin/...` en vez de que todo viva bajo `/admin/...`.
3. **Quién construye qué**: este documento no asigna tareas — falta decidir en equipo quién hace el backend de activación, quién el frontend de login/activación, y quién los guards de rutas.

---

## 8. Orden recomendado para implementarlo

1. Pantalla de Login en Angular (ya tiene backend listo, se puede hacer ya) + guardar el token.
2. Interceptor HTTP para mandar el token en cada petición.
3. Redirección por rol tras el login (aunque las pantallas de destino aún no existan del todo, sirve para probar el flujo).
4. Backend: campo de estado pendiente + entidad/campo de token en `Usuario`.
5. Backend: `POST /api/usuarios` (alta) + `POST /api/auth/activar`.
6. Frontend: pantalla de activación.
7. Envío de email (real o simulado, según lo decidido en la sección 7).
8. Matriz de permisos por rol en `SecurityConfig` (tarea aparte, ya documentada en `usuarios-roles-login.md`).
