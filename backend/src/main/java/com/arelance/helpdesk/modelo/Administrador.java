package com.arelance.helpdesk.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Administrador del helpdesk. Hereda id/username/passwordHash/email/nombre/activo
 * de Usuario (tabla compartida "usuario", SINGLE_TABLE) - Jhon (Backend)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("ADMINISTRADOR")
public class Administrador extends Usuario {

    public enum Nivel {
        ADMIN_TOTAL, SUPERVISOR
    }

    @Enumerated(EnumType.STRING)
    @Column()
    private Nivel nivel;
}
