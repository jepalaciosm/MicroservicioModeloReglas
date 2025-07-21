package com.bancolombia.MSModeloReglas.service;

import org.springframework.http.ResponseEntity;
import com.bancolombia.MSModeloReglas.model.AssigmentResultEntity;

public interface IAssigmentService {
    ResponseEntity<?> AssignClient(long document);
    ResponseEntity<?> assignAllClients();
    ResponseEntity<?> saveAssigmentResult(AssigmentResultEntity assigmentResult);
    ResponseEntity<?> deleteAllAssigments();
    ResponseEntity<?> findAll();

}
