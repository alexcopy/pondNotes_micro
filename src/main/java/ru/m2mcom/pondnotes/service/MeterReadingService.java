package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.domain.MeterReading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing MeterReading.
 */
public interface MeterReadingService {

    /**
     * Save a meterReading.
     *
     * @param meterReading the entity to save
     * @return the persisted entity
     */
    MeterReading save(MeterReading meterReading);

    /**
     *  Get all the meterReadings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeterReading> findAll(Pageable pageable);

    /**
     *  Get the "id" meterReading.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeterReading findOne(Long id);

    /**
     *  Delete the "id" meterReading.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the meterReading corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeterReading> search(String query, Pageable pageable);
}
