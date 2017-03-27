package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.domain.TempMeter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing TempMeter.
 */
public interface TempMeterService {

    /**
     * Save a tempMeter.
     *
     * @param tempMeter the entity to save
     * @return the persisted entity
     */
    TempMeter save(TempMeter tempMeter);

    /**
     *  Get all the tempMeters.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TempMeter> findAll(Pageable pageable);

    /**
     *  Get the "id" tempMeter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TempMeter findOne(Long id);

    /**
     *  Delete the "id" tempMeter.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tempMeter corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TempMeter> search(String query, Pageable pageable);
}
