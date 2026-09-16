package com.arelance.helpdesk.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase base de la jerarquia de usuarios (Cliente, Tecnico, Administrador).
 * Estrategia SINGLE_TABLE: los 3 roles viven en una unica tabla "usuario",
 * distinguidos por la columna "rol" - Jhon (Backend)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rol")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean activo;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;
}
