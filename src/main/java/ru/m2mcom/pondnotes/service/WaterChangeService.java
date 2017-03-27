package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.domain.WaterChange;
import java.util.List;

/**
 * Service Interface for managing WaterChange.
 */
public interface WaterChangeService {

    /**
     * Save a waterChange.
     *
     * @param waterChange the entity to save
     * @return the persisted entity
     */
    WaterChange save(WaterChange waterChange);

    /**
     *  Get all the waterChanges.
     *  
     *  @return the list of entities
     */
    List<WaterChange> findAll();

    /**
     *  Get the "id" waterChange.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WaterChange findOne(Long id);

    /**
     *  Delete the "id" waterChange.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the waterChange corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<WaterChange> search(String query);
}
