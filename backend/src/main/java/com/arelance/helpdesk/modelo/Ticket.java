package com.arelance.helpdesk.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.arelance.helpdesk.modelo.Ticket.Canal;
import com.arelance.helpdesk.modelo.Ticket.Prioridad;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ==========================================================
// TODO Ticket:
//   - Atributos: asunto, descripcion, fechaApertura, fechaCierre, prioridad (enum)
//   - Relaciones: @ManyToOne Cliente, @ManyToOne Tecnico, @ManyToOne Categoria
//   - Enum Prioridad: BAJA, MEDIA, ALTA, CRITICA -> @Enumerated(EnumType.STRING)
//   - Logica de calculo de SLA a partir de fechaApertura + objetivos del Contrato

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Table (name = "ticket")

public class Ticket {
    
public enum Prioridad {
        BAJA, MEDIA, ALTA, CRITICA
    }

    public enum Estado {
    EN_ABIERTO,
    EN_CURSO,
    ESCALADO,
    RESUELTO,
    CERRADO,
    CANCELADO,
    CADUCADO
    }

    public enum Canal {
    Teléfono,
    Email
    }

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long idTicket;

    @Column(nullable = false)
    private String asunto;

    @Column
    private String descripcion;

    @Column(name = "fecha_apertura")
    private LocalDateTime fechaApertura;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Canal canal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_tecnico", nullable = true)
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_contrato")
    private Contrato contrato;

    @OneToMany(mappedBy = "ticket")
    @JsonIgnore
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "ticket")
    @JsonIgnore
    private List<TransicionTicket> transiciones = new ArrayList<>();

    @OneToMany(mappedBy = "ticket")
    @JsonIgnore
    private List<Adjunto> adjuntos = new ArrayList<>();
}
