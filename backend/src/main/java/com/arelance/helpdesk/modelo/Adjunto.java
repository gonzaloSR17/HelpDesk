package com.arelance.helpdesk.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "adjunto")
public class Adjunto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;

    private String tipoMime; // ej: "image/png", "application/pdf"

    // @Lob (Version antigua)
    // @Column(name = "contenido", columnDefinition = "LONGBLOB") // O VARBINARY(MAX) / BYTEA según la BD
    // private byte[] contenido;
    
    // Poner:
    @Column(name = "ruta_almacenamiento", nullable = false)
    private String rutaAlmacenamiento;   // ej: "/uploads/tickets/42/factura.pdf"

    private LocalDateTime fechaSubida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket")
    private Ticket ticket;

}
