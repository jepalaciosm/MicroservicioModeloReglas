package com.bancolombia.MSModeloReglas.service;


import org.springframework.http.ResponseEntity;
import com.bancolombia.MSModeloReglas.model.ComercialEntity;

public interface IComercialService {
    ResponseEntity<?> saveComercial(ComercialEntity comercial);
    ResponseEntity<?> deleteComercial(Long id);    
    ResponseEntity<?> updateComercial(Long id, ComercialEntity comercial);
    ResponseEntity<?> findComercialById(Long id);
    ResponseEntity<?> findComercialByDocument(long document);
    ResponseEntity<?> findAllComerciales();
    String getFullName(long document);
    // String nombreComercial();
    // List<ComercialEntity> imprimirComerciales();
    // ComercialEntity crearComercial (String name, long cedula, String lider);

    //     // In IComercialService.java
    // ComercialEntity actualizarComercial(long cedula, ComercialEntity comercial);
    // boolean eliminarComercial(long cedula);
}

