package com.bancolombia.MSModeloReglas.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;
    private String ubicacionGeografica; // Ej: "Zona Norte", "Ciudad Capital", "Region Sur"
    private String tipoCliente; // Ej: "PYME", "Corporativo", "Individual"
    private Double volumenCompraAnual; // Ej: 15000.00
    private String sectorIndustrial; // Ej: "Tecnologia", "Salud", "Educacion"

    
}
