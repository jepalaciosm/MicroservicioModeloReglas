package com.bancolombia.MSModeloReglas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancolombia.MSModeloReglas.service.IAssigmentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/asignacionclientes/")
@AllArgsConstructor
public class AssigmentController {
    private final IAssigmentService assigmentService;
    
    @GetMapping("/asignClient") 
    public ResponseEntity<?> assignClient(@RequestBody Long document) {
        if (document == null) {
            return ResponseEntity.badRequest().body("Document cannot be null");
        }
        return assigmentService.AssignClient(document);
    }
    
    @GetMapping("/asignAllClients")
    public ResponseEntity<?> asignAllClients() {
        return assigmentService.assignAllClients();
    }

    @GetMapping("/findAllAssigments")
    public ResponseEntity<?> findAllAssigments() {
        return assigmentService.findAll();
    }
    
    
    
    @GetMapping("/test")
    public ResponseEntity<?> test(@RequestBody String name) {
        return ResponseEntity.ok("Hello " + name);
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findClientById(@RequestBody Long id) {
        return ResponseEntity.ok("Hello " + id);
    }

}
