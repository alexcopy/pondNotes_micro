package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.TempMeterDTO;
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
     * @param tempMeterDTO the entity to save
     * @return the persisted entity
     */
    TempMeterDTO save(TempMeterDTO tempMeterDTO);

    /**
     *  Get all the tempMeters.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TempMeterDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" tempMeter.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TempMeterDTO findOne(Long id);

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
    Page<TempMeterDTO> search(String query, Pageable pageable);
}
