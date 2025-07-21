package com.bancolombia.MSModeloReglas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="resultado_asignacion")
public class AssigmentResultEntity {
    @Id
    @Column(name="documento_cliente")
    private long client_document;
    @Column(name="nombreCompleto_cliente")
    private String client_full_name;
    @Column(name="documento_comercial")
    private long comercial_document;
    @Column(name="nombre_comercial")
    private String comercial_full_name;
    @Column(name="id_regla")
    private long id;
   
    public AssigmentResultEntity(Long client_document, String client_full_name) {
        this.client_document = client_document;
        this.client_full_name = client_full_name;
        this.comercial_document = -1;
        this.comercial_full_name = "";  
        this.id = -1;
        
    }

    
}
