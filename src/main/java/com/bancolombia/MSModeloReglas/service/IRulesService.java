package com.bancolombia.MSModeloReglas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bancolombia.MSModeloReglas.model.ClientEntity;
import com.bancolombia.MSModeloReglas.model.RulesEntity;

public interface IRulesService {
    ResponseEntity<?> saveRules(RulesEntity rules);
    // ResponseEntity<?> AssignClient(ClientEntity client);
    boolean OKRule(ClientEntity client, RulesEntity rules);
    List<RulesEntity> findAllRulesActive();
    ResponseEntity<?> findRulesByID(Long id);
}
