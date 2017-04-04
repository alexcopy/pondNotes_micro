package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.ChemicalsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Chemicals.
 */
public interface ChemicalsService {

    /**
     * Save a chemicals.
     *
     * @param chemicalsDTO the entity to save
     * @return the persisted entity
     */
    ChemicalsDTO save(ChemicalsDTO chemicalsDTO);

    /**
     *  Get all the chemicals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ChemicalsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" chemicals.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ChemicalsDTO findOne(Long id);

    /**
     *  Delete the "id" chemicals.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the chemicals corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ChemicalsDTO> search(String query, Pageable pageable);
}
