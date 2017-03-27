package ru.m2mcom.pondnotes.web.rest;

import ru.m2mcom.pondnotes.PondNotesApp;

import ru.m2mcom.pondnotes.domain.Chemicals;
import ru.m2mcom.pondnotes.repository.ChemicalsRepository;
import ru.m2mcom.pondnotes.service.ChemicalsService;
import ru.m2mcom.pondnotes.repository.search.ChemicalsSearchRepository;
import ru.m2mcom.pondnotes.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ru.m2mcom.pondnotes.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChemicalsResource REST controller.
 *
 * @see ChemicalsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PondNotesApp.class)
public class ChemicalsResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Double DEFAULT_TEMP_VAL = 1D;
    private static final Double UPDATED_TEMP_VAL = 2D;

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    @Autowired
    private ChemicalsRepository chemicalsRepository;

    @Autowired
    private ChemicalsService chemicalsService;

    @Autowired
    private ChemicalsSearchRepository chemicalsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChemicalsMockMvc;

    private Chemicals chemicals;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChemicalsResource chemicalsResource = new ChemicalsResource(chemicalsService);
        this.restChemicalsMockMvc = MockMvcBuilders.standaloneSetup(chemicalsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chemicals createEntity(EntityManager em) {
        Chemicals chemicals = new Chemicals()
            .date(DEFAULT_DATE)
            .qty(DEFAULT_QTY)
            .reason(DEFAULT_REASON)
            .tempVal(DEFAULT_TEMP_VAL)
            .timestamp(DEFAULT_TIMESTAMP);
        return chemicals;
    }

    @Before
    public void initTest() {
        chemicalsSearchRepository.deleteAll();
        chemicals = createEntity(em);
    }

    @Test
    @Transactional
    public void createChemicals() throws Exception {
        int databaseSizeBeforeCreate = chemicalsRepository.findAll().size();

        // Create the Chemicals
        restChemicalsMockMvc.perform(post("/api/chemicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicals)))
            .andExpect(status().isCreated());

        // Validate the Chemicals in the database
        List<Chemicals> chemicalsList = chemicalsRepository.findAll();
        assertThat(chemicalsList).hasSize(databaseSizeBeforeCreate + 1);
        Chemicals testChemicals = chemicalsList.get(chemicalsList.size() - 1);
        assertThat(testChemicals.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testChemicals.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testChemicals.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testChemicals.getTempVal()).isEqualTo(DEFAULT_TEMP_VAL);
        assertThat(testChemicals.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);

        // Validate the Chemicals in Elasticsearch
        Chemicals chemicalsEs = chemicalsSearchRepository.findOne(testChemicals.getId());
        assertThat(chemicalsEs).isEqualToComparingFieldByField(testChemicals);
    }

    @Test
    @Transactional
    public void createChemicalsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chemicalsRepository.findAll().size();

        // Create the Chemicals with an existing ID
        chemicals.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChemicalsMockMvc.perform(post("/api/chemicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicals)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Chemicals> chemicalsList = chemicalsRepository.findAll();
        assertThat(chemicalsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = chemicalsRepository.findAll().size();
        // set the field null
        chemicals.setDate(null);

        // Create the Chemicals, which fails.

        restChemicalsMockMvc.perform(post("/api/chemicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicals)))
            .andExpect(status().isBadRequest());

        List<Chemicals> chemicalsList = chemicalsRepository.findAll();
        assertThat(chemicalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempValIsRequired() throws Exception {
        int databaseSizeBeforeTest = chemicalsRepository.findAll().size();
        // set the field null
        chemicals.setTempVal(null);

        // Create the Chemicals, which fails.

        restChemicalsMockMvc.perform(post("/api/chemicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicals)))
            .andExpect(status().isBadRequest());

        List<Chemicals> chemicalsList = chemicalsRepository.findAll();
        assertThat(chemicalsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChemicals() throws Exception {
        // Initialize the database
        chemicalsRepository.saveAndFlush(chemicals);

        // Get all the chemicalsList
        restChemicalsMockMvc.perform(get("/api/chemicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chemicals.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].tempVal").value(hasItem(DEFAULT_TEMP_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getChemicals() throws Exception {
        // Initialize the database
        chemicalsRepository.saveAndFlush(chemicals);

        // Get the chemicals
        restChemicalsMockMvc.perform(get("/api/chemicals/{id}", chemicals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chemicals.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.tempVal").value(DEFAULT_TEMP_VAL.doubleValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP));
    }

    @Test
    @Transactional
    public void getNonExistingChemicals() throws Exception {
        // Get the chemicals
        restChemicalsMockMvc.perform(get("/api/chemicals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChemicals() throws Exception {
        // Initialize the database
        chemicalsService.save(chemicals);

        int databaseSizeBeforeUpdate = chemicalsRepository.findAll().size();

        // Update the chemicals
        Chemicals updatedChemicals = chemicalsRepository.findOne(chemicals.getId());
        updatedChemicals
            .date(UPDATED_DATE)
            .qty(UPDATED_QTY)
            .reason(UPDATED_REASON)
            .tempVal(UPDATED_TEMP_VAL)
            .timestamp(UPDATED_TIMESTAMP);

        restChemicalsMockMvc.perform(put("/api/chemicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChemicals)))
            .andExpect(status().isOk());

        // Validate the Chemicals in the database
        List<Chemicals> chemicalsList = chemicalsRepository.findAll();
        assertThat(chemicalsList).hasSize(databaseSizeBeforeUpdate);
        Chemicals testChemicals = chemicalsList.get(chemicalsList.size() - 1);
        assertThat(testChemicals.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testChemicals.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testChemicals.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testChemicals.getTempVal()).isEqualTo(UPDATED_TEMP_VAL);
        assertThat(testChemicals.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);

        // Validate the Chemicals in Elasticsearch
        Chemicals chemicalsEs = chemicalsSearchRepository.findOne(testChemicals.getId());
        assertThat(chemicalsEs).isEqualToComparingFieldByField(testChemicals);
    }

    @Test
    @Transactional
    public void updateNonExistingChemicals() throws Exception {
        int databaseSizeBeforeUpdate = chemicalsRepository.findAll().size();

        // Create the Chemicals

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChemicalsMockMvc.perform(put("/api/chemicals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicals)))
            .andExpect(status().isCreated());

        // Validate the Chemicals in the database
        List<Chemicals> chemicalsList = chemicalsRepository.findAll();
        assertThat(chemicalsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChemicals() throws Exception {
        // Initialize the database
        chemicalsService.save(chemicals);

        int databaseSizeBeforeDelete = chemicalsRepository.findAll().size();

        // Get the chemicals
        restChemicalsMockMvc.perform(delete("/api/chemicals/{id}", chemicals.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean chemicalsExistsInEs = chemicalsSearchRepository.exists(chemicals.getId());
        assertThat(chemicalsExistsInEs).isFalse();

        // Validate the database is empty
        List<Chemicals> chemicalsList = chemicalsRepository.findAll();
        assertThat(chemicalsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchChemicals() throws Exception {
        // Initialize the database
        chemicalsService.save(chemicals);

        // Search the chemicals
        restChemicalsMockMvc.perform(get("/api/_search/chemicals?query=id:" + chemicals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chemicals.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].tempVal").value(hasItem(DEFAULT_TEMP_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chemicals.class);
    }
}
