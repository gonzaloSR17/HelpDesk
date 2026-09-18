package com.arelance.helpdesk.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arelance.helpdesk.dto.LoginRequestDto;
import com.arelance.helpdesk.dto.LoginResponseDto;
import com.arelance.helpdesk.modelo.Administrador;
import com.arelance.helpdesk.modelo.Cliente;
import com.arelance.helpdesk.modelo.Tecnico;
import com.arelance.helpdesk.modelo.Usuario;
import com.arelance.helpdesk.repositorio.UsuarioRepo;
import com.arelance.helpdesk.seguridad.JwtService;

import io.swagger.v3.oas.annotations.Operation;

/**
 * Login unico para los 3 roles (Cliente/Tecnico/Administrador) - Jhon (Backend)
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepo usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UsuarioRepo usuarioRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login unico para Cliente, Tecnico y Administrador. Devuelve un JWT con el rol.")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto datos) {
        Usuario usuario = usuarioRepo.findByUsername(datos.username()).orElse(null);

        if (usuario == null || !passwordEncoder.matches(datos.password(), usuario.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña incorrectos");
        }

        if (!usuario.isActivo()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario desactivado");
        }

        String rol = obtenerRol(usuario);
        String token = jwtService.generarToken(usuario.getUsername(), rol);

        return ResponseEntity.ok(new LoginResponseDto(token, rol, usuario.getNombre()));
    }

    private String obtenerRol(Usuario usuario) {
        if (usuario instanceof Cliente) {
            return "CLIENTE";
        }
        if (usuario instanceof Tecnico) {
            return "TECNICO";
        }
        return "ADMINISTRADOR";
    }
}
