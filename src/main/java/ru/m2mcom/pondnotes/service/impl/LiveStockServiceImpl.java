package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.LiveStockService;
import ru.m2mcom.pondnotes.domain.LiveStock;
import ru.m2mcom.pondnotes.repository.LiveStockRepository;
import ru.m2mcom.pondnotes.repository.search.LiveStockSearchRepository;
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
 * Service Implementation for managing LiveStock.
 */
@Service
@Transactional
public class LiveStockServiceImpl implements LiveStockService{

    private final Logger log = LoggerFactory.getLogger(LiveStockServiceImpl.class);
    
    private final LiveStockRepository liveStockRepository;

    private final LiveStockSearchRepository liveStockSearchRepository;

    public LiveStockServiceImpl(LiveStockRepository liveStockRepository, LiveStockSearchRepository liveStockSearchRepository) {
        this.liveStockRepository = liveStockRepository;
        this.liveStockSearchRepository = liveStockSearchRepository;
    }

    /**
     * Save a liveStock.
     *
     * @param liveStock the entity to save
     * @return the persisted entity
     */
    @Override
    public LiveStock save(LiveStock liveStock) {
        log.debug("Request to save LiveStock : {}", liveStock);
        LiveStock result = liveStockRepository.save(liveStock);
        liveStockSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the liveStocks.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LiveStock> findAll(Pageable pageable) {
        log.debug("Request to get all LiveStocks");
        Page<LiveStock> result = liveStockRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one liveStock by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LiveStock findOne(Long id) {
        log.debug("Request to get LiveStock : {}", id);
        LiveStock liveStock = liveStockRepository.findOne(id);
        return liveStock;
    }

    /**
     *  Delete the  liveStock by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LiveStock : {}", id);
        liveStockRepository.delete(id);
        liveStockSearchRepository.delete(id);
    }

    /**
     * Search for the liveStock corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LiveStock> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LiveStocks for query {}", query);
        Page<LiveStock> result = liveStockSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
