package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.domain.OtherWorks;
import java.util.List;

/**
 * Service Interface for managing OtherWorks.
 */
public interface OtherWorksService {

    /**
     * Save a otherWorks.
     *
     * @param otherWorks the entity to save
     * @return the persisted entity
     */
    OtherWorks save(OtherWorks otherWorks);

    /**
     *  Get all the otherWorks.
     *  
     *  @return the list of entities
     */
    List<OtherWorks> findAll();

    /**
     *  Get the "id" otherWorks.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OtherWorks findOne(Long id);

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
    List<OtherWorks> search(String query);
}
