package com.arelance.helpdesk.modelo;

// ==========================================================
// TODO Ticket:
//   - Atributos: asunto, descripcion, fechaApertura, fechaCierre, prioridad (enum)
//   - Relaciones: @ManyToOne Cliente, @ManyToOne Tecnico, @ManyToOne Categoria
//   - Enum Prioridad: BAJA, MEDIA, ALTA, CRITICA -> @Enumerated(EnumType.STRING)
//   - Logica de calculo de SLA a partir de fechaApertura + objetivos del Contrato

public class Ticket {

}
