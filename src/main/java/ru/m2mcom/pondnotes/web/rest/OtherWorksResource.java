package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.domain.OtherWorks;
import ru.m2mcom.pondnotes.service.OtherWorksService;
import ru.m2mcom.pondnotes.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
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
     * @param otherWorks the otherWorks to create
     * @return the ResponseEntity with status 201 (Created) and with body the new otherWorks, or with status 400 (Bad Request) if the otherWorks has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/other-works")
    @Timed
    public ResponseEntity<OtherWorks> createOtherWorks(@Valid @RequestBody OtherWorks otherWorks) throws URISyntaxException {
        log.debug("REST request to save OtherWorks : {}", otherWorks);
        if (otherWorks.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new otherWorks cannot already have an ID")).body(null);
        }
        OtherWorks result = otherWorksService.save(otherWorks);
        return ResponseEntity.created(new URI("/api/other-works/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /other-works : Updates an existing otherWorks.
     *
     * @param otherWorks the otherWorks to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated otherWorks,
     * or with status 400 (Bad Request) if the otherWorks is not valid,
     * or with status 500 (Internal Server Error) if the otherWorks couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/other-works")
    @Timed
    public ResponseEntity<OtherWorks> updateOtherWorks(@Valid @RequestBody OtherWorks otherWorks) throws URISyntaxException {
        log.debug("REST request to update OtherWorks : {}", otherWorks);
        if (otherWorks.getId() == null) {
            return createOtherWorks(otherWorks);
        }
        OtherWorks result = otherWorksService.save(otherWorks);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, otherWorks.getId().toString()))
            .body(result);
    }

    /**
     * GET  /other-works : get all the otherWorks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of otherWorks in body
     */
    @GetMapping("/other-works")
    @Timed
    public List<OtherWorks> getAllOtherWorks() {
        log.debug("REST request to get all OtherWorks");
        return otherWorksService.findAll();
    }

    /**
     * GET  /other-works/:id : get the "id" otherWorks.
     *
     * @param id the id of the otherWorks to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the otherWorks, or with status 404 (Not Found)
     */
    @GetMapping("/other-works/{id}")
    @Timed
    public ResponseEntity<OtherWorks> getOtherWorks(@PathVariable Long id) {
        log.debug("REST request to get OtherWorks : {}", id);
        OtherWorks otherWorks = otherWorksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(otherWorks));
    }

    /**
     * DELETE  /other-works/:id : delete the "id" otherWorks.
     *
     * @param id the id of the otherWorks to delete
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
    public List<OtherWorks> searchOtherWorks(@RequestParam String query) {
        log.debug("REST request to search OtherWorks for query {}", query);
        return otherWorksService.search(query);
    }


}
