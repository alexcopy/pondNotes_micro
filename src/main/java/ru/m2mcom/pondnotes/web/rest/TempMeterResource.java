package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.service.TempMeterService;
import ru.m2mcom.pondnotes.web.rest.util.HeaderUtil;
import ru.m2mcom.pondnotes.web.rest.util.PaginationUtil;
import ru.m2mcom.pondnotes.service.dto.TempMeterDTO;
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
 * REST controller for managing TempMeter.
 */
@RestController
@RequestMapping("/api")
public class TempMeterResource {

    private final Logger log = LoggerFactory.getLogger(TempMeterResource.class);

    private static final String ENTITY_NAME = "tempMeter";
        
    private final TempMeterService tempMeterService;

    public TempMeterResource(TempMeterService tempMeterService) {
        this.tempMeterService = tempMeterService;
    }

    /**
     * POST  /temp-meters : Create a new tempMeter.
     *
     * @param tempMeterDTO the tempMeterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tempMeterDTO, or with status 400 (Bad Request) if the tempMeter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/temp-meters")
    @Timed
    public ResponseEntity<TempMeterDTO> createTempMeter(@Valid @RequestBody TempMeterDTO tempMeterDTO) throws URISyntaxException {
        log.debug("REST request to save TempMeter : {}", tempMeterDTO);
        if (tempMeterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tempMeter cannot already have an ID")).body(null);
        }
        TempMeterDTO result = tempMeterService.save(tempMeterDTO);
        return ResponseEntity.created(new URI("/api/temp-meters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /temp-meters : Updates an existing tempMeter.
     *
     * @param tempMeterDTO the tempMeterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tempMeterDTO,
     * or with status 400 (Bad Request) if the tempMeterDTO is not valid,
     * or with status 500 (Internal Server Error) if the tempMeterDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/temp-meters")
    @Timed
    public ResponseEntity<TempMeterDTO> updateTempMeter(@Valid @RequestBody TempMeterDTO tempMeterDTO) throws URISyntaxException {
        log.debug("REST request to update TempMeter : {}", tempMeterDTO);
        if (tempMeterDTO.getId() == null) {
            return createTempMeter(tempMeterDTO);
        }
        TempMeterDTO result = tempMeterService.save(tempMeterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tempMeterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /temp-meters : get all the tempMeters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tempMeters in body
     */
    @GetMapping("/temp-meters")
    @Timed
    public ResponseEntity<List<TempMeterDTO>> getAllTempMeters(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TempMeters");
        Page<TempMeterDTO> page = tempMeterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/temp-meters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /temp-meters/:id : get the "id" tempMeter.
     *
     * @param id the id of the tempMeterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tempMeterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/temp-meters/{id}")
    @Timed
    public ResponseEntity<TempMeterDTO> getTempMeter(@PathVariable Long id) {
        log.debug("REST request to get TempMeter : {}", id);
        TempMeterDTO tempMeterDTO = tempMeterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tempMeterDTO));
    }

    /**
     * DELETE  /temp-meters/:id : delete the "id" tempMeter.
     *
     * @param id the id of the tempMeterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/temp-meters/{id}")
    @Timed
    public ResponseEntity<Void> deleteTempMeter(@PathVariable Long id) {
        log.debug("REST request to delete TempMeter : {}", id);
        tempMeterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/temp-meters?query=:query : search for the tempMeter corresponding
     * to the query.
     *
     * @param query the query of the tempMeter search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/temp-meters")
    @Timed
    public ResponseEntity<List<TempMeterDTO>> searchTempMeters(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of TempMeters for query {}", query);
        Page<TempMeterDTO> page = tempMeterService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/temp-meters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
