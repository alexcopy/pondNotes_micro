package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.service.RegisteredUserService;
import ru.m2mcom.pondnotes.web.rest.util.HeaderUtil;
import ru.m2mcom.pondnotes.service.dto.RegisteredUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing RegisteredUser.
 */
@RestController
@RequestMapping("/api")
public class RegisteredUserResource {

    private final Logger log = LoggerFactory.getLogger(RegisteredUserResource.class);

    private static final String ENTITY_NAME = "registeredUser";
        
    private final RegisteredUserService registeredUserService;

    public RegisteredUserResource(RegisteredUserService registeredUserService) {
        this.registeredUserService = registeredUserService;
    }

    /**
     * POST  /registered-users : Create a new registeredUser.
     *
     * @param registeredUserDTO the registeredUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new registeredUserDTO, or with status 400 (Bad Request) if the registeredUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/registered-users")
    @Timed
    public ResponseEntity<RegisteredUserDTO> createRegisteredUser(@RequestBody RegisteredUserDTO registeredUserDTO) throws URISyntaxException {
        log.debug("REST request to save RegisteredUser : {}", registeredUserDTO);
        if (registeredUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new registeredUser cannot already have an ID")).body(null);
        }
        RegisteredUserDTO result = registeredUserService.save(registeredUserDTO);
        return ResponseEntity.created(new URI("/api/registered-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /registered-users : Updates an existing registeredUser.
     *
     * @param registeredUserDTO the registeredUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated registeredUserDTO,
     * or with status 400 (Bad Request) if the registeredUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the registeredUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/registered-users")
    @Timed
    public ResponseEntity<RegisteredUserDTO> updateRegisteredUser(@RequestBody RegisteredUserDTO registeredUserDTO) throws URISyntaxException {
        log.debug("REST request to update RegisteredUser : {}", registeredUserDTO);
        if (registeredUserDTO.getId() == null) {
            return createRegisteredUser(registeredUserDTO);
        }
        RegisteredUserDTO result = registeredUserService.save(registeredUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, registeredUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /registered-users : get all the registeredUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of registeredUsers in body
     */
    @GetMapping("/registered-users")
    @Timed
    public List<RegisteredUserDTO> getAllRegisteredUsers() {
        log.debug("REST request to get all RegisteredUsers");
        return registeredUserService.findAll();
    }

    /**
     * GET  /registered-users/:id : get the "id" registeredUser.
     *
     * @param id the id of the registeredUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the registeredUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/registered-users/{id}")
    @Timed
    public ResponseEntity<RegisteredUserDTO> getRegisteredUser(@PathVariable Long id) {
        log.debug("REST request to get RegisteredUser : {}", id);
        RegisteredUserDTO registeredUserDTO = registeredUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(registeredUserDTO));
    }

    /**
     * DELETE  /registered-users/:id : delete the "id" registeredUser.
     *
     * @param id the id of the registeredUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/registered-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegisteredUser(@PathVariable Long id) {
        log.debug("REST request to delete RegisteredUser : {}", id);
        registeredUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/registered-users?query=:query : search for the registeredUser corresponding
     * to the query.
     *
     * @param query the query of the registeredUser search 
     * @return the result of the search
     */
    @GetMapping("/_search/registered-users")
    @Timed
    public List<RegisteredUserDTO> searchRegisteredUsers(@RequestParam String query) {
        log.debug("REST request to search RegisteredUsers for query {}", query);
        return registeredUserService.search(query);
    }


}
