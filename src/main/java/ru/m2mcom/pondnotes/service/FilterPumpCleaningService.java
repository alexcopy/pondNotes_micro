package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.domain.FilterPumpCleaning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing FilterPumpCleaning.
 */
public interface FilterPumpCleaningService {

    /**
     * Save a filterPumpCleaning.
     *
     * @param filterPumpCleaning the entity to save
     * @return the persisted entity
     */
    FilterPumpCleaning save(FilterPumpCleaning filterPumpCleaning);

    /**
     *  Get all the filterPumpCleanings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FilterPumpCleaning> findAll(Pageable pageable);

    /**
     *  Get the "id" filterPumpCleaning.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FilterPumpCleaning findOne(Long id);

    /**
     *  Delete the "id" filterPumpCleaning.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the filterPumpCleaning corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FilterPumpCleaning> search(String query, Pageable pageable);
}
