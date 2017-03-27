package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.WaterChangeService;
import ru.m2mcom.pondnotes.domain.WaterChange;
import ru.m2mcom.pondnotes.repository.WaterChangeRepository;
import ru.m2mcom.pondnotes.repository.search.WaterChangeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

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

    private final WaterChangeSearchRepository waterChangeSearchRepository;

    public WaterChangeServiceImpl(WaterChangeRepository waterChangeRepository, WaterChangeSearchRepository waterChangeSearchRepository) {
        this.waterChangeRepository = waterChangeRepository;
        this.waterChangeSearchRepository = waterChangeSearchRepository;
    }

    /**
     * Save a waterChange.
     *
     * @param waterChange the entity to save
     * @return the persisted entity
     */
    @Override
    public WaterChange save(WaterChange waterChange) {
        log.debug("Request to save WaterChange : {}", waterChange);
        WaterChange result = waterChangeRepository.save(waterChange);
        waterChangeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the waterChanges.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WaterChange> findAll() {
        log.debug("Request to get all WaterChanges");
        List<WaterChange> result = waterChangeRepository.findAll();

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
    public WaterChange findOne(Long id) {
        log.debug("Request to get WaterChange : {}", id);
        WaterChange waterChange = waterChangeRepository.findOne(id);
        return waterChange;
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
    public List<WaterChange> search(String query) {
        log.debug("Request to search WaterChanges for query {}", query);
        return StreamSupport
            .stream(waterChangeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
