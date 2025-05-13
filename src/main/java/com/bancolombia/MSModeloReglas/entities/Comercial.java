package com.bancolombia.MSModeloReglas.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Entidad para los Comerciales
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comerciales")
public class Comercial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    // Otros campos relevantes para un comercial
}
