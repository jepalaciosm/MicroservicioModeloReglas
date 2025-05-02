package com.bancolombia.MSModeloReglas.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="maestra_comercial")
public class ComercialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name="nombre_comercial")
    private String name;
    private long cedula;
    private String lider;


    

    public ComercialEntity() {
    }

    




    public ComercialEntity(long id, String name, long cedula, String lider) {
        this.id = id;
        this.name = name;
        this.cedula = cedula;
        this.lider = lider;
    }

    public ComercialEntity( String name, long cedula, String lider) {        
        this.name = name;
        this.cedula = cedula;
        this.lider = lider;
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }




    public String getName() {
        return name;
    }




    public void setName(String name) {
        this.name = name;
    }




    public long getCedula() {
        return cedula;
    }




    public void setCedula(long cedula) {
        this.cedula = cedula;
    }




    public String getLider() {
        return lider;
    }




    public void setLider(String lider) {
        this.lider = lider;
    }

    
    
    

    
}
