package com.arelance.helpdesk.modelo;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO Comentario:
//   - Atributos: texto, autor, fecha
//   - Relacion: @ManyToOne Ticket

@Entity 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long idComentario;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private String autor;

    @Column
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_ticket")
    private Ticket ticket;

}
