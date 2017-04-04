package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.MeterReadingDTO;
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
     * @param meterReadingDTO the entity to save
     * @return the persisted entity
     */
    MeterReadingDTO save(MeterReadingDTO meterReadingDTO);

    /**
     *  Get all the meterReadings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<MeterReadingDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" meterReading.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    MeterReadingDTO findOne(Long id);

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
    Page<MeterReadingDTO> search(String query, Pageable pageable);
}
