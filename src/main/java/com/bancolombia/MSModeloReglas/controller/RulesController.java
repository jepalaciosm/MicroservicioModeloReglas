package com.bancolombia.MSModeloReglas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.MSModeloReglas.service.IRulesService;

import lombok.AllArgsConstructor;

import java.util.List;
import com.bancolombia.MSModeloReglas.model.RulesEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/asignacionclientes/reglas")
@AllArgsConstructor
public class RulesController {
    private final IRulesService rulesService;

    @GetMapping("/findAll")
    public List<RulesEntity> findActiveRules() {
        return rulesService.findAllRulesActive();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRules(@RequestBody RulesEntity reglas) {
        return rulesService.saveRules(reglas);
    }   
    
    

}
