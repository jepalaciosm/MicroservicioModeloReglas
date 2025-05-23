package com.bancolombia.MSModeloReglas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bancolombia.MSModeloReglas.model.ClientEntity;
import com.bancolombia.MSModeloReglas.model.RulesEntity;

public interface IRulesService {
    ResponseEntity<?> saveRules(RulesEntity rules);
    //ResponseEntity<?> AssignClient(long document);
    boolean OKRule(ClientEntity client, RulesEntity rules);
    List<RulesEntity> findAllRulesActive();
    List<RulesEntity> findAllRulesActiveBySegment(String segment);
    ResponseEntity<?> findRulesByID(Long id);
    
}
