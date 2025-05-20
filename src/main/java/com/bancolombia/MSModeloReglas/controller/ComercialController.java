package com.bancolombia.MSModeloReglas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.MSModeloReglas.model.ComercialEntity;
import com.bancolombia.MSModeloReglas.service.IComercialService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/asignacionclientes/comercial")
@AllArgsConstructor
public class ComercialController {
    private final IComercialService comercialService;

    // Implement the endpoints for CRUD operations here
    @PostMapping("/create")    
    public ResponseEntity<?> createComercial(@RequestBody ComercialEntity comercial) {
         return comercialService.saveComercial(comercial);    
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findComercialById(@RequestBody Long id) {
        return comercialService.findComercialById(id);
    }

    @GetMapping("/findByDocument/{document}")
    public ResponseEntity<?> findComercialByDocument(@PathVariable("document") Long document) {
        return comercialService.findComercialByDocument(document);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComercial(@PathVariable("id") Long id) {
        return comercialService.deleteComercial(id);
    }
}
