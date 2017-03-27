package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.MeterReadingService;
import ru.m2mcom.pondnotes.domain.MeterReading;
import ru.m2mcom.pondnotes.repository.MeterReadingRepository;
import ru.m2mcom.pondnotes.repository.search.MeterReadingSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MeterReading.
 */
@Service
@Transactional
public class MeterReadingServiceImpl implements MeterReadingService{

    private final Logger log = LoggerFactory.getLogger(MeterReadingServiceImpl.class);
    
    private final MeterReadingRepository meterReadingRepository;

    private final MeterReadingSearchRepository meterReadingSearchRepository;

    public MeterReadingServiceImpl(MeterReadingRepository meterReadingRepository, MeterReadingSearchRepository meterReadingSearchRepository) {
        this.meterReadingRepository = meterReadingRepository;
        this.meterReadingSearchRepository = meterReadingSearchRepository;
    }

    /**
     * Save a meterReading.
     *
     * @param meterReading the entity to save
     * @return the persisted entity
     */
    @Override
    public MeterReading save(MeterReading meterReading) {
        log.debug("Request to save MeterReading : {}", meterReading);
        MeterReading result = meterReadingRepository.save(meterReading);
        meterReadingSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the meterReadings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeterReading> findAll(Pageable pageable) {
        log.debug("Request to get all MeterReadings");
        Page<MeterReading> result = meterReadingRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one meterReading by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeterReading findOne(Long id) {
        log.debug("Request to get MeterReading : {}", id);
        MeterReading meterReading = meterReadingRepository.findOne(id);
        return meterReading;
    }

    /**
     *  Delete the  meterReading by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeterReading : {}", id);
        meterReadingRepository.delete(id);
        meterReadingSearchRepository.delete(id);
    }

    /**
     * Search for the meterReading corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeterReading> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MeterReadings for query {}", query);
        Page<MeterReading> result = meterReadingSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
