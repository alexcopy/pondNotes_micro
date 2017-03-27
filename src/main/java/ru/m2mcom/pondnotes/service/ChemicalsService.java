package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.domain.Chemicals;
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
     * @param chemicals the entity to save
     * @return the persisted entity
     */
    Chemicals save(Chemicals chemicals);

    /**
     *  Get all the chemicals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Chemicals> findAll(Pageable pageable);

    /**
     *  Get the "id" chemicals.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Chemicals findOne(Long id);

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
    Page<Chemicals> search(String query, Pageable pageable);
}
