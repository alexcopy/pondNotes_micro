package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.LiveStockDTO;
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
     * @param liveStockDTO the entity to save
     * @return the persisted entity
     */
    LiveStockDTO save(LiveStockDTO liveStockDTO);

    /**
     *  Get all the liveStocks.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LiveStockDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" liveStock.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LiveStockDTO findOne(Long id);

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
    Page<LiveStockDTO> search(String query, Pageable pageable);
}
