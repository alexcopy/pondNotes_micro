package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.service.FilterPumpCleaningService;
import ru.m2mcom.pondnotes.web.rest.util.HeaderUtil;
import ru.m2mcom.pondnotes.web.rest.util.PaginationUtil;
import ru.m2mcom.pondnotes.service.dto.FilterPumpCleaningDTO;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FilterPumpCleaning.
 */
@RestController
@RequestMapping("/api")
public class FilterPumpCleaningResource {

    private final Logger log = LoggerFactory.getLogger(FilterPumpCleaningResource.class);

    private static final String ENTITY_NAME = "filterPumpCleaning";
        
    private final FilterPumpCleaningService filterPumpCleaningService;

    public FilterPumpCleaningResource(FilterPumpCleaningService filterPumpCleaningService) {
        this.filterPumpCleaningService = filterPumpCleaningService;
    }

    /**
     * POST  /filter-pump-cleanings : Create a new filterPumpCleaning.
     *
     * @param filterPumpCleaningDTO the filterPumpCleaningDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filterPumpCleaningDTO, or with status 400 (Bad Request) if the filterPumpCleaning has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filter-pump-cleanings")
    @Timed
    public ResponseEntity<FilterPumpCleaningDTO> createFilterPumpCleaning(@Valid @RequestBody FilterPumpCleaningDTO filterPumpCleaningDTO) throws URISyntaxException {
        log.debug("REST request to save FilterPumpCleaning : {}", filterPumpCleaningDTO);
        if (filterPumpCleaningDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new filterPumpCleaning cannot already have an ID")).body(null);
        }
        FilterPumpCleaningDTO result = filterPumpCleaningService.save(filterPumpCleaningDTO);
        return ResponseEntity.created(new URI("/api/filter-pump-cleanings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filter-pump-cleanings : Updates an existing filterPumpCleaning.
     *
     * @param filterPumpCleaningDTO the filterPumpCleaningDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filterPumpCleaningDTO,
     * or with status 400 (Bad Request) if the filterPumpCleaningDTO is not valid,
     * or with status 500 (Internal Server Error) if the filterPumpCleaningDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filter-pump-cleanings")
    @Timed
    public ResponseEntity<FilterPumpCleaningDTO> updateFilterPumpCleaning(@Valid @RequestBody FilterPumpCleaningDTO filterPumpCleaningDTO) throws URISyntaxException {
        log.debug("REST request to update FilterPumpCleaning : {}", filterPumpCleaningDTO);
        if (filterPumpCleaningDTO.getId() == null) {
            return createFilterPumpCleaning(filterPumpCleaningDTO);
        }
        FilterPumpCleaningDTO result = filterPumpCleaningService.save(filterPumpCleaningDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filterPumpCleaningDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filter-pump-cleanings : get all the filterPumpCleanings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of filterPumpCleanings in body
     */
    @GetMapping("/filter-pump-cleanings")
    @Timed
    public ResponseEntity<List<FilterPumpCleaningDTO>> getAllFilterPumpCleanings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FilterPumpCleanings");
        Page<FilterPumpCleaningDTO> page = filterPumpCleaningService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filter-pump-cleanings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /filter-pump-cleanings/:id : get the "id" filterPumpCleaning.
     *
     * @param id the id of the filterPumpCleaningDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filterPumpCleaningDTO, or with status 404 (Not Found)
     */
    @GetMapping("/filter-pump-cleanings/{id}")
    @Timed
    public ResponseEntity<FilterPumpCleaningDTO> getFilterPumpCleaning(@PathVariable Long id) {
        log.debug("REST request to get FilterPumpCleaning : {}", id);
        FilterPumpCleaningDTO filterPumpCleaningDTO = filterPumpCleaningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filterPumpCleaningDTO));
    }

    /**
     * DELETE  /filter-pump-cleanings/:id : delete the "id" filterPumpCleaning.
     *
     * @param id the id of the filterPumpCleaningDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filter-pump-cleanings/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilterPumpCleaning(@PathVariable Long id) {
        log.debug("REST request to delete FilterPumpCleaning : {}", id);
        filterPumpCleaningService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/filter-pump-cleanings?query=:query : search for the filterPumpCleaning corresponding
     * to the query.
     *
     * @param query the query of the filterPumpCleaning search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/filter-pump-cleanings")
    @Timed
    public ResponseEntity<List<FilterPumpCleaningDTO>> searchFilterPumpCleanings(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of FilterPumpCleanings for query {}", query);
        Page<FilterPumpCleaningDTO> page = filterPumpCleaningService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/filter-pump-cleanings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
