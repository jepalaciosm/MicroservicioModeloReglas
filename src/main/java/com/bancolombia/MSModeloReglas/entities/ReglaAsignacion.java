package com.bancolombia.MSModeloReglas.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


// Enum para los operadores lógicos de las reglas
enum OperadorLogico {
    IGUAL_A,          // ==
    NO_IGUAL_A,       // !=
    MAYOR_QUE,        // >
    MENOR_QUE,        // <
    MAYOR_O_IGUAL_QUE, // >=
    MENOR_O_IGUAL_QUE, // <=
    CONTIENE,         // String.contains()
    NO_CONTIENE,      // !String.contains()
    EN_LISTA,         // Valor está en una lista separada por comas (ej: "A,B,C")
    NO_EN_LISTA      // Valor NO está en una lista separada por comas
}

// Entidad para las Reglas de Asignación
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reglas_asignacion")
public class ReglaAsignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreRegla;

    @Column(nullable = false)
    private int prioridad; // Menor número = mayor prioridad

    @Column(nullable = false)
    private String campoCliente; // Nombre del campo en la entidad Cliente (o DTO) a evaluar. Ej: "ubicacionGeografica"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperadorLogico operador;

    @Column(nullable = false)
    private String valorCondicion; // Valor contra el cual se compara el campo del cliente

    @Column(nullable = false)
    private Long idComercialAsignado; // FK (conceptual) al Comercial que se asignará si la regla se cumple

    private boolean activa = true;

    @Column(length = 500)
    private String descripcionNoTecnica;
}
