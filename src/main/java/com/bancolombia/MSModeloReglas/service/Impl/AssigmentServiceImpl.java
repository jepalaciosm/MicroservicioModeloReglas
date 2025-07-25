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
            List<RulesEntity> activeRules = rulesServiceImpl.findAllRulesActive(); 
            if (activeRules.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ninguna regla activa para asignar el cliente queda sin comercial");
            }            
            for (RulesEntity rule : activeRules) {
                if (rulesServiceImpl.OKRule(client, rule)) {

                    System.out.println("Cumplio la regla "+rule.getId()+" con el comercial "+rule.getIdComercialAsignado());
                    try {                        
                        if (comercialRepository.findByDocument(rule.getIdComercialAsignado()).isPresent()) {
                            AssigmentResultEntity assigment = new AssigmentResultEntity(client.getDocument(), client.getFull_name(), rule.getIdComercialAsignado(), comercialRepository.findByDocument(rule.getIdComercialAsignado()).get().getFull_name(), rule.getId());
                            assigmentResultRepository.save(assigment);
                            // if (test==1){return ResponseEntity.ok(rule.getIdComercialAsignado());}
                            
                            System.out.println("Asignación exitosa para el cliente: " + client.getFull_name() );
                            return ResponseEntity.ok(assigment);
                        } else {
                            AssigmentResultEntity result = new AssigmentResultEntity(client.getDocument(), client.getFull_name());
                            saveAssigmentResult(result);
                            System.out.println("Comercial no encontrado para el documento: " + rule.getIdComercialAsignado());
                            
                        }
                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al asignar el comercial al cliente: " + e.getMessage());
                    }
                    
                }
            }
            assigmentResultRepository.save(new AssigmentResultEntity(client.getDocument(), client.getFull_name()));
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
            System.out.println("*******Asignando cliente: " + client.getFull_name()+"***********");
            ResponseEntity<?> response = AssignClient(client.getDocument());
            
            System.out.println("Asignación para cliente " + client.getFull_name() );
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
