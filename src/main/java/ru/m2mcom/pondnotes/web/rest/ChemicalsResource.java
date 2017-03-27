package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.domain.Chemicals;
import ru.m2mcom.pondnotes.service.ChemicalsService;
import ru.m2mcom.pondnotes.web.rest.util.HeaderUtil;
import ru.m2mcom.pondnotes.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing Chemicals.
 */
@RestController
@RequestMapping("/api")
public class ChemicalsResource {

    private final Logger log = LoggerFactory.getLogger(ChemicalsResource.class);

    private static final String ENTITY_NAME = "chemicals";
        
    private final ChemicalsService chemicalsService;

    public ChemicalsResource(ChemicalsService chemicalsService) {
        this.chemicalsService = chemicalsService;
    }

    /**
     * POST  /chemicals : Create a new chemicals.
     *
     * @param chemicals the chemicals to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chemicals, or with status 400 (Bad Request) if the chemicals has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chemicals")
    @Timed
    public ResponseEntity<Chemicals> createChemicals(@Valid @RequestBody Chemicals chemicals) throws URISyntaxException {
        log.debug("REST request to save Chemicals : {}", chemicals);
        if (chemicals.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new chemicals cannot already have an ID")).body(null);
        }
        Chemicals result = chemicalsService.save(chemicals);
        return ResponseEntity.created(new URI("/api/chemicals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chemicals : Updates an existing chemicals.
     *
     * @param chemicals the chemicals to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chemicals,
     * or with status 400 (Bad Request) if the chemicals is not valid,
     * or with status 500 (Internal Server Error) if the chemicals couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chemicals")
    @Timed
    public ResponseEntity<Chemicals> updateChemicals(@Valid @RequestBody Chemicals chemicals) throws URISyntaxException {
        log.debug("REST request to update Chemicals : {}", chemicals);
        if (chemicals.getId() == null) {
            return createChemicals(chemicals);
        }
        Chemicals result = chemicalsService.save(chemicals);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chemicals.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chemicals : get all the chemicals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chemicals in body
     */
    @GetMapping("/chemicals")
    @Timed
    public ResponseEntity<List<Chemicals>> getAllChemicals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Chemicals");
        Page<Chemicals> page = chemicalsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chemicals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chemicals/:id : get the "id" chemicals.
     *
     * @param id the id of the chemicals to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chemicals, or with status 404 (Not Found)
     */
    @GetMapping("/chemicals/{id}")
    @Timed
    public ResponseEntity<Chemicals> getChemicals(@PathVariable Long id) {
        log.debug("REST request to get Chemicals : {}", id);
        Chemicals chemicals = chemicalsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chemicals));
    }

    /**
     * DELETE  /chemicals/:id : delete the "id" chemicals.
     *
     * @param id the id of the chemicals to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chemicals/{id}")
    @Timed
    public ResponseEntity<Void> deleteChemicals(@PathVariable Long id) {
        log.debug("REST request to delete Chemicals : {}", id);
        chemicalsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/chemicals?query=:query : search for the chemicals corresponding
     * to the query.
     *
     * @param query the query of the chemicals search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/chemicals")
    @Timed
    public ResponseEntity<List<Chemicals>> searchChemicals(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Chemicals for query {}", query);
        Page<Chemicals> page = chemicalsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/chemicals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
