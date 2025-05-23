package com.bancolombia.MSModeloReglas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.MSModeloReglas.service.IClientService;
import com.bancolombia.MSModeloReglas.service.IRulesService;

import lombok.AllArgsConstructor;

import java.util.List;

import com.bancolombia.MSModeloReglas.model.ClientEntity;
import com.bancolombia.MSModeloReglas.model.RulesEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/asignacionclientes/reglas")
@AllArgsConstructor
public class RulesController {
    private final IRulesService rulesService;
    private final IClientService clientService;

    @GetMapping("/findAll")
    public List<RulesEntity> findActiveRules() {
        return rulesService.findAllRulesActive();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRules(@RequestBody RulesEntity reglas) {
        return rulesService.saveRules(reglas);
    }
    
    @GetMapping("/findBySegment")
    public List<RulesEntity> getMethodName(@RequestBody String segmento) {
        return rulesService.findAllRulesActiveBySegment(segmento);
    }
    
    
    @GetMapping("/validate")
    public ResponseEntity<?> findRulesByClient(@PathVariable("id") Long id) {
        ResponseEntity<?> response =clientService.findClientById(id);
        ResponseEntity<?> response1 =rulesService.findRulesByID(id);
        if (response.getStatusCode().is2xxSuccessful() && response1.getStatusCode().is2xxSuccessful()) {
            ClientEntity client = (ClientEntity) response.getBody();
            RulesEntity rules = (RulesEntity) response1.getBody();
            boolean isOK = rulesService.OKRule(client, rules);
            
            return ResponseEntity.ok(isOK);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Error al validar reglas");
        }
        
    }
    

}
