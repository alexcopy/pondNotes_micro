package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.domain.Tank;
import java.util.List;

/**
 * Service Interface for managing Tank.
 */
public interface TankService {

    /**
     * Save a tank.
     *
     * @param tank the entity to save
     * @return the persisted entity
     */
    Tank save(Tank tank);

    /**
     *  Get all the tanks.
     *  
     *  @return the list of entities
     */
    List<Tank> findAll();

    /**
     *  Get the "id" tank.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tank findOne(Long id);

    /**
     *  Delete the "id" tank.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tank corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Tank> search(String query);
}
