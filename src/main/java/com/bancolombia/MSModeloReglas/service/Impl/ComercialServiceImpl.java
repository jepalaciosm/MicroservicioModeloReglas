package com.bancolombia.MSModeloReglas.service.Impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bancolombia.MSModeloReglas.model.ComercialEntity;
import com.bancolombia.MSModeloReglas.repositories.IComercialRepository;
import com.bancolombia.MSModeloReglas.service.IComercialService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ComercialServiceImpl implements IComercialService {
    private final IComercialRepository comercialRepository;

    @Override
    public ResponseEntity<?> saveComercial(ComercialEntity comercial) {
        var newComercial = this.comercialRepository.save(comercial);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComercial);
    }

    @Override
    public ResponseEntity<?> deleteComercial(Long id) {
        if (comercialRepository.existsById(id)) {
            comercialRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Comercial deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comercial not found");
        }
    }

    @Override
    public ResponseEntity<?> updateComercial(Long id, ComercialEntity comercial) {
        if (comercialRepository.existsById(id)) {
            comercial.setId(id);
            ComercialEntity updatedComercial = comercialRepository.save(comercial);
            return ResponseEntity.status(HttpStatus.OK).body(updatedComercial);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comercial not found");
        }
    }

    @Override
    public ResponseEntity<?> findComercialById(Long id) {
        var comercial = comercialRepository.findById(id);
        if (comercial.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(comercial.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comercial not found");
        }
    }

    @Override
    public ResponseEntity<?> findComercialByDocument(long id) {
        Optional<ComercialEntity> comercial = comercialRepository.findByDocument(id);
        if (comercial.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(comercial.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comercial not found");
        }
    }

    @Override
    public ResponseEntity<?> findAllComerciales() {
        var comerciales = comercialRepository.findAll();
        if (comerciales.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Comerciales found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(comerciales);
        }
    }

    @Override
    public String getFullName(long id) {
        Optional<ComercialEntity> comercial = comercialRepository.findByDocument(id);
        if (comercial.isPresent()) {
            return comercial.get().getFull_name();
        } else {
            return "";
        }
    }
   

}
