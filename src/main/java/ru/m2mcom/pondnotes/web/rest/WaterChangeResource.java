package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.domain.WaterChange;
import ru.m2mcom.pondnotes.service.WaterChangeService;
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
 * REST controller for managing WaterChange.
 */
@RestController
@RequestMapping("/api")
public class WaterChangeResource {

    private final Logger log = LoggerFactory.getLogger(WaterChangeResource.class);

    private static final String ENTITY_NAME = "waterChange";
        
    private final WaterChangeService waterChangeService;

    public WaterChangeResource(WaterChangeService waterChangeService) {
        this.waterChangeService = waterChangeService;
    }

    /**
     * POST  /water-changes : Create a new waterChange.
     *
     * @param waterChange the waterChange to create
     * @return the ResponseEntity with status 201 (Created) and with body the new waterChange, or with status 400 (Bad Request) if the waterChange has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/water-changes")
    @Timed
    public ResponseEntity<WaterChange> createWaterChange(@Valid @RequestBody WaterChange waterChange) throws URISyntaxException {
        log.debug("REST request to save WaterChange : {}", waterChange);
        if (waterChange.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new waterChange cannot already have an ID")).body(null);
        }
        WaterChange result = waterChangeService.save(waterChange);
        return ResponseEntity.created(new URI("/api/water-changes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /water-changes : Updates an existing waterChange.
     *
     * @param waterChange the waterChange to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated waterChange,
     * or with status 400 (Bad Request) if the waterChange is not valid,
     * or with status 500 (Internal Server Error) if the waterChange couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/water-changes")
    @Timed
    public ResponseEntity<WaterChange> updateWaterChange(@Valid @RequestBody WaterChange waterChange) throws URISyntaxException {
        log.debug("REST request to update WaterChange : {}", waterChange);
        if (waterChange.getId() == null) {
            return createWaterChange(waterChange);
        }
        WaterChange result = waterChangeService.save(waterChange);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, waterChange.getId().toString()))
            .body(result);
    }

    /**
     * GET  /water-changes : get all the waterChanges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of waterChanges in body
     */
    @GetMapping("/water-changes")
    @Timed
    public List<WaterChange> getAllWaterChanges() {
        log.debug("REST request to get all WaterChanges");
        return waterChangeService.findAll();
    }

    /**
     * GET  /water-changes/:id : get the "id" waterChange.
     *
     * @param id the id of the waterChange to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the waterChange, or with status 404 (Not Found)
     */
    @GetMapping("/water-changes/{id}")
    @Timed
    public ResponseEntity<WaterChange> getWaterChange(@PathVariable Long id) {
        log.debug("REST request to get WaterChange : {}", id);
        WaterChange waterChange = waterChangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(waterChange));
    }

    /**
     * DELETE  /water-changes/:id : delete the "id" waterChange.
     *
     * @param id the id of the waterChange to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/water-changes/{id}")
    @Timed
    public ResponseEntity<Void> deleteWaterChange(@PathVariable Long id) {
        log.debug("REST request to delete WaterChange : {}", id);
        waterChangeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/water-changes?query=:query : search for the waterChange corresponding
     * to the query.
     *
     * @param query the query of the waterChange search 
     * @return the result of the search
     */
    @GetMapping("/_search/water-changes")
    @Timed
    public List<WaterChange> searchWaterChanges(@RequestParam String query) {
        log.debug("REST request to search WaterChanges for query {}", query);
        return waterChangeService.search(query);
    }


}
