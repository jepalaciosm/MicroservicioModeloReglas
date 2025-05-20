package com.bancolombia.MSModeloReglas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bancolombia.MSModeloReglas.model.ReglasEntity;
import java.util.List;

public interface IReglasRepository extends JpaRepository<ReglasEntity, Long>{
    List<ReglasEntity> findByActivaTrueOrderByPrioridadAsc();

    @Query("select r from Reglas r where r.segmento = ?1 and r.activa=True order by r.prioridad asc")
    List<ReglasEntity> findActiveRulesBySegmentAsc(String segmento);
}
