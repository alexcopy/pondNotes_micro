package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.ChemicalAnalysisDTO;
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
     * @param chemicalAnalysisDTO the entity to save
     * @return the persisted entity
     */
    ChemicalAnalysisDTO save(ChemicalAnalysisDTO chemicalAnalysisDTO);

    /**
     *  Get all the chemicalAnalyses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ChemicalAnalysisDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" chemicalAnalysis.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChemicalAnalysisDTO findOne(Long id);

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
    Page<ChemicalAnalysisDTO> search(String query, Pageable pageable);
}
