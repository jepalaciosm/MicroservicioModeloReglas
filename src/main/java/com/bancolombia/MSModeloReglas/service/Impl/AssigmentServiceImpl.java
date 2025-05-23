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
    public ResponseEntity<?> AssignClient(long document, int test) {
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
                    assigmentResultRepository.save(new AssigmentResultEntity(client.getDocument(), client.getFull_name(), rule.getIdComercialAsignado(), comercialRepository.findByDocument(rule.getIdComercialAsignado()).get().getFull_name(), rule.getId(), rule.getNombreRegla()));
                    if (test==1){return ResponseEntity.ok(rule.getIdComercialAsignado());}
                    return ResponseEntity.ok(comercialRepository.findByDocument(rule.getIdComercialAsignado()));
                }
            }
            // assigmentResultRepository.save(new AssigmentResultEntity(client.getDocument(), client.getFull_name()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ninguna regla activa para asignar el cliente queda sin comercial");
    
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
    }

}
