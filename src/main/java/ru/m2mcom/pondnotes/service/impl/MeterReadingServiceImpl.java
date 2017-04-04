package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.MeterReadingService;
import ru.m2mcom.pondnotes.domain.MeterReading;
import ru.m2mcom.pondnotes.repository.MeterReadingRepository;
import ru.m2mcom.pondnotes.repository.search.MeterReadingSearchRepository;
import ru.m2mcom.pondnotes.service.dto.MeterReadingDTO;
import ru.m2mcom.pondnotes.service.mapper.MeterReadingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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

    private final MeterReadingMapper meterReadingMapper;

    private final MeterReadingSearchRepository meterReadingSearchRepository;

    public MeterReadingServiceImpl(MeterReadingRepository meterReadingRepository, MeterReadingMapper meterReadingMapper, MeterReadingSearchRepository meterReadingSearchRepository) {
        this.meterReadingRepository = meterReadingRepository;
        this.meterReadingMapper = meterReadingMapper;
        this.meterReadingSearchRepository = meterReadingSearchRepository;
    }

    /**
     * Save a meterReading.
     *
     * @param meterReadingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MeterReadingDTO save(MeterReadingDTO meterReadingDTO) {
        log.debug("Request to save MeterReading : {}", meterReadingDTO);
        MeterReading meterReading = meterReadingMapper.meterReadingDTOToMeterReading(meterReadingDTO);
        meterReading = meterReadingRepository.save(meterReading);
        MeterReadingDTO result = meterReadingMapper.meterReadingToMeterReadingDTO(meterReading);
        meterReadingSearchRepository.save(meterReading);
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
    public Page<MeterReadingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeterReadings");
        Page<MeterReading> result = meterReadingRepository.findAll(pageable);
        return result.map(meterReading -> meterReadingMapper.meterReadingToMeterReadingDTO(meterReading));
    }

    /**
     *  Get one meterReading by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MeterReadingDTO findOne(Long id) {
        log.debug("Request to get MeterReading : {}", id);
        MeterReading meterReading = meterReadingRepository.findOne(id);
        MeterReadingDTO meterReadingDTO = meterReadingMapper.meterReadingToMeterReadingDTO(meterReading);
        return meterReadingDTO;
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
    public Page<MeterReadingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MeterReadings for query {}", query);
        Page<MeterReading> result = meterReadingSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(meterReading -> meterReadingMapper.meterReadingToMeterReadingDTO(meterReading));
    }
}
