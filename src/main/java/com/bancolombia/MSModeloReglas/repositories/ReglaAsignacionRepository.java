package com.bancolombia.MSModeloReglas.repositories;

import com.bancolombia.MSModeloReglas.entities.ReglaAsignacion;

import java.util.List;


@Repository
public interface ReglaAsignacionRepository extends JpaRepository<ReglaAsignacion, Long> {
    // Busca todas las reglas activas, ordenadas por prioridad ascendente (menor número primero)
    List<ReglaAsignacion> findByActivaTrueOrderByPrioridadAsc();
}