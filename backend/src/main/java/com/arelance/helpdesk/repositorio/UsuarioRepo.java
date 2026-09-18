package com.arelance.helpdesk.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arelance.helpdesk.modelo.Usuario;

/**
 * Repositorio de Usuario (clase base de Cliente/Tecnico/Administrador).
 * Usado por el login para buscar por username - Jhon (Backend)
 */
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
}
