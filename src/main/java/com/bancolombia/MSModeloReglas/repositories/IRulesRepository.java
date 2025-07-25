package com.bancolombia.MSModeloReglas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bancolombia.MSModeloReglas.model.RulesEntity;
import java.util.List;

public interface IRulesRepository extends JpaRepository<RulesEntity, Long>{
    List<RulesEntity> findByActivaTrueOrderByPrioridadAsc();

    @Query("select r from RulesEntity r where r.producto = ?1 and r.activa=true order by r.prioridad asc")
    List<RulesEntity> findActiveRulesByProductAsc(String producto);

    @Query("select r from RulesEntity r where r.activa=true order by r.prioridad asc")
    List<RulesEntity> findActiveRulesBySegmentAsc(String segmento);
}
