package com.bancolombia.MSModeloReglas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bancolombia.MSModeloReglas.model.ClientEntity;

import java.util.Optional;


public interface IClientRepository extends JpaRepository <ClientEntity, Long> {
    //jpa methods
    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    Optional<ClientEntity> findByDocument(Long  document);

}



