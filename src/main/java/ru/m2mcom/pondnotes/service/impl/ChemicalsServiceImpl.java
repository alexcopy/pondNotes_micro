package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.ChemicalsService;
import ru.m2mcom.pondnotes.domain.Chemicals;
import ru.m2mcom.pondnotes.repository.ChemicalsRepository;
import ru.m2mcom.pondnotes.repository.search.ChemicalsSearchRepository;
import ru.m2mcom.pondnotes.service.dto.ChemicalsDTO;
import ru.m2mcom.pondnotes.service.mapper.ChemicalsMapper;
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
 * Service Implementation for managing Chemicals.
 */
@Service
@Transactional
public class ChemicalsServiceImpl implements ChemicalsService{

    private final Logger log = LoggerFactory.getLogger(ChemicalsServiceImpl.class);
    
    private final ChemicalsRepository chemicalsRepository;

    private final ChemicalsMapper chemicalsMapper;

    private final ChemicalsSearchRepository chemicalsSearchRepository;

    public ChemicalsServiceImpl(ChemicalsRepository chemicalsRepository, ChemicalsMapper chemicalsMapper, ChemicalsSearchRepository chemicalsSearchRepository) {
        this.chemicalsRepository = chemicalsRepository;
        this.chemicalsMapper = chemicalsMapper;
        this.chemicalsSearchRepository = chemicalsSearchRepository;
    }

    /**
     * Save a chemicals.
     *
     * @param chemicalsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ChemicalsDTO save(ChemicalsDTO chemicalsDTO) {
        log.debug("Request to save Chemicals : {}", chemicalsDTO);
        Chemicals chemicals = chemicalsMapper.chemicalsDTOToChemicals(chemicalsDTO);
        chemicals = chemicalsRepository.save(chemicals);
        ChemicalsDTO result = chemicalsMapper.chemicalsToChemicalsDTO(chemicals);
        chemicalsSearchRepository.save(chemicals);
        return result;
    }

    /**
     *  Get all the chemicals.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChemicalsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Chemicals");
        Page<Chemicals> result = chemicalsRepository.findAll(pageable);
        return result.map(chemicals -> chemicalsMapper.chemicalsToChemicalsDTO(chemicals));
    }

    /**
     *  Get one chemicals by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ChemicalsDTO findOne(Long id) {
        log.debug("Request to get Chemicals : {}", id);
        Chemicals chemicals = chemicalsRepository.findOne(id);
        ChemicalsDTO chemicalsDTO = chemicalsMapper.chemicalsToChemicalsDTO(chemicals);
        return chemicalsDTO;
    }

    /**
     *  Delete the  chemicals by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chemicals : {}", id);
        chemicalsRepository.delete(id);
        chemicalsSearchRepository.delete(id);
    }

    /**
     * Search for the chemicals corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ChemicalsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Chemicals for query {}", query);
        Page<Chemicals> result = chemicalsSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(chemicals -> chemicalsMapper.chemicalsToChemicalsDTO(chemicals));
    }
}
