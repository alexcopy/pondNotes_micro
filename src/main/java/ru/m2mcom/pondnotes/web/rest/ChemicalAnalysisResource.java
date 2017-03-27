package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.domain.ChemicalAnalysis;
import ru.m2mcom.pondnotes.service.ChemicalAnalysisService;
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
 * REST controller for managing ChemicalAnalysis.
 */
@RestController
@RequestMapping("/api")
public class ChemicalAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(ChemicalAnalysisResource.class);

    private static final String ENTITY_NAME = "chemicalAnalysis";
        
    private final ChemicalAnalysisService chemicalAnalysisService;

    public ChemicalAnalysisResource(ChemicalAnalysisService chemicalAnalysisService) {
        this.chemicalAnalysisService = chemicalAnalysisService;
    }

    /**
     * POST  /chemical-analyses : Create a new chemicalAnalysis.
     *
     * @param chemicalAnalysis the chemicalAnalysis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chemicalAnalysis, or with status 400 (Bad Request) if the chemicalAnalysis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/chemical-analyses")
    @Timed
    public ResponseEntity<ChemicalAnalysis> createChemicalAnalysis(@Valid @RequestBody ChemicalAnalysis chemicalAnalysis) throws URISyntaxException {
        log.debug("REST request to save ChemicalAnalysis : {}", chemicalAnalysis);
        if (chemicalAnalysis.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new chemicalAnalysis cannot already have an ID")).body(null);
        }
        ChemicalAnalysis result = chemicalAnalysisService.save(chemicalAnalysis);
        return ResponseEntity.created(new URI("/api/chemical-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /chemical-analyses : Updates an existing chemicalAnalysis.
     *
     * @param chemicalAnalysis the chemicalAnalysis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chemicalAnalysis,
     * or with status 400 (Bad Request) if the chemicalAnalysis is not valid,
     * or with status 500 (Internal Server Error) if the chemicalAnalysis couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/chemical-analyses")
    @Timed
    public ResponseEntity<ChemicalAnalysis> updateChemicalAnalysis(@Valid @RequestBody ChemicalAnalysis chemicalAnalysis) throws URISyntaxException {
        log.debug("REST request to update ChemicalAnalysis : {}", chemicalAnalysis);
        if (chemicalAnalysis.getId() == null) {
            return createChemicalAnalysis(chemicalAnalysis);
        }
        ChemicalAnalysis result = chemicalAnalysisService.save(chemicalAnalysis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chemicalAnalysis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /chemical-analyses : get all the chemicalAnalyses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chemicalAnalyses in body
     */
    @GetMapping("/chemical-analyses")
    @Timed
    public ResponseEntity<List<ChemicalAnalysis>> getAllChemicalAnalyses(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ChemicalAnalyses");
        Page<ChemicalAnalysis> page = chemicalAnalysisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/chemical-analyses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /chemical-analyses/:id : get the "id" chemicalAnalysis.
     *
     * @param id the id of the chemicalAnalysis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chemicalAnalysis, or with status 404 (Not Found)
     */
    @GetMapping("/chemical-analyses/{id}")
    @Timed
    public ResponseEntity<ChemicalAnalysis> getChemicalAnalysis(@PathVariable Long id) {
        log.debug("REST request to get ChemicalAnalysis : {}", id);
        ChemicalAnalysis chemicalAnalysis = chemicalAnalysisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chemicalAnalysis));
    }

    /**
     * DELETE  /chemical-analyses/:id : delete the "id" chemicalAnalysis.
     *
     * @param id the id of the chemicalAnalysis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/chemical-analyses/{id}")
    @Timed
    public ResponseEntity<Void> deleteChemicalAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete ChemicalAnalysis : {}", id);
        chemicalAnalysisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/chemical-analyses?query=:query : search for the chemicalAnalysis corresponding
     * to the query.
     *
     * @param query the query of the chemicalAnalysis search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/chemical-analyses")
    @Timed
    public ResponseEntity<List<ChemicalAnalysis>> searchChemicalAnalyses(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ChemicalAnalyses for query {}", query);
        Page<ChemicalAnalysis> page = chemicalAnalysisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/chemical-analyses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
