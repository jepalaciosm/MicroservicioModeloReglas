package com.bancolombia.MSModeloReglas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.MSModeloReglas.model.ClientEntity;
import com.bancolombia.MSModeloReglas.service.IClientService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/asignacionclientes/client")
@AllArgsConstructor
public class ClientController {
    private final IClientService clientService;
    
    // Implement the endpoints for CRUD operations here
    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestBody ClientEntity client) {
        return clientService.saveClient(client); 
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAllClients() {
        return clientService.findAllClients();
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findClientById(@RequestBody Long id) {
        return clientService.findClientById(id);
    }

    @GetMapping("/findByDocument/{document}")
    public ResponseEntity<?> findClientByDocument(@PathVariable("document") Long document) {
        return clientService.findClientByDocument(document);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable("id") Long id) {
        return clientService.deleteClient(id);
    }


}

    
