package com.bancolombia.MSModeloReglas.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Entidad para los Clientes (simplificada para el ejemplo)
// En un caso real, esta entidad tendría muchos más campos.
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;
    private String ubicacionGeografica; // Ej: "Zona Norte", "Ciudad Capital", "Region Sur"
    private String tipoCliente; // Ej: "PYME", "Corporativo", "Individual"
    private Double volumenCompraAnual; // Ej: 15000.00
    private String sectorIndustrial; // Ej: "Tecnologia", "Salud", "Educacion"

    @ManyToOne
    @JoinColumn(name = "comercial_asignado_id")
    private Comercial comercialAsignado; // Comercial que finalmente se le asigna
    
    // Constructor para facilitar la creación en DTOs o pruebas
    public Cliente(String nombreCompleto, String ubicacionGeografica, String tipoCliente, Double volumenCompraAnual, String sectorIndustrial) {
        this.nombreCompleto = nombreCompleto;
        this.ubicacionGeografica = ubicacionGeografica;
        this.tipoCliente = tipoCliente;
        this.volumenCompraAnual = volumenCompraAnual;
        this.sectorIndustrial = sectorIndustrial;
    }
}
