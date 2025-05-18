package com.bancolombia.MSModeloReglas.service.Impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bancolombia.MSModeloReglas.model.ClientEntity;
import com.bancolombia.MSModeloReglas.repositories.IClientRepository;
import com.bancolombia.MSModeloReglas.service.IClientService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements IClientService {
    private final  IClientRepository ClientRepository;

    @Override
    public ResponseEntity<?> saveClient(ClientEntity client) {
        Optional<ClientEntity> existingClient = ClientRepository.findByDocument(client.getDocument());
        if (existingClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Client already exists");
        } else {
            var newUser=this.ClientRepository.save(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }        
    }

    @Override
    public ResponseEntity<?> deleteClient(Long id) {
        Optional<ClientEntity> client = ClientRepository.findById(id);
        if (client.isPresent()) {
            ClientRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Client deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
    }

    @Override
    public ResponseEntity<?> updateClient(Long id, ClientEntity client) {
        Optional<ClientEntity> existingClient = ClientRepository.findById(id);
        if (existingClient.isPresent()) {
            client.setId(id);
            ClientEntity updatedClient = ClientRepository.save(client);
            return ResponseEntity.status(HttpStatus.OK).body(updatedClient);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
    }

    @Override
    public ResponseEntity<?> findClientById(Long id) {
        Optional<ClientEntity> client = ClientRepository.findById(id);
        if (client.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(client.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
    }

    @Override
    public ResponseEntity<?> findClientByDocument(Long id) {
        Optional<ClientEntity> client = ClientRepository.findByDocument(id);
        if (client.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(client.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
    }

    @Override
    public ResponseEntity<?> findAllClients() {
        var clients = ClientRepository.findAll();
        if (clients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No clients found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(clients);
        }
    }
   
}


