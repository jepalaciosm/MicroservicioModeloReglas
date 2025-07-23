package com.bancolombia.MSModeloReglas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bancolombia.MSModeloReglas.model.ClientEntity;
import com.bancolombia.MSModeloReglas.model.RulesEntity;
import com.bancolombia.MSModeloReglas.model.RulesEntity.OperadorLogico;

public interface IRulesService {
    ResponseEntity<?> saveRules(RulesEntity rules);
    //ResponseEntity<?> AssignClient(long document);
    boolean OKRule(ClientEntity client, RulesEntity rules);
    boolean valideCondition(Object valorCliente, String valorCondicion, OperadorLogico operador);
    List<RulesEntity> findAllRules();
    List<RulesEntity> findAllRulesActive();
    List<RulesEntity> findAllRulesActiveBySegment(String segment);
    ResponseEntity<?> findRulesByID(Long id);
    ResponseEntity<?> DeleteRule(Long id);
    
}
