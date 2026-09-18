package com.arelance.helpdesk.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ==========================================================
// TODO SLA:
//   - Atributos: tiempoRespuesta, tiempoResolucionObjetivo
//   - Relacion: @OneToOne Contrato (mappedBy en Contrato)

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Table (name = "sla")
public class SLA {

    // Version antigua
    // @Id 
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "idSla")
    // private Long idSla;

    // @Column(name = "tiempo_respuesta", nullable = false)
    // private Integer tiempoRespuesta;

    // @Column(name = "tiempo_resolucion_objetivo", nullable = false)
    // private Integer tiempoResolucionObjetivo;

    // @OneToOne
    // @JoinColumn(name = "id_contrato")
    // private Contrato contrato;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSla;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Ticket.Prioridad prioridad;   // CRITICA, ALTA, MEDIA, BAJA — una fila por cada una

    @Column(name = "tiempo_respuesta_objetivo_min", nullable = false)
    private Integer tiempoRespuestaObjetivoMin;

    @Column(name = "tiempo_resolucion_objetivo_min", nullable = false)
    private Integer tiempoResolucionObjetivoMin;

}
