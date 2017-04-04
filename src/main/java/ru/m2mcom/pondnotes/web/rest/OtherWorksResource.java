package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.service.OtherWorksService;
import ru.m2mcom.pondnotes.web.rest.util.HeaderUtil;
import ru.m2mcom.pondnotes.service.dto.OtherWorksDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing OtherWorks.
 */
@RestController
@RequestMapping("/api")
public class OtherWorksResource {

    private final Logger log = LoggerFactory.getLogger(OtherWorksResource.class);

    private static final String ENTITY_NAME = "otherWorks";
        
    private final OtherWorksService otherWorksService;

    public OtherWorksResource(OtherWorksService otherWorksService) {
        this.otherWorksService = otherWorksService;
    }

    /**
     * POST  /other-works : Create a new otherWorks.
     *
     * @param otherWorksDTO the otherWorksDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new otherWorksDTO, or with status 400 (Bad Request) if the otherWorks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/other-works")
    @Timed
    public ResponseEntity<OtherWorksDTO> createOtherWorks(@Valid @RequestBody OtherWorksDTO otherWorksDTO) throws URISyntaxException {
        log.debug("REST request to save OtherWorks : {}", otherWorksDTO);
        if (otherWorksDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new otherWorks cannot already have an ID")).body(null);
        }
        OtherWorksDTO result = otherWorksService.save(otherWorksDTO);
        return ResponseEntity.created(new URI("/api/other-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /other-works : Updates an existing otherWorks.
     *
     * @param otherWorksDTO the otherWorksDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated otherWorksDTO,
     * or with status 400 (Bad Request) if the otherWorksDTO is not valid,
     * or with status 500 (Internal Server Error) if the otherWorksDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/other-works")
    @Timed
    public ResponseEntity<OtherWorksDTO> updateOtherWorks(@Valid @RequestBody OtherWorksDTO otherWorksDTO) throws URISyntaxException {
        log.debug("REST request to update OtherWorks : {}", otherWorksDTO);
        if (otherWorksDTO.getId() == null) {
            return createOtherWorks(otherWorksDTO);
        }
        OtherWorksDTO result = otherWorksService.save(otherWorksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, otherWorksDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /other-works : get all the otherWorks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of otherWorks in body
     */
    @GetMapping("/other-works")
    @Timed
    public List<OtherWorksDTO> getAllOtherWorks() {
        log.debug("REST request to get all OtherWorks");
        return otherWorksService.findAll();
    }

    /**
     * GET  /other-works/:id : get the "id" otherWorks.
     *
     * @param id the id of the otherWorksDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the otherWorksDTO, or with status 404 (Not Found)
     */
    @GetMapping("/other-works/{id}")
    @Timed
    public ResponseEntity<OtherWorksDTO> getOtherWorks(@PathVariable Long id) {
        log.debug("REST request to get OtherWorks : {}", id);
        OtherWorksDTO otherWorksDTO = otherWorksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(otherWorksDTO));
    }

    /**
     * DELETE  /other-works/:id : delete the "id" otherWorks.
     *
     * @param id the id of the otherWorksDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/other-works/{id}")
    @Timed
    public ResponseEntity<Void> deleteOtherWorks(@PathVariable Long id) {
        log.debug("REST request to delete OtherWorks : {}", id);
        otherWorksService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/other-works?query=:query : search for the otherWorks corresponding
     * to the query.
     *
     * @param query the query of the otherWorks search 
     * @return the result of the search
     */
    @GetMapping("/_search/other-works")
    @Timed
    public List<OtherWorksDTO> searchOtherWorks(@RequestParam String query) {
        log.debug("REST request to search OtherWorks for query {}", query);
        return otherWorksService.search(query);
    }


}
