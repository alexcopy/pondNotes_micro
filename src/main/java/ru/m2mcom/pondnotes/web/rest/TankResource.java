package ru.m2mcom.pondnotes.web.rest;

import com.codahale.metrics.annotation.Timed;
import ru.m2mcom.pondnotes.domain.Tank;
import ru.m2mcom.pondnotes.service.TankService;
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
 * REST controller for managing Tank.
 */
@RestController
@RequestMapping("/api")
public class TankResource {

    private final Logger log = LoggerFactory.getLogger(TankResource.class);

    private static final String ENTITY_NAME = "tank";
        
    private final TankService tankService;

    public TankResource(TankService tankService) {
        this.tankService = tankService;
    }

    /**
     * POST  /tanks : Create a new tank.
     *
     * @param tank the tank to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tank, or with status 400 (Bad Request) if the tank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tanks")
    @Timed
    public ResponseEntity<Tank> createTank(@Valid @RequestBody Tank tank) throws URISyntaxException {
        log.debug("REST request to save Tank : {}", tank);
        if (tank.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tank cannot already have an ID")).body(null);
        }
        Tank result = tankService.save(tank);
        return ResponseEntity.created(new URI("/api/tanks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tanks : Updates an existing tank.
     *
     * @param tank the tank to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tank,
     * or with status 400 (Bad Request) if the tank is not valid,
     * or with status 500 (Internal Server Error) if the tank couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tanks")
    @Timed
    public ResponseEntity<Tank> updateTank(@Valid @RequestBody Tank tank) throws URISyntaxException {
        log.debug("REST request to update Tank : {}", tank);
        if (tank.getId() == null) {
            return createTank(tank);
        }
        Tank result = tankService.save(tank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tank.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tanks : get all the tanks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tanks in body
     */
    @GetMapping("/tanks")
    @Timed
    public List<Tank> getAllTanks() {
        log.debug("REST request to get all Tanks");
        return tankService.findAll();
    }

    /**
     * GET  /tanks/:id : get the "id" tank.
     *
     * @param id the id of the tank to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tank, or with status 404 (Not Found)
     */
    @GetMapping("/tanks/{id}")
    @Timed
    public ResponseEntity<Tank> getTank(@PathVariable Long id) {
        log.debug("REST request to get Tank : {}", id);
        Tank tank = tankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tank));
    }

    /**
     * DELETE  /tanks/:id : delete the "id" tank.
     *
     * @param id the id of the tank to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tanks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTank(@PathVariable Long id) {
        log.debug("REST request to delete Tank : {}", id);
        tankService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tanks?query=:query : search for the tank corresponding
     * to the query.
     *
     * @param query the query of the tank search 
     * @return the result of the search
     */
    @GetMapping("/_search/tanks")
    @Timed
    public List<Tank> searchTanks(@RequestParam String query) {
        log.debug("REST request to search Tanks for query {}", query);
        return tankService.search(query);
    }


}
