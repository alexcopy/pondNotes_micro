package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.service.MeterReadingService;
import ru.m2mcom.pondnotes.web.rest.util.HeaderUtil;
import ru.m2mcom.pondnotes.web.rest.util.PaginationUtil;
import ru.m2mcom.pondnotes.service.dto.MeterReadingDTO;
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
 * REST controller for managing MeterReading.
 */
@RestController
@RequestMapping("/api")
public class MeterReadingResource {

    private final Logger log = LoggerFactory.getLogger(MeterReadingResource.class);

    private static final String ENTITY_NAME = "meterReading";
        
    private final MeterReadingService meterReadingService;

    public MeterReadingResource(MeterReadingService meterReadingService) {
        this.meterReadingService = meterReadingService;
    }

    /**
     * POST  /meter-readings : Create a new meterReading.
     *
     * @param meterReadingDTO the meterReadingDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new meterReadingDTO, or with status 400 (Bad Request) if the meterReading has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meter-readings")
    @Timed
    public ResponseEntity<MeterReadingDTO> createMeterReading(@Valid @RequestBody MeterReadingDTO meterReadingDTO) throws URISyntaxException {
        log.debug("REST request to save MeterReading : {}", meterReadingDTO);
        if (meterReadingDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new meterReading cannot already have an ID")).body(null);
        }
        MeterReadingDTO result = meterReadingService.save(meterReadingDTO);
        return ResponseEntity.created(new URI("/api/meter-readings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meter-readings : Updates an existing meterReading.
     *
     * @param meterReadingDTO the meterReadingDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated meterReadingDTO,
     * or with status 400 (Bad Request) if the meterReadingDTO is not valid,
     * or with status 500 (Internal Server Error) if the meterReadingDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meter-readings")
    @Timed
    public ResponseEntity<MeterReadingDTO> updateMeterReading(@Valid @RequestBody MeterReadingDTO meterReadingDTO) throws URISyntaxException {
        log.debug("REST request to update MeterReading : {}", meterReadingDTO);
        if (meterReadingDTO.getId() == null) {
            return createMeterReading(meterReadingDTO);
        }
        MeterReadingDTO result = meterReadingService.save(meterReadingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, meterReadingDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meter-readings : get all the meterReadings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meterReadings in body
     */
    @GetMapping("/meter-readings")
    @Timed
    public ResponseEntity<List<MeterReadingDTO>> getAllMeterReadings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MeterReadings");
        Page<MeterReadingDTO> page = meterReadingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meter-readings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /meter-readings/:id : get the "id" meterReading.
     *
     * @param id the id of the meterReadingDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the meterReadingDTO, or with status 404 (Not Found)
     */
    @GetMapping("/meter-readings/{id}")
    @Timed
    public ResponseEntity<MeterReadingDTO> getMeterReading(@PathVariable Long id) {
        log.debug("REST request to get MeterReading : {}", id);
        MeterReadingDTO meterReadingDTO = meterReadingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(meterReadingDTO));
    }

    /**
     * DELETE  /meter-readings/:id : delete the "id" meterReading.
     *
     * @param id the id of the meterReadingDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meter-readings/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeterReading(@PathVariable Long id) {
        log.debug("REST request to delete MeterReading : {}", id);
        meterReadingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/meter-readings?query=:query : search for the meterReading corresponding
     * to the query.
     *
     * @param query the query of the meterReading search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/meter-readings")
    @Timed
    public ResponseEntity<List<MeterReadingDTO>> searchMeterReadings(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MeterReadings for query {}", query);
        Page<MeterReadingDTO> page = meterReadingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/meter-readings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
