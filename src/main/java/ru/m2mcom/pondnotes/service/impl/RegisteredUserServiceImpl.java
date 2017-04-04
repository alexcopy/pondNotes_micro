package ru.m2mcom.pondnotes.service.impl;

import ru.m2mcom.pondnotes.service.RegisteredUserService;
import ru.m2mcom.pondnotes.domain.RegisteredUser;
import ru.m2mcom.pondnotes.repository.RegisteredUserRepository;
import ru.m2mcom.pondnotes.repository.search.RegisteredUserSearchRepository;
import ru.m2mcom.pondnotes.service.dto.RegisteredUserDTO;
import ru.m2mcom.pondnotes.service.mapper.RegisteredUserMapper;
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
 * Service Implementation for managing RegisteredUser.
 */
@Service
@Transactional
public class RegisteredUserServiceImpl implements RegisteredUserService{

    private final Logger log = LoggerFactory.getLogger(RegisteredUserServiceImpl.class);
    
    private final RegisteredUserRepository registeredUserRepository;

    private final RegisteredUserMapper registeredUserMapper;

    private final RegisteredUserSearchRepository registeredUserSearchRepository;

    public RegisteredUserServiceImpl(RegisteredUserRepository registeredUserRepository, RegisteredUserMapper registeredUserMapper, RegisteredUserSearchRepository registeredUserSearchRepository) {
        this.registeredUserRepository = registeredUserRepository;
        this.registeredUserMapper = registeredUserMapper;
        this.registeredUserSearchRepository = registeredUserSearchRepository;
    }

    /**
     * Save a registeredUser.
     *
     * @param registeredUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RegisteredUserDTO save(RegisteredUserDTO registeredUserDTO) {
        log.debug("Request to save RegisteredUser : {}", registeredUserDTO);
        RegisteredUser registeredUser = registeredUserMapper.registeredUserDTOToRegisteredUser(registeredUserDTO);
        registeredUser = registeredUserRepository.save(registeredUser);
        RegisteredUserDTO result = registeredUserMapper.registeredUserToRegisteredUserDTO(registeredUser);
        registeredUserSearchRepository.save(registeredUser);
        return result;
    }

    /**
     *  Get all the registeredUsers.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegisteredUserDTO> findAll() {
        log.debug("Request to get all RegisteredUsers");
        List<RegisteredUserDTO> result = registeredUserRepository.findAll().stream()
            .map(registeredUserMapper::registeredUserToRegisteredUserDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one registeredUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RegisteredUserDTO findOne(Long id) {
        log.debug("Request to get RegisteredUser : {}", id);
        RegisteredUser registeredUser = registeredUserRepository.findOne(id);
        RegisteredUserDTO registeredUserDTO = registeredUserMapper.registeredUserToRegisteredUserDTO(registeredUser);
        return registeredUserDTO;
    }

    /**
     *  Delete the  registeredUser by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegisteredUser : {}", id);
        registeredUserRepository.delete(id);
        registeredUserSearchRepository.delete(id);
    }

    /**
     * Search for the registeredUser corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RegisteredUserDTO> search(String query) {
        log.debug("Request to search RegisteredUsers for query {}", query);
        return StreamSupport
            .stream(registeredUserSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(registeredUserMapper::registeredUserToRegisteredUserDTO)
            .collect(Collectors.toList());
    }
}
