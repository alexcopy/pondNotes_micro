package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.ChemicalAnalysisService;
import ru.m2mcom.pondnotes.domain.ChemicalAnalysis;
import ru.m2mcom.pondnotes.repository.ChemicalAnalysisRepository;
import ru.m2mcom.pondnotes.repository.search.ChemicalAnalysisSearchRepository;
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
 * Service Implementation for managing ChemicalAnalysis.
 */
@Service
@Transactional
public class ChemicalAnalysisServiceImpl implements ChemicalAnalysisService{

    private final Logger log = LoggerFactory.getLogger(ChemicalAnalysisServiceImpl.class);
    
    private final ChemicalAnalysisRepository chemicalAnalysisRepository;

    private final ChemicalAnalysisSearchRepository chemicalAnalysisSearchRepository;

    public ChemicalAnalysisServiceImpl(ChemicalAnalysisRepository chemicalAnalysisRepository, ChemicalAnalysisSearchRepository chemicalAnalysisSearchRepository) {
        this.chemicalAnalysisRepository = chemicalAnalysisRepository;
        this.chemicalAnalysisSearchRepository = chemicalAnalysisSearchRepository;
    }

    /**
     * Save a chemicalAnalysis.
     *
     * @param chemicalAnalysis the entity to save
     * @return the persisted entity
     */
    @Override
    public ChemicalAnalysis save(ChemicalAnalysis chemicalAnalysis) {
        log.debug("Request to save ChemicalAnalysis : {}", chemicalAnalysis);
        ChemicalAnalysis result = chemicalAnalysisRepository.save(chemicalAnalysis);
        chemicalAnalysisSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the chemicalAnalyses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChemicalAnalysis> findAll(Pageable pageable) {
        log.debug("Request to get all ChemicalAnalyses");
        Page<ChemicalAnalysis> result = chemicalAnalysisRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one chemicalAnalysis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ChemicalAnalysis findOne(Long id) {
        log.debug("Request to get ChemicalAnalysis : {}", id);
        ChemicalAnalysis chemicalAnalysis = chemicalAnalysisRepository.findOneWithEagerRelationships(id);
        return chemicalAnalysis;
    }

    /**
     *  Delete the  chemicalAnalysis by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChemicalAnalysis : {}", id);
        chemicalAnalysisRepository.delete(id);
        chemicalAnalysisSearchRepository.delete(id);
    }

    /**
     * Search for the chemicalAnalysis corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChemicalAnalysis> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChemicalAnalyses for query {}", query);
        Page<ChemicalAnalysis> result = chemicalAnalysisSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
