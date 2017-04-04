package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.FilterPumpCleaningDTO;
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
     * @param filterPumpCleaningDTO the entity to save
     * @return the persisted entity
     */
    FilterPumpCleaningDTO save(FilterPumpCleaningDTO filterPumpCleaningDTO);

    /**
     *  Get all the filterPumpCleanings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FilterPumpCleaningDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" filterPumpCleaning.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FilterPumpCleaningDTO findOne(Long id);

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
    Page<FilterPumpCleaningDTO> search(String query, Pageable pageable);
}
