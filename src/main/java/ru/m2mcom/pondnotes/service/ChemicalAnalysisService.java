package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.domain.ChemicalAnalysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing ChemicalAnalysis.
 */
public interface ChemicalAnalysisService {

    /**
     * Save a chemicalAnalysis.
     *
     * @param chemicalAnalysis the entity to save
     * @return the persisted entity
     */
    ChemicalAnalysis save(ChemicalAnalysis chemicalAnalysis);

    /**
     *  Get all the chemicalAnalyses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ChemicalAnalysis> findAll(Pageable pageable);

    /**
     *  Get the "id" chemicalAnalysis.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChemicalAnalysis findOne(Long id);

    /**
     *  Delete the "id" chemicalAnalysis.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chemicalAnalysis corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ChemicalAnalysis> search(String query, Pageable pageable);
}
