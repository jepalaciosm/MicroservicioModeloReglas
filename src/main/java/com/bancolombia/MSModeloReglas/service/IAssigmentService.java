package com.bancolombia.MSModeloReglas.service;

import org.springframework.http.ResponseEntity;

public interface IAssigmentService {
    ResponseEntity<?> AssignClient(long document, int test);

}
