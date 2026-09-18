package com.arelance.helpdesk.seguridad;

import java.security.KeyPair;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Genera y valida los JWT de login, firmados con RSA (clave privada
 * para firmar, clave publica para validar) - Jhon (Backend)
 */
@Service
public class JwtService {

    private final KeyPair keyPair;
    private final long caducidadMs;

    public JwtService(KeyPair jwtKeyPair, @Value("${app.jwt.caducidad}") long caducidadMs) {
        this.keyPair = jwtKeyPair;
        this.caducidadMs = caducidadMs;
    }

    /**
     * Crea un token firmado para un usuario recien autenticado.
     *
     * @param username el nombre de usuario (va en el "subject" del token)
     * @param rol      CLIENTE / TECNICO / ADMINISTRADOR
     * @return el JWT ya firmado, listo para devolver al cliente
     */
    public String generarToken(String username, String rol) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + caducidadMs);

        return Jwts.builder()
                .subject(username)
                .claim("rol", rol)
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(keyPair.getPrivate())
                .compact();
    }

    /**
     * Valida la firma y la caducidad de un token, y devuelve sus claims.
     * Si el token esta manipulado o caducado, lanza una excepcion.
     *
     * @param token el JWT recibido en la cabecera Authorization
     * @return los claims (username, rol...) si el token es valido
     */
    public Claims validarYObtenerClaims(String token) {
        return Jwts.parser()
                .verifyWith(keyPair.getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
