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
// TODO Tecnico:
//   - Atributos: nombre, especialidad, email
//   - Relacion: @OneToMany Ticket (1:N)

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Table (name = "tecnico")
public class Tecnico {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTecnico")
    private Integer idTecnico;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String especialidad;

    @Column
    private String email;

    @OneToMany(mappedBy = "tecnico")
    private List<Ticket> tickets = new ArrayList<>();
}
