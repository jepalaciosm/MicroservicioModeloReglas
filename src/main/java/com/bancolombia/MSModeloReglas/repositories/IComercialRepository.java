package com.bancolombia.MSModeloReglas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bancolombia.MSModeloReglas.model.ComercialEntity;


public interface IComercialRepository extends JpaRepository<ComercialEntity, Long> {

}
