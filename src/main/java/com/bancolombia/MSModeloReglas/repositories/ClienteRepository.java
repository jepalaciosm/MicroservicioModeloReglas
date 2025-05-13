package com.bancolombia.MSModeloReglas.repositories;

import com.bancolombia.MSModeloReglas.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
} 