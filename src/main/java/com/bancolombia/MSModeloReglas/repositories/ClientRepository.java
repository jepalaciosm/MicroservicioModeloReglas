package com.bancolombia.MSModeloReglas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.bancolombia.MSModeloReglas.entities.ClientEntity;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository <ClientEntity, Long> {
    //jpa methods
    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

   // Optional<ClientEntity> findByDocument(String  document);
}



