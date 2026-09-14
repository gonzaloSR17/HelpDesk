package com.arelance.helpdesk.modelo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ==========================================================

// TODO Cliente:
//   - Atributos: nombre, cif, sector, contacto
//   - Relacion: @OneToMany Contrato (1:N)

@Entity 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Table(name = "cliente")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Cliente")
    private Long idCliente;

    @Column(nullable = false)
    private String nombre;

     @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String cif;

    @Column
    private String sector;

    @Column(length = 9)
    private String contacto;

    @Column
    private String email;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Contrato> contratos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

}
