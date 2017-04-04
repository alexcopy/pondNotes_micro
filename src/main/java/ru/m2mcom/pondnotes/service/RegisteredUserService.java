package ru.m2mcom.pondnotes.service;

import ru.m2mcom.pondnotes.service.dto.RegisteredUserDTO;
import java.util.List;

/**
 * Service Interface for managing RegisteredUser.
 */
public interface RegisteredUserService {

    /**
     * Save a registeredUser.
     *
     * @param registeredUserDTO the entity to save
     * @return the persisted entity
     */
    RegisteredUserDTO save(RegisteredUserDTO registeredUserDTO);

    /**
     *  Get all the registeredUsers.
     *  
     *  @return the list of entities
     */
    List<RegisteredUserDTO> findAll();

    /**
     *  Get the "id" registeredUser.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    RegisteredUserDTO findOne(Long id);

    /**
     *  Delete the "id" registeredUser.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the registeredUser corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<RegisteredUserDTO> search(String query);
}
