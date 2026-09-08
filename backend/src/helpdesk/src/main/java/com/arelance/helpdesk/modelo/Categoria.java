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

// TODO Categoria:
//   - Atributos: nombre, descripcion
//   - Relacion: @OneToMany Ticket (1:N)

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Table(name = "categoria")

public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria")
    private Integer idCategoria; 


    @Column(length = 100, columnDefinition = "VARCHAR(100)", nullable = false)
    private String nombre;

    @Column(length = 100, columnDefinition = "VARCHAR(100)", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    private List<Ticket> tickets = new ArrayList<>();
}
