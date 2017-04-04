package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.OtherWorksService;
import ru.m2mcom.pondnotes.domain.OtherWorks;
import ru.m2mcom.pondnotes.repository.OtherWorksRepository;
import ru.m2mcom.pondnotes.repository.search.OtherWorksSearchRepository;
import ru.m2mcom.pondnotes.service.dto.OtherWorksDTO;
import ru.m2mcom.pondnotes.service.mapper.OtherWorksMapper;
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
 * Service Implementation for managing OtherWorks.
 */
@Service
@Transactional
public class OtherWorksServiceImpl implements OtherWorksService{

    private final Logger log = LoggerFactory.getLogger(OtherWorksServiceImpl.class);
    
    private final OtherWorksRepository otherWorksRepository;

    private final OtherWorksMapper otherWorksMapper;

    private final OtherWorksSearchRepository otherWorksSearchRepository;

    public OtherWorksServiceImpl(OtherWorksRepository otherWorksRepository, OtherWorksMapper otherWorksMapper, OtherWorksSearchRepository otherWorksSearchRepository) {
        this.otherWorksRepository = otherWorksRepository;
        this.otherWorksMapper = otherWorksMapper;
        this.otherWorksSearchRepository = otherWorksSearchRepository;
    }

    /**
     * Save a otherWorks.
     *
     * @param otherWorksDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OtherWorksDTO save(OtherWorksDTO otherWorksDTO) {
        log.debug("Request to save OtherWorks : {}", otherWorksDTO);
        OtherWorks otherWorks = otherWorksMapper.otherWorksDTOToOtherWorks(otherWorksDTO);
        otherWorks = otherWorksRepository.save(otherWorks);
        OtherWorksDTO result = otherWorksMapper.otherWorksToOtherWorksDTO(otherWorks);
        otherWorksSearchRepository.save(otherWorks);
        return result;
    }

    /**
     *  Get all the otherWorks.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OtherWorksDTO> findAll() {
        log.debug("Request to get all OtherWorks");
        List<OtherWorksDTO> result = otherWorksRepository.findAll().stream()
            .map(otherWorksMapper::otherWorksToOtherWorksDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one otherWorks by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OtherWorksDTO findOne(Long id) {
        log.debug("Request to get OtherWorks : {}", id);
        OtherWorks otherWorks = otherWorksRepository.findOne(id);
        OtherWorksDTO otherWorksDTO = otherWorksMapper.otherWorksToOtherWorksDTO(otherWorks);
        return otherWorksDTO;
    }

    /**
     *  Delete the  otherWorks by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OtherWorks : {}", id);
        otherWorksRepository.delete(id);
        otherWorksSearchRepository.delete(id);
    }

    /**
     * Search for the otherWorks corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OtherWorksDTO> search(String query) {
        log.debug("Request to search OtherWorks for query {}", query);
        return StreamSupport
            .stream(otherWorksSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(otherWorksMapper::otherWorksToOtherWorksDTO)
            .collect(Collectors.toList());
    }
}
