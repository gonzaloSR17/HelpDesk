package com.arelance.helpdesk.modelo;


import java.time.LocalDateTime;
 
import com.arelance.helpdesk.modelo.Ticket.Estado;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transicion_ticket")
public class TransicionTicket {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transicion")
    private Long idTransicion;
 
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;
 
    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio;

    @Column(name = "fecha_primera_respuesta")
    private LocalDateTime fechaPrimeraRespuesta;

    @Column(name = "fecha_resolutiva")
    private LocalDateTime fechaResolutiva;
 
    @ManyToOne
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;
}