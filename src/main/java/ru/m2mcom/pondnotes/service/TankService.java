package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.TankDTO;
import java.util.List;

/**
 * Service Interface for managing Tank.
 */
public interface TankService {

    /**
     * Save a tank.
     *
     * @param tankDTO the entity to save
     * @return the persisted entity
     */
    TankDTO save(TankDTO tankDTO);

    /**
     *  Get all the tanks.
     *  
     *  @return the list of entities
     */
    List<TankDTO> findAll();

    /**
     *  Get the "id" tank.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TankDTO findOne(Long id);

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
    List<TankDTO> search(String query);
}
