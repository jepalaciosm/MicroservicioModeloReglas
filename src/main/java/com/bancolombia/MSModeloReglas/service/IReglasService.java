package com.bancolombia.MSModeloReglas.service;

import org.springframework.http.ResponseEntity;

import com.bancolombia.MSModeloReglas.model.ClientEntity;

public interface IReglasService {
    ResponseEntity<?> AssignClient(ClientEntity client);
}
