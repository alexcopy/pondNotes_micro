package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.ChemicalAnalysisService;
import ru.m2mcom.pondnotes.domain.ChemicalAnalysis;
import ru.m2mcom.pondnotes.repository.ChemicalAnalysisRepository;
import ru.m2mcom.pondnotes.repository.search.ChemicalAnalysisSearchRepository;
import ru.m2mcom.pondnotes.service.dto.ChemicalAnalysisDTO;
import ru.m2mcom.pondnotes.service.mapper.ChemicalAnalysisMapper;
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
 * Service Implementation for managing ChemicalAnalysis.
 */
@Service
@Transactional
public class ChemicalAnalysisServiceImpl implements ChemicalAnalysisService{

    private final Logger log = LoggerFactory.getLogger(ChemicalAnalysisServiceImpl.class);
    
    private final ChemicalAnalysisRepository chemicalAnalysisRepository;

    private final ChemicalAnalysisMapper chemicalAnalysisMapper;

    private final ChemicalAnalysisSearchRepository chemicalAnalysisSearchRepository;

    public ChemicalAnalysisServiceImpl(ChemicalAnalysisRepository chemicalAnalysisRepository, ChemicalAnalysisMapper chemicalAnalysisMapper, ChemicalAnalysisSearchRepository chemicalAnalysisSearchRepository) {
        this.chemicalAnalysisRepository = chemicalAnalysisRepository;
        this.chemicalAnalysisMapper = chemicalAnalysisMapper;
        this.chemicalAnalysisSearchRepository = chemicalAnalysisSearchRepository;
    }

    /**
     * Save a chemicalAnalysis.
     *
     * @param chemicalAnalysisDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChemicalAnalysisDTO save(ChemicalAnalysisDTO chemicalAnalysisDTO) {
        log.debug("Request to save ChemicalAnalysis : {}", chemicalAnalysisDTO);
        ChemicalAnalysis chemicalAnalysis = chemicalAnalysisMapper.chemicalAnalysisDTOToChemicalAnalysis(chemicalAnalysisDTO);
        chemicalAnalysis = chemicalAnalysisRepository.save(chemicalAnalysis);
        ChemicalAnalysisDTO result = chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(chemicalAnalysis);
        chemicalAnalysisSearchRepository.save(chemicalAnalysis);
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
    public Page<ChemicalAnalysisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChemicalAnalyses");
        Page<ChemicalAnalysis> result = chemicalAnalysisRepository.findAll(pageable);
        return result.map(chemicalAnalysis -> chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(chemicalAnalysis));
    }

    /**
     *  Get one chemicalAnalysis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ChemicalAnalysisDTO findOne(Long id) {
        log.debug("Request to get ChemicalAnalysis : {}", id);
        ChemicalAnalysis chemicalAnalysis = chemicalAnalysisRepository.findOne(id);
        ChemicalAnalysisDTO chemicalAnalysisDTO = chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(chemicalAnalysis);
        return chemicalAnalysisDTO;
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
    public Page<ChemicalAnalysisDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChemicalAnalyses for query {}", query);
        Page<ChemicalAnalysis> result = chemicalAnalysisSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(chemicalAnalysis -> chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(chemicalAnalysis));
    }
}
