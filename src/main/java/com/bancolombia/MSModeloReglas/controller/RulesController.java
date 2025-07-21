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
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping("/findAllAcitive")
    public List<RulesEntity> findActiveRules() {
        return rulesService.findAllRulesActive();
    }

    @GetMapping("/findAll")
    public List<RulesEntity> findAllRules() {
        return rulesService.findAllRules();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRules(@RequestBody RulesEntity reglas) {
        List<RulesEntity> activeRueles = rulesService.findAllRulesActive();
        if (activeRueles.stream().anyMatch(rule -> rule.getPrioridad()==(reglas.getPrioridad()))
                ) {
            return ResponseEntity.badRequest().body("Ya existe una regla activa con la misma prioridad");
        }
        return rulesService.saveRules(reglas);
    }
    
    @GetMapping("/findBySegment")
    public List<RulesEntity> getMethodName(@RequestBody String segmento) {
        return rulesService.findAllRulesActiveBySegment(segmento);
    }
    
    
    @GetMapping("/validate/{idCliente}/{idRegla}")
    public ResponseEntity<?> findRulesByClient(
            @PathVariable("idCliente") Long idCliente,
            @PathVariable("idRegla") Long idRegla) {
        ResponseEntity<?> response = clientService.findClientById(idCliente);
        ResponseEntity<?> response1 = rulesService.findRulesByID(idRegla);
        if (response.getStatusCode().is2xxSuccessful() && response1.getStatusCode().is2xxSuccessful()) {
            ClientEntity client = (ClientEntity) response.getBody();
            RulesEntity rules = (RulesEntity) response1.getBody();
            boolean isOK = rulesService.OKRule(client, rules);
            
            return ResponseEntity.ok(isOK);
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Error al validar reglas");
        }
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRule(@PathVariable("id") Long id) {
        if (!rulesService.findRulesByID(id).getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(404).body("Regla no encontrada");
        }
        return rulesService.DeleteRule(id);
    }   
    

}
