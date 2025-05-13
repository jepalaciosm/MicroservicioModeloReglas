package com.bancolombia.MSModeloReglas.service;
import com.bancolombia.MSModeloReglas.entities.ComercialEntity;
import java.util.List;

public interface IComercialService {
    String nombreComercial();
    List<ComercialEntity> imprimirComerciales();
    ComercialEntity crearComercial (String name, long cedula, String lider);

        // In IComercialService.java
    ComercialEntity actualizarComercial(long cedula, ComercialEntity comercial);
    boolean eliminarComercial(long cedula);
}

