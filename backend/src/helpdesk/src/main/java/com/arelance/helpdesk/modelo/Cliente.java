package com.arelance.helpdesk.modelo;

import java.util.ArrayList;
import java.util.List;


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
    @Column(name = "idCliente")
    private Integer idCliente;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String cif;

    @Column
    private String sector;

    @Column
    private String contacto;

    @OneToMany(mappedBy = "cliente")
    private List<Contrato> contratos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente")
    private List<Ticket> tickets = new ArrayList<>();

}
