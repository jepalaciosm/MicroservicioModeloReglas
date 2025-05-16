package com.bancolombia.MSModeloReglas.service.Impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.bancolombia.MSModeloReglas.entities.ClientEntity;
import com.bancolombia.MSModeloReglas.repositories.ClientRepository;
import com.bancolombia.MSModeloReglas.service.IClientService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClientServiceImpl implements IClientService {
    private final  ClientRepository ClientRepository;

    @Override
    public ResponseEntity<?> guardarCliente(ClientEntity cliente){
        var newCliente = this.ClientRepository.save(cliente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCliente);
    }


   
}


