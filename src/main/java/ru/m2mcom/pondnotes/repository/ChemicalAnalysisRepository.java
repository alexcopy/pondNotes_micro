package ru.m2mcom.pondnotes.repository;

import ru.m2mcom.pondnotes.domain.ChemicalAnalysis;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ChemicalAnalysis entity.
 */
@SuppressWarnings("unused")
public interface ChemicalAnalysisRepository extends JpaRepository<ChemicalAnalysis,Long> {

    @Query("select distinct chemicalAnalysis from ChemicalAnalysis chemicalAnalysis left join fetch chemicalAnalysis.tanks")
    List<ChemicalAnalysis> findAllWithEagerRelationships();

    @Query("select chemicalAnalysis from ChemicalAnalysis chemicalAnalysis left join fetch chemicalAnalysis.tanks where chemicalAnalysis.id =:id")
    ChemicalAnalysis findOneWithEagerRelationships(@Param("id") Long id);

}
