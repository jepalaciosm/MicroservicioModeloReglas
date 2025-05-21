package com.bancolombia.MSModeloReglas.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bancolombia.MSModeloReglas.model.RulesEntity;
import com.bancolombia.MSModeloReglas.repositories.IRulesRepository;
import com.bancolombia.MSModeloReglas.service.IRulesService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RulesServiceImpl  implements  IRulesService{
    private final IRulesRepository rulesRepository;
    // private final IComercialService comercialService;
    // private final IComercialRepository comercialRepository;
    // private final IClientRepository clientRepository;
    // private final IClientService clientService;

    @Override
    public ResponseEntity<?> saveRules(RulesEntity reglas) {
        var newReglas = this.rulesRepository.save(reglas);
        return ResponseEntity.status(HttpStatus.CREATED).body(newReglas);
    }

    // @Override
    // public ResponseEntity<?> AssignClient(ClientEntity client) {
    //     var newClient = this.clientRepository.save(client);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    // }

    @Override
    public List<RulesEntity> findAllRulesActive() {
        return this.rulesRepository.findByActivaTrueOrderByPrioridadAsc()
                .stream()
                .map(rules -> rules)
                .collect(Collectors.toList());
    }

}
