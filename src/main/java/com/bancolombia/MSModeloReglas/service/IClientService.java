package com.bancolombia.MSModeloReglas.service;

import com.bancolombia.MSModeloReglas.entities.ClientEntity;


import java.util.Optional;

import org.springframework.http.ResponseEntity;

public interface IClientService {
    public ResponseEntity<?> guardarCliente(ClientEntity cliente);
    // public Optional<ClientEntity> buscarPorId(Long id);
}

