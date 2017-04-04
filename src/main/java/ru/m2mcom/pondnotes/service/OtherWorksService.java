package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.OtherWorksDTO;
import java.util.List;

/**
 * Service Interface for managing OtherWorks.
 */
public interface OtherWorksService {

    /**
     * Save a otherWorks.
     *
     * @param otherWorksDTO the entity to save
     * @return the persisted entity
     */
    OtherWorksDTO save(OtherWorksDTO otherWorksDTO);

    /**
     *  Get all the otherWorks.
     *  
     *  @return the list of entities
     */
    List<OtherWorksDTO> findAll();

    /**
     *  Get the "id" otherWorks.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OtherWorksDTO findOne(Long id);

    /**
     *  Delete the "id" otherWorks.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the otherWorks corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<OtherWorksDTO> search(String query);
}
