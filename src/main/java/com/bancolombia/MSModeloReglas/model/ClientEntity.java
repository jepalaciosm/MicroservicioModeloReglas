package com.bancolombia.MSModeloReglas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "maestra_clientes")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="documento")
    private Long document;
    @Column(name="nombreCompleto")
    private String full_name;
    @Column(name="region")
    private String region; // Ej: "Zona Norte", "Ciudad Capital", "Region Sur"
    @Column(name="cod_sucursal")
    private Integer cod_office;
    @Column(name="sucursal")
    private String office;
    @Column(name="segmento")
    private String segment; // Ej: "PYME", "Corporativo", "Empresarial"
    @Column(name="cargo")
    private String position;

    
}
