package com.arelance.helpdesk.seguridad;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Se ejecuta en cada peticion HTTP: si trae un JWT valido en la cabecera
 * Authorization, autentica al usuario para el resto de la peticion - Jhon (Backend)
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String cabecera = request.getHeader("Authorization");

        if (cabecera == null || !cabecera.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = cabecera.substring(7);

        try {
            Claims claims = jwtService.validarYObtenerClaims(token);
            String username = claims.getSubject();
            String rol = claims.get("rol", String.class);

            var authoridad = new SimpleGrantedAuthority("ROLE_" + rol);
            var autenticacion = new UsernamePasswordAuthenticationToken(
                    username, null, List.of(authoridad));

            SecurityContextHolder.getContext().setAuthentication(autenticacion);
        } catch (Exception e) {
            // Token invalido, caducado o manipulado: seguimos sin autenticar,
            // Spring Security decidira despues si la ruta lo requiere o no
        }

        filterChain.doFilter(request, response);
    }
}
