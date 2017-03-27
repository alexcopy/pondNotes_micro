package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.domain.LiveStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing LiveStock.
 */
public interface LiveStockService {

    /**
     * Save a liveStock.
     *
     * @param liveStock the entity to save
     * @return the persisted entity
     */
    LiveStock save(LiveStock liveStock);

    /**
     *  Get all the liveStocks.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LiveStock> findAll(Pageable pageable);

    /**
     *  Get the "id" liveStock.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LiveStock findOne(Long id);

    /**
     *  Delete the "id" liveStock.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the liveStock corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LiveStock> search(String query, Pageable pageable);
}
