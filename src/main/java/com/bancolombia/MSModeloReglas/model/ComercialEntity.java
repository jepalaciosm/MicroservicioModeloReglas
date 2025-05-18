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
@Table(name="maestra_comerciales")
public class ComercialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name="documento")
    private String document;
    @Column(name="nombre")
    private String full_name;
    @Column(name="documento_lider")
    private long lider_document;
    @Column(name="nombre_lider")
    private String lider_full_name;
}
