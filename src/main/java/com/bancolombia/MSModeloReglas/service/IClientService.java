package com.bancolombia.MSModeloReglas.service;

import org.springframework.http.ResponseEntity;
import com.bancolombia.MSModeloReglas.model.ClientEntity;

public interface IClientService {
    ResponseEntity<?> saveClient(ClientEntity client);
    ResponseEntity<?> deleteClient(Long id);
    ResponseEntity<?> updateClient(Long id, ClientEntity client);
    ResponseEntity<?> findClientById(Long id);
    ResponseEntity<?> findClientByDocument(Long id);
    ResponseEntity<?> findAllClients();
    //public ResponseEntity<?> guardarCliente(ClientEntity cliente);
    // public Optional<ClientEntity> buscarPorId(Long id);
}

