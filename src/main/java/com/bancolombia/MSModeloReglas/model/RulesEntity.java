package com.bancolombia.MSModeloReglas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "maestra_reglas")
public class RulesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreRegla;

    @Column(nullable = false)
    private int prioridad; // Menor número = mayor prioridad

    // @Column(nullable = false)
    // private String segmento;

    @Column(nullable = false)
    private String campoCliente1; // Nombre del campo en la entidad Cliente (o DTO) a evaluar. Ej: "ubicacionGeografica"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperadorLogico operador1;

    @Column(nullable = false)
    private String valorCondicion1; // Valor contra el cual se compara el campo del cliente

     @Column(nullable = true)
    private String campoCliente2; // Nombre del campo en la entidad Cliente (o DTO) a evaluar. Ej: "ubicacionGeografica"

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private OperadorLogico operador2;

    @Column(nullable = true)
    private String valorCondicion2;

     @Column(nullable = true)
    private String campoCliente3; // Nombre del campo en la entidad Cliente (o DTO) a evaluar. Ej: "ubicacionGeografica"

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private OperadorLogico operador3;

    @Column(nullable = true)
    private String valorCondicion3;

    @Column(nullable = false)
    private Long idComercialAsignado; // FK (conceptual) al Comercial que se asignará si la regla se cumple

    private boolean activa = true;

    @Column(length = 500)
    private String descripcionNoTecnica;

    // Enum para los operadores lógicos de las reglas
    public enum OperadorLogico {
        IGUAL_A,          // ==
        NO_IGUAL_A,       // !=
        MAYOR_QUE,        // >
        MENOR_QUE,        // <
        MAYOR_O_IGUAL_QUE, // >=
        MENOR_O_IGUAL_QUE, // <=
        CONTIENE,         // String.contains()
        NO_CONTIENE,      // !String.contains()
        EN_LISTA,         // Valor está en una lista separada por comas (ej: "A,B,C")
        NO_EN_LISTA,      // Valor NO está en una lista separada por comas
        NO_APLICA        // No aplica, se usa para condiciones opcionales
    }


}
