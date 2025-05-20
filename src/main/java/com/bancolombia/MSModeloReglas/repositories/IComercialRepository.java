package com.bancolombia.MSModeloReglas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bancolombia.MSModeloReglas.model.ComercialEntity;

import java.util.Optional;

public interface IComercialRepository extends JpaRepository<ComercialEntity, Long> {
    Optional<ComercialEntity> findByDocument(long  document);
}
