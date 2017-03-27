package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.FilterPumpCleaningService;
import ru.m2mcom.pondnotes.domain.FilterPumpCleaning;
import ru.m2mcom.pondnotes.repository.FilterPumpCleaningRepository;
import ru.m2mcom.pondnotes.repository.search.FilterPumpCleaningSearchRepository;
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
 * Service Implementation for managing FilterPumpCleaning.
 */
@Service
@Transactional
public class FilterPumpCleaningServiceImpl implements FilterPumpCleaningService{

    private final Logger log = LoggerFactory.getLogger(FilterPumpCleaningServiceImpl.class);
    
    private final FilterPumpCleaningRepository filterPumpCleaningRepository;

    private final FilterPumpCleaningSearchRepository filterPumpCleaningSearchRepository;

    public FilterPumpCleaningServiceImpl(FilterPumpCleaningRepository filterPumpCleaningRepository, FilterPumpCleaningSearchRepository filterPumpCleaningSearchRepository) {
        this.filterPumpCleaningRepository = filterPumpCleaningRepository;
        this.filterPumpCleaningSearchRepository = filterPumpCleaningSearchRepository;
    }

    /**
     * Save a filterPumpCleaning.
     *
     * @param filterPumpCleaning the entity to save
     * @return the persisted entity
     */
    @Override
    public FilterPumpCleaning save(FilterPumpCleaning filterPumpCleaning) {
        log.debug("Request to save FilterPumpCleaning : {}", filterPumpCleaning);
        FilterPumpCleaning result = filterPumpCleaningRepository.save(filterPumpCleaning);
        filterPumpCleaningSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the filterPumpCleanings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FilterPumpCleaning> findAll(Pageable pageable) {
        log.debug("Request to get all FilterPumpCleanings");
        Page<FilterPumpCleaning> result = filterPumpCleaningRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one filterPumpCleaning by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FilterPumpCleaning findOne(Long id) {
        log.debug("Request to get FilterPumpCleaning : {}", id);
        FilterPumpCleaning filterPumpCleaning = filterPumpCleaningRepository.findOne(id);
        return filterPumpCleaning;
    }

    /**
     *  Delete the  filterPumpCleaning by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FilterPumpCleaning : {}", id);
        filterPumpCleaningRepository.delete(id);
        filterPumpCleaningSearchRepository.delete(id);
    }

    /**
     * Search for the filterPumpCleaning corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FilterPumpCleaning> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FilterPumpCleanings for query {}", query);
        Page<FilterPumpCleaning> result = filterPumpCleaningSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
