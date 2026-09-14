package com.arelance.helpdesk.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Contrato:
//   - Atributos: codigo, nivel (enum: BASICO, ESTANDAR, PREMIUM), fechaInicio, fechaFin
//   - Relaciones: @ManyToOne Cliente (N:1), @OneToOne SLA (1:1)
@Entity
@NoArgsConstructor 
@AllArgsConstructor 
@Data 
@Table(name = "contrato")
public class Contrato {

 public enum Nivel {
        BASICO, ESTANDAR, PREMIUM
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idContrato")
    private Long idContrato;

    @Column(nullable = false)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nivel nivel;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToOne(mappedBy = "contrato", cascade = CascadeType.ALL)
    private SLA sla;

    @OneToMany(mappedBy = "contrato")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

}
