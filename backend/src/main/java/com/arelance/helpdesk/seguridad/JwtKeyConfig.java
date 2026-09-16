package com.arelance.helpdesk.seguridad;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Genera un par de claves RSA en memoria al arrancar la aplicacion.
 * No se persiste en ningun archivo ni variable de entorno: como el repo
 * es publico, una clave privada guardada en cualquier sitio del proyecto
 * seria un fallo de seguridad. El coste es que al reiniciar el backend,
 * los tokens emitidos antes dejan de ser validos - Jhon (Backend)
 */
@Configuration
public class JwtKeyConfig {

    @Bean
    public KeyPair jwtKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
        generador.initialize(2048);
        return generador.generateKeyPair();
    }
}
