package com.arelance.helpdesk.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Tecnico del helpdesk. Hereda id/username/passwordHash/email/nombre/activo
 * de Usuario (tabla compartida "usuario", SINGLE_TABLE) - Jhon (Backend)
 *
 * Relaciones:
 * - 1:N con Ticket (un tecnico puede tener varios tickets asignados)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("TECNICO")
public class Tecnico extends Usuario {

    @Column()
    private String apellido;

    @Column
    private String especialidad;

    @Column()
    private boolean disponible;

    @OneToMany(mappedBy = "tecnico")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();
}
