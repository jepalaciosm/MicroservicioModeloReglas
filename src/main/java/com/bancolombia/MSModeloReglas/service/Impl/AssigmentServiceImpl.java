package com.bancolombia.MSModeloReglas.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bancolombia.MSModeloReglas.model.ClientEntity;
import com.bancolombia.MSModeloReglas.model.RulesEntity;
import com.bancolombia.MSModeloReglas.repositories.IAssigmentResultRepository;
import com.bancolombia.MSModeloReglas.repositories.IClientRepository;
import com.bancolombia.MSModeloReglas.repositories.IComercialRepository;
import com.bancolombia.MSModeloReglas.repositories.IRulesRepository;
import com.bancolombia.MSModeloReglas.service.IAssigmentService;
import com.bancolombia.MSModeloReglas.model.AssigmentResultEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AssigmentServiceImpl implements IAssigmentService {
    private final IRulesRepository rulesRepository;
    private final IComercialRepository comercialRepository;
    private final IClientRepository clientRepository;
    private final RulesServiceImpl rulesServiceImpl;
    private final IAssigmentResultRepository assigmentResultRepository;
   
    @Override
    public ResponseEntity<?> AssignClient(long document) {
        Optional<ClientEntity> response = clientRepository.findByDocument(document);
        if (response.isPresent()) {
            ClientEntity client = response.get();                        
            List<RulesEntity> activeRules = rulesServiceImpl.findAllRulesActiveBySegment(client.getSegment()); 
            if (activeRules.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ninguna regla activa para asignar el cliente queda sin comercial");
            }            
            for (RulesEntity rule : activeRules) {
                if (rulesServiceImpl.OKRule(client, rule)) {
                    // Asignar el comercial al cliente
                    //client.setIdComercialAsignado(rule.getIdComercialAsignado());
                    // Guardar el cliente actualizado en la base de datos
                    // clientRepository.save(client);
                    if (comercialRepository.findByDocument(rule.getIdComercialAsignado()).isPresent()) {
                        assigmentResultRepository.save(new AssigmentResultEntity(client.getDocument(), client.getFull_name(), rule.getIdComercialAsignado(), comercialRepository.findByDocument(rule.getIdComercialAsignado()).get().getFull_name(), rule.getId()));
                        // if (test==1){return ResponseEntity.ok(rule.getIdComercialAsignado());}
                        return ResponseEntity.ok(comercialRepository.findByDocument(rule.getIdComercialAsignado()));
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el comercial asignado");
                    }
                    
                }
            }
            // assigmentResultRepository.save(new AssigmentResultEntity(client.getDocument(), client.getFull_name()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ninguna regla activa para asignar el cliente queda sin comercial");
    
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
    }

    @Override
    public ResponseEntity<?> assignAllClients() {
        deleteAllAssigments();
        List<ClientEntity> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay clientes para asignar");
        }
        
        for (ClientEntity client : clients) {
            ResponseEntity<?> response = AssignClient(client.getDocument());
            if (!response.getStatusCode().is2xxSuccessful()) {
                //creamos registro de asignacion
                AssigmentResultEntity result = new AssigmentResultEntity(client.getDocument(), client.getFull_name());
                saveAssigmentResult(result);
            }else {
                // Si la asignación fue exitosa, guardamos el resultado
                Object responseBody = response.getBody();
                Long comercialDocument = null;
                String comercialFullName = null;
                if (responseBody instanceof Optional) {
                    Optional<?> comercialOptional = (Optional<?>) responseBody;
                    if (comercialOptional.isPresent()) {
                        Object comercialObj = comercialOptional.get();
                        // Assuming comercialObj is of type ComercialEntity
                        comercialDocument = ((com.bancolombia.MSModeloReglas.model.ComercialEntity) comercialObj).getDocument();
                        comercialFullName = ((com.bancolombia.MSModeloReglas.model.ComercialEntity) comercialObj).getFull_name();
                    }
                }
                AssigmentResultEntity result = new AssigmentResultEntity(client.getDocument(), client.getFull_name(),
                        comercialDocument, comercialFullName,
                        -1);
                saveAssigmentResult(result);
            }
        }
        
        return ResponseEntity.ok("Todos los clientes han sido asignados correctamente");
    }

    @Override
    public ResponseEntity<?> saveAssigmentResult(AssigmentResultEntity assigmentResult) {
        AssigmentResultEntity savedResult = assigmentResultRepository.save(assigmentResult);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResult);
    }

    @Override
    public ResponseEntity<?> deleteAllAssigments() {
        assigmentResultRepository.deleteAll();
        return ResponseEntity.ok("All assignments deleted successfully");
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<AssigmentResultEntity> results = assigmentResultRepository.findAll();
        if (results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No assignments found");
        }
        return ResponseEntity.ok(results);
    }

}
