package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.WaterChangeService;
import ru.m2mcom.pondnotes.domain.WaterChange;
import ru.m2mcom.pondnotes.repository.WaterChangeRepository;
import ru.m2mcom.pondnotes.repository.search.WaterChangeSearchRepository;
import ru.m2mcom.pondnotes.service.dto.WaterChangeDTO;
import ru.m2mcom.pondnotes.service.mapper.WaterChangeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing WaterChange.
 */
@Service
@Transactional
public class WaterChangeServiceImpl implements WaterChangeService{

    private final Logger log = LoggerFactory.getLogger(WaterChangeServiceImpl.class);
    
    private final WaterChangeRepository waterChangeRepository;

    private final WaterChangeMapper waterChangeMapper;

    private final WaterChangeSearchRepository waterChangeSearchRepository;

    public WaterChangeServiceImpl(WaterChangeRepository waterChangeRepository, WaterChangeMapper waterChangeMapper, WaterChangeSearchRepository waterChangeSearchRepository) {
        this.waterChangeRepository = waterChangeRepository;
        this.waterChangeMapper = waterChangeMapper;
        this.waterChangeSearchRepository = waterChangeSearchRepository;
    }

    /**
     * Save a waterChange.
     *
     * @param waterChangeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WaterChangeDTO save(WaterChangeDTO waterChangeDTO) {
        log.debug("Request to save WaterChange : {}", waterChangeDTO);
        WaterChange waterChange = waterChangeMapper.waterChangeDTOToWaterChange(waterChangeDTO);
        waterChange = waterChangeRepository.save(waterChange);
        WaterChangeDTO result = waterChangeMapper.waterChangeToWaterChangeDTO(waterChange);
        waterChangeSearchRepository.save(waterChange);
        return result;
    }

    /**
     *  Get all the waterChanges.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WaterChangeDTO> findAll() {
        log.debug("Request to get all WaterChanges");
        List<WaterChangeDTO> result = waterChangeRepository.findAll().stream()
            .map(waterChangeMapper::waterChangeToWaterChangeDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one waterChange by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WaterChangeDTO findOne(Long id) {
        log.debug("Request to get WaterChange : {}", id);
        WaterChange waterChange = waterChangeRepository.findOne(id);
        WaterChangeDTO waterChangeDTO = waterChangeMapper.waterChangeToWaterChangeDTO(waterChange);
        return waterChangeDTO;
    }

    /**
     *  Delete the  waterChange by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WaterChange : {}", id);
        waterChangeRepository.delete(id);
        waterChangeSearchRepository.delete(id);
    }

    /**
     * Search for the waterChange corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WaterChangeDTO> search(String query) {
        log.debug("Request to search WaterChanges for query {}", query);
        return StreamSupport
            .stream(waterChangeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(waterChangeMapper::waterChangeToWaterChangeDTO)
            .collect(Collectors.toList());
    }
}
