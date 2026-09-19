package com.arelance.helpdesk.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Reglas por rol: HttpMethod distingue GET de POST en una misma ruta
// (requestMatchers), y HttpStatus + HttpStatusEntryPoint hacen que una
// petición sin token responda 401 en vez del 403 que Spring da por defecto - Jhon
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

/**
 * Configuración de seguridad de la API.
 *
 * <ul>
 *   <li>Sin sesiones (stateless): cada petición se autentica sola con su JWT,
 *       leído por {@link JwtAuthFilter} antes del filtro de login clásico.</li>
 *   <li>Sin formulario de login ni HTTP Basic de Spring: el login es
 *       {@code POST /api/auth/login}.</li>
 *   <li>Permisos por rol con {@code requestMatchers}: la lista de reglas se
 *       evalúa de arriba abajo y gana la primera que encaja, así que lo más
 *       específico va antes y {@code anyRequest()} va el último.</li>
 *   <li>Sin token responde 401; con token pero sin el rol adecuado, 403.</li>
 * </ul>
 *
 * Los roles se comprueban con {@code hasRole("X")}, que busca la autoridad
 * {@code ROLE_X}: es la que crea {@link JwtAuthFilter}. Si cambia el prefijo
 * allí, dejarían de funcionar todas las reglas.
 *
 * @see JwtAuthFilter
 * - Jhon (Backend)
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(e -> e
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .authorizeHttpRequests(auth -> auth
                    // Públicas
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                    .requestMatchers("/error").permitAll()


                    // Métricas y contadores globales: solo administrador
                    .requestMatchers("/api/v1/metrics/**").hasRole("ADMINISTRADOR")
                    .requestMatchers("/api/v1/tickets/contar/**").hasRole("ADMINISTRADOR")

                    // Ver todos los tickets: administrador y técnico
                    .requestMatchers(HttpMethod.GET, "/api/v1/tickets/consultar",
                            "/api/v1/tickets/listado", "/api/v1/tickets/search")
                    .hasAnyRole("ADMINISTRADOR", "TECNICO")

                    // Tickets de un cliente y crear ticket: cliente
                    .requestMatchers(HttpMethod.GET, "/api/v1/tickets/cliente/**").hasRole("CLIENTE")
                    .requestMatchers(HttpMethod.POST, "/api/v1/tickets").hasRole("CLIENTE")

                    // Altas masivas heredadas de la plantilla: solo administrador
                    .requestMatchers("/api/v1/tickets/crear", "/api/cliente/**", "/api/tecnico/**",
                            "/api/contrato/**", "/api/sla/**", "/api/categoria/**",
                            "/api/adjunto/**", "/api/comentario/**", "/api/transicionticket/**")
                    .hasRole("ADMINISTRADOR")

                    // Cualquier otra ruta: al menos estar logueado
                    .anyRequest().authenticated())

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
