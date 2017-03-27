package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.domain.LiveStock;
import ru.m2mcom.pondnotes.service.LiveStockService;
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
 * REST controller for managing LiveStock.
 */
@RestController
@RequestMapping("/api")
public class LiveStockResource {

    private final Logger log = LoggerFactory.getLogger(LiveStockResource.class);

    private static final String ENTITY_NAME = "liveStock";
        
    private final LiveStockService liveStockService;

    public LiveStockResource(LiveStockService liveStockService) {
        this.liveStockService = liveStockService;
    }

    /**
     * POST  /live-stocks : Create a new liveStock.
     *
     * @param liveStock the liveStock to create
     * @return the ResponseEntity with status 201 (Created) and with body the new liveStock, or with status 400 (Bad Request) if the liveStock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/live-stocks")
    @Timed
    public ResponseEntity<LiveStock> createLiveStock(@Valid @RequestBody LiveStock liveStock) throws URISyntaxException {
        log.debug("REST request to save LiveStock : {}", liveStock);
        if (liveStock.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new liveStock cannot already have an ID")).body(null);
        }
        LiveStock result = liveStockService.save(liveStock);
        return ResponseEntity.created(new URI("/api/live-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /live-stocks : Updates an existing liveStock.
     *
     * @param liveStock the liveStock to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated liveStock,
     * or with status 400 (Bad Request) if the liveStock is not valid,
     * or with status 500 (Internal Server Error) if the liveStock couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/live-stocks")
    @Timed
    public ResponseEntity<LiveStock> updateLiveStock(@Valid @RequestBody LiveStock liveStock) throws URISyntaxException {
        log.debug("REST request to update LiveStock : {}", liveStock);
        if (liveStock.getId() == null) {
            return createLiveStock(liveStock);
        }
        LiveStock result = liveStockService.save(liveStock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, liveStock.getId().toString()))
            .body(result);
    }

    /**
     * GET  /live-stocks : get all the liveStocks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of liveStocks in body
     */
    @GetMapping("/live-stocks")
    @Timed
    public ResponseEntity<List<LiveStock>> getAllLiveStocks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LiveStocks");
        Page<LiveStock> page = liveStockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/live-stocks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /live-stocks/:id : get the "id" liveStock.
     *
     * @param id the id of the liveStock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the liveStock, or with status 404 (Not Found)
     */
    @GetMapping("/live-stocks/{id}")
    @Timed
    public ResponseEntity<LiveStock> getLiveStock(@PathVariable Long id) {
        log.debug("REST request to get LiveStock : {}", id);
        LiveStock liveStock = liveStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(liveStock));
    }

    /**
     * DELETE  /live-stocks/:id : delete the "id" liveStock.
     *
     * @param id the id of the liveStock to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/live-stocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteLiveStock(@PathVariable Long id) {
        log.debug("REST request to delete LiveStock : {}", id);
        liveStockService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/live-stocks?query=:query : search for the liveStock corresponding
     * to the query.
     *
     * @param query the query of the liveStock search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/live-stocks")
    @Timed
    public ResponseEntity<List<LiveStock>> searchLiveStocks(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of LiveStocks for query {}", query);
        Page<LiveStock> page = liveStockService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/live-stocks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
