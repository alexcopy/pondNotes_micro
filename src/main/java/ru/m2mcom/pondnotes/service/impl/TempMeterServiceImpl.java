package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.TempMeterService;
import ru.m2mcom.pondnotes.domain.TempMeter;
import ru.m2mcom.pondnotes.repository.TempMeterRepository;
import ru.m2mcom.pondnotes.repository.search.TempMeterSearchRepository;
import ru.m2mcom.pondnotes.service.dto.TempMeterDTO;
import ru.m2mcom.pondnotes.service.mapper.TempMeterMapper;
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
 * Service Implementation for managing TempMeter.
 */
@Service
@Transactional
public class TempMeterServiceImpl implements TempMeterService{

    private final Logger log = LoggerFactory.getLogger(TempMeterServiceImpl.class);
    
    private final TempMeterRepository tempMeterRepository;

    private final TempMeterMapper tempMeterMapper;

    private final TempMeterSearchRepository tempMeterSearchRepository;

    public TempMeterServiceImpl(TempMeterRepository tempMeterRepository, TempMeterMapper tempMeterMapper, TempMeterSearchRepository tempMeterSearchRepository) {
        this.tempMeterRepository = tempMeterRepository;
        this.tempMeterMapper = tempMeterMapper;
        this.tempMeterSearchRepository = tempMeterSearchRepository;
    }

    /**
     * Save a tempMeter.
     *
     * @param tempMeterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TempMeterDTO save(TempMeterDTO tempMeterDTO) {
        log.debug("Request to save TempMeter : {}", tempMeterDTO);
        TempMeter tempMeter = tempMeterMapper.tempMeterDTOToTempMeter(tempMeterDTO);
        tempMeter = tempMeterRepository.save(tempMeter);
        TempMeterDTO result = tempMeterMapper.tempMeterToTempMeterDTO(tempMeter);
        tempMeterSearchRepository.save(tempMeter);
        return result;
    }

    /**
     *  Get all the tempMeters.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TempMeterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TempMeters");
        Page<TempMeter> result = tempMeterRepository.findAll(pageable);
        return result.map(tempMeter -> tempMeterMapper.tempMeterToTempMeterDTO(tempMeter));
    }

    /**
     *  Get one tempMeter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TempMeterDTO findOne(Long id) {
        log.debug("Request to get TempMeter : {}", id);
        TempMeter tempMeter = tempMeterRepository.findOne(id);
        TempMeterDTO tempMeterDTO = tempMeterMapper.tempMeterToTempMeterDTO(tempMeter);
        return tempMeterDTO;
    }

    /**
     *  Delete the  tempMeter by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TempMeter : {}", id);
        tempMeterRepository.delete(id);
        tempMeterSearchRepository.delete(id);
    }

    /**
     * Search for the tempMeter corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TempMeterDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TempMeters for query {}", query);
        Page<TempMeter> result = tempMeterSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(tempMeter -> tempMeterMapper.tempMeterToTempMeterDTO(tempMeter));
    }
}
