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
 * Cliente del helpdesk. Hereda id/username/passwordHash/email/nombre/activo
 * de Usuario (tabla compartida "usuario", SINGLE_TABLE) - Jhon (Backend)
 *
 * Relaciones:
 * - 1:N con Contrato (un cliente puede tener varios contratos)
 * - 1:N con Ticket (un cliente puede tener varios tickets)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {

    @Column
    private String apellido;

    @Column
    private String cif;


    @Column(length = 9)
    private String contacto;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Contrato> contratos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();
}

