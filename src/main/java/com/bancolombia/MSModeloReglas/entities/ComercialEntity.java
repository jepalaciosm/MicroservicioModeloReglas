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

    private String lider;

    

    public ComercialEntity() {
    }

    public ComercialEntity(long id, String name, String lider) {
        this.id = id;
        this.name = name;
        this.lider = lider;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ComercialEntity [id=" + id + ", name=" + name + ", lider=" + lider + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLider() {
        return lider;
    }

    public void setLider(String lider) {
        this.lider = lider;
    }

    
    

    
}
