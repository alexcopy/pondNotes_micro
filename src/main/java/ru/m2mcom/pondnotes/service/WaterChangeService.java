package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.WaterChangeDTO;
import java.util.List;

/**
 * Service Interface for managing WaterChange.
 */
public interface WaterChangeService {

    /**
     * Save a waterChange.
     *
     * @param waterChangeDTO the entity to save
     * @return the persisted entity
     */
    WaterChangeDTO save(WaterChangeDTO waterChangeDTO);

    /**
     *  Get all the waterChanges.
     *  
     *  @return the list of entities
     */
    List<WaterChangeDTO> findAll();

    /**
     *  Get the "id" waterChange.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WaterChangeDTO findOne(Long id);

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
    List<WaterChangeDTO> search(String query);
}
