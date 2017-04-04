package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.TankService;
import ru.m2mcom.pondnotes.domain.Tank;
import ru.m2mcom.pondnotes.repository.TankRepository;
import ru.m2mcom.pondnotes.repository.search.TankSearchRepository;
import ru.m2mcom.pondnotes.service.dto.TankDTO;
import ru.m2mcom.pondnotes.service.mapper.TankMapper;
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
 * Service Implementation for managing Tank.
 */
@Service
@Transactional
public class TankServiceImpl implements TankService{

    private final Logger log = LoggerFactory.getLogger(TankServiceImpl.class);
    
    private final TankRepository tankRepository;

    private final TankMapper tankMapper;

    private final TankSearchRepository tankSearchRepository;

    public TankServiceImpl(TankRepository tankRepository, TankMapper tankMapper, TankSearchRepository tankSearchRepository) {
        this.tankRepository = tankRepository;
        this.tankMapper = tankMapper;
        this.tankSearchRepository = tankSearchRepository;
    }

    /**
     * Save a tank.
     *
     * @param tankDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TankDTO save(TankDTO tankDTO) {
        log.debug("Request to save Tank : {}", tankDTO);
        Tank tank = tankMapper.tankDTOToTank(tankDTO);
        tank = tankRepository.save(tank);
        TankDTO result = tankMapper.tankToTankDTO(tank);
        tankSearchRepository.save(tank);
        return result;
    }

    /**
     *  Get all the tanks.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TankDTO> findAll() {
        log.debug("Request to get all Tanks");
        List<TankDTO> result = tankRepository.findAll().stream()
            .map(tankMapper::tankToTankDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one tank by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TankDTO findOne(Long id) {
        log.debug("Request to get Tank : {}", id);
        Tank tank = tankRepository.findOne(id);
        TankDTO tankDTO = tankMapper.tankToTankDTO(tank);
        return tankDTO;
    }

    /**
     *  Delete the  tank by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tank : {}", id);
        tankRepository.delete(id);
        tankSearchRepository.delete(id);
    }

    /**
     * Search for the tank corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TankDTO> search(String query) {
        log.debug("Request to search Tanks for query {}", query);
        return StreamSupport
            .stream(tankSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(tankMapper::tankToTankDTO)
            .collect(Collectors.toList());
    }
}
