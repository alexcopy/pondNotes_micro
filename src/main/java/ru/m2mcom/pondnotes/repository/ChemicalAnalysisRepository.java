package ru.m2mcom.pondnotes.repository;

import ru.m2mcom.pondnotes.domain.ChemicalAnalysis;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ChemicalAnalysis entity.
 */
@SuppressWarnings("unused")
public interface ChemicalAnalysisRepository extends JpaRepository<ChemicalAnalysis,Long> {

}
