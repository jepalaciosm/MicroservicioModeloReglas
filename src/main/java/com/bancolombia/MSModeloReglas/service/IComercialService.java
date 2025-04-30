package com.bancolombia.MSModeloReglas.service;
import com.bancolombia.MSModeloReglas.entities.ComercialEntity;
import java.util.List;

public interface IComercialService {
    String nombreComercial();
    List<ComercialEntity> imprimirComerciales();
}

