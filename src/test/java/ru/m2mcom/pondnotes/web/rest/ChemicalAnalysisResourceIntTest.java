package ru.m2mcom.pondnotes.web.rest;

import ru.m2mcom.pondnotes.PondNotesApp;

import ru.m2mcom.pondnotes.domain.ChemicalAnalysis;
import ru.m2mcom.pondnotes.repository.ChemicalAnalysisRepository;
import ru.m2mcom.pondnotes.service.ChemicalAnalysisService;
import ru.m2mcom.pondnotes.repository.search.ChemicalAnalysisSearchRepository;
import ru.m2mcom.pondnotes.service.dto.ChemicalAnalysisDTO;
import ru.m2mcom.pondnotes.service.mapper.ChemicalAnalysisMapper;
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
 * Test class for the ChemicalAnalysisResource REST controller.
 *
 * @see ChemicalAnalysisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PondNotesApp.class)
public class ChemicalAnalysisResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_N_O_2 = "AAAAAAAAAA";
    private static final String UPDATED_N_O_2 = "BBBBBBBBBB";

    private static final String DEFAULT_N_O_3 = "AAAAAAAAAA";
    private static final String UPDATED_N_O_3 = "BBBBBBBBBB";

    private static final String DEFAULT_N_H_4 = "AAAAAAAAAA";
    private static final String UPDATED_N_H_4 = "BBBBBBBBBB";

    private static final String DEFAULT_PH = "AAAAAAAAAA";
    private static final String UPDATED_PH = "BBBBBBBBBB";

    private static final Double DEFAULT_TEMP_VAL = 1D;
    private static final Double UPDATED_TEMP_VAL = 2D;

    private static final Long DEFAULT_TIMESTAMP = 1L;
    private static final Long UPDATED_TIMESTAMP = 2L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private ChemicalAnalysisRepository chemicalAnalysisRepository;

    @Autowired
    private ChemicalAnalysisMapper chemicalAnalysisMapper;

    @Autowired
    private ChemicalAnalysisService chemicalAnalysisService;

    @Autowired
    private ChemicalAnalysisSearchRepository chemicalAnalysisSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChemicalAnalysisMockMvc;

    private ChemicalAnalysis chemicalAnalysis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChemicalAnalysisResource chemicalAnalysisResource = new ChemicalAnalysisResource(chemicalAnalysisService);
        this.restChemicalAnalysisMockMvc = MockMvcBuilders.standaloneSetup(chemicalAnalysisResource)
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
    public static ChemicalAnalysis createEntity(EntityManager em) {
        ChemicalAnalysis chemicalAnalysis = new ChemicalAnalysis()
            .date(DEFAULT_DATE)
            .nO2(DEFAULT_N_O_2)
            .nO3(DEFAULT_N_O_3)
            .nH4(DEFAULT_N_H_4)
            .ph(DEFAULT_PH)
            .tempVal(DEFAULT_TEMP_VAL)
            .timestamp(DEFAULT_TIMESTAMP)
            .userId(DEFAULT_USER_ID);
        return chemicalAnalysis;
    }

    @Before
    public void initTest() {
        chemicalAnalysisSearchRepository.deleteAll();
        chemicalAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createChemicalAnalysis() throws Exception {
        int databaseSizeBeforeCreate = chemicalAnalysisRepository.findAll().size();

        // Create the ChemicalAnalysis
        ChemicalAnalysisDTO chemicalAnalysisDTO = chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(chemicalAnalysis);
        restChemicalAnalysisMockMvc.perform(post("/api/chemical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicalAnalysisDTO)))
            .andExpect(status().isCreated());

        // Validate the ChemicalAnalysis in the database
        List<ChemicalAnalysis> chemicalAnalysisList = chemicalAnalysisRepository.findAll();
        assertThat(chemicalAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        ChemicalAnalysis testChemicalAnalysis = chemicalAnalysisList.get(chemicalAnalysisList.size() - 1);
        assertThat(testChemicalAnalysis.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testChemicalAnalysis.getnO2()).isEqualTo(DEFAULT_N_O_2);
        assertThat(testChemicalAnalysis.getnO3()).isEqualTo(DEFAULT_N_O_3);
        assertThat(testChemicalAnalysis.getnH4()).isEqualTo(DEFAULT_N_H_4);
        assertThat(testChemicalAnalysis.getPh()).isEqualTo(DEFAULT_PH);
        assertThat(testChemicalAnalysis.getTempVal()).isEqualTo(DEFAULT_TEMP_VAL);
        assertThat(testChemicalAnalysis.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testChemicalAnalysis.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the ChemicalAnalysis in Elasticsearch
        ChemicalAnalysis chemicalAnalysisEs = chemicalAnalysisSearchRepository.findOne(testChemicalAnalysis.getId());
        assertThat(chemicalAnalysisEs).isEqualToComparingFieldByField(testChemicalAnalysis);
    }

    @Test
    @Transactional
    public void createChemicalAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chemicalAnalysisRepository.findAll().size();

        // Create the ChemicalAnalysis with an existing ID
        chemicalAnalysis.setId(1L);
        ChemicalAnalysisDTO chemicalAnalysisDTO = chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(chemicalAnalysis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChemicalAnalysisMockMvc.perform(post("/api/chemical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicalAnalysisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ChemicalAnalysis> chemicalAnalysisList = chemicalAnalysisRepository.findAll();
        assertThat(chemicalAnalysisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = chemicalAnalysisRepository.findAll().size();
        // set the field null
        chemicalAnalysis.setDate(null);

        // Create the ChemicalAnalysis, which fails.
        ChemicalAnalysisDTO chemicalAnalysisDTO = chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(chemicalAnalysis);

        restChemicalAnalysisMockMvc.perform(post("/api/chemical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicalAnalysisDTO)))
            .andExpect(status().isBadRequest());

        List<ChemicalAnalysis> chemicalAnalysisList = chemicalAnalysisRepository.findAll();
        assertThat(chemicalAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempValIsRequired() throws Exception {
        int databaseSizeBeforeTest = chemicalAnalysisRepository.findAll().size();
        // set the field null
        chemicalAnalysis.setTempVal(null);

        // Create the ChemicalAnalysis, which fails.
        ChemicalAnalysisDTO chemicalAnalysisDTO = chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(chemicalAnalysis);

        restChemicalAnalysisMockMvc.perform(post("/api/chemical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicalAnalysisDTO)))
            .andExpect(status().isBadRequest());

        List<ChemicalAnalysis> chemicalAnalysisList = chemicalAnalysisRepository.findAll();
        assertThat(chemicalAnalysisList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChemicalAnalyses() throws Exception {
        // Initialize the database
        chemicalAnalysisRepository.saveAndFlush(chemicalAnalysis);

        // Get all the chemicalAnalysisList
        restChemicalAnalysisMockMvc.perform(get("/api/chemical-analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chemicalAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].nO2").value(hasItem(DEFAULT_N_O_2.toString())))
            .andExpect(jsonPath("$.[*].nO3").value(hasItem(DEFAULT_N_O_3.toString())))
            .andExpect(jsonPath("$.[*].nH4").value(hasItem(DEFAULT_N_H_4.toString())))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.toString())))
            .andExpect(jsonPath("$.[*].tempVal").value(hasItem(DEFAULT_TEMP_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getChemicalAnalysis() throws Exception {
        // Initialize the database
        chemicalAnalysisRepository.saveAndFlush(chemicalAnalysis);

        // Get the chemicalAnalysis
        restChemicalAnalysisMockMvc.perform(get("/api/chemical-analyses/{id}", chemicalAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chemicalAnalysis.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.nO2").value(DEFAULT_N_O_2.toString()))
            .andExpect(jsonPath("$.nO3").value(DEFAULT_N_O_3.toString()))
            .andExpect(jsonPath("$.nH4").value(DEFAULT_N_H_4.toString()))
            .andExpect(jsonPath("$.ph").value(DEFAULT_PH.toString()))
            .andExpect(jsonPath("$.tempVal").value(DEFAULT_TEMP_VAL.doubleValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChemicalAnalysis() throws Exception {
        // Get the chemicalAnalysis
        restChemicalAnalysisMockMvc.perform(get("/api/chemical-analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChemicalAnalysis() throws Exception {
        // Initialize the database
        chemicalAnalysisRepository.saveAndFlush(chemicalAnalysis);
        chemicalAnalysisSearchRepository.save(chemicalAnalysis);
        int databaseSizeBeforeUpdate = chemicalAnalysisRepository.findAll().size();

        // Update the chemicalAnalysis
        ChemicalAnalysis updatedChemicalAnalysis = chemicalAnalysisRepository.findOne(chemicalAnalysis.getId());
        updatedChemicalAnalysis
            .date(UPDATED_DATE)
            .nO2(UPDATED_N_O_2)
            .nO3(UPDATED_N_O_3)
            .nH4(UPDATED_N_H_4)
            .ph(UPDATED_PH)
            .tempVal(UPDATED_TEMP_VAL)
            .timestamp(UPDATED_TIMESTAMP)
            .userId(UPDATED_USER_ID);
        ChemicalAnalysisDTO chemicalAnalysisDTO = chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(updatedChemicalAnalysis);

        restChemicalAnalysisMockMvc.perform(put("/api/chemical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicalAnalysisDTO)))
            .andExpect(status().isOk());

        // Validate the ChemicalAnalysis in the database
        List<ChemicalAnalysis> chemicalAnalysisList = chemicalAnalysisRepository.findAll();
        assertThat(chemicalAnalysisList).hasSize(databaseSizeBeforeUpdate);
        ChemicalAnalysis testChemicalAnalysis = chemicalAnalysisList.get(chemicalAnalysisList.size() - 1);
        assertThat(testChemicalAnalysis.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testChemicalAnalysis.getnO2()).isEqualTo(UPDATED_N_O_2);
        assertThat(testChemicalAnalysis.getnO3()).isEqualTo(UPDATED_N_O_3);
        assertThat(testChemicalAnalysis.getnH4()).isEqualTo(UPDATED_N_H_4);
        assertThat(testChemicalAnalysis.getPh()).isEqualTo(UPDATED_PH);
        assertThat(testChemicalAnalysis.getTempVal()).isEqualTo(UPDATED_TEMP_VAL);
        assertThat(testChemicalAnalysis.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testChemicalAnalysis.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the ChemicalAnalysis in Elasticsearch
        ChemicalAnalysis chemicalAnalysisEs = chemicalAnalysisSearchRepository.findOne(testChemicalAnalysis.getId());
        assertThat(chemicalAnalysisEs).isEqualToComparingFieldByField(testChemicalAnalysis);
    }

    @Test
    @Transactional
    public void updateNonExistingChemicalAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = chemicalAnalysisRepository.findAll().size();

        // Create the ChemicalAnalysis
        ChemicalAnalysisDTO chemicalAnalysisDTO = chemicalAnalysisMapper.chemicalAnalysisToChemicalAnalysisDTO(chemicalAnalysis);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChemicalAnalysisMockMvc.perform(put("/api/chemical-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chemicalAnalysisDTO)))
            .andExpect(status().isCreated());

        // Validate the ChemicalAnalysis in the database
        List<ChemicalAnalysis> chemicalAnalysisList = chemicalAnalysisRepository.findAll();
        assertThat(chemicalAnalysisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChemicalAnalysis() throws Exception {
        // Initialize the database
        chemicalAnalysisRepository.saveAndFlush(chemicalAnalysis);
        chemicalAnalysisSearchRepository.save(chemicalAnalysis);
        int databaseSizeBeforeDelete = chemicalAnalysisRepository.findAll().size();

        // Get the chemicalAnalysis
        restChemicalAnalysisMockMvc.perform(delete("/api/chemical-analyses/{id}", chemicalAnalysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean chemicalAnalysisExistsInEs = chemicalAnalysisSearchRepository.exists(chemicalAnalysis.getId());
        assertThat(chemicalAnalysisExistsInEs).isFalse();

        // Validate the database is empty
        List<ChemicalAnalysis> chemicalAnalysisList = chemicalAnalysisRepository.findAll();
        assertThat(chemicalAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchChemicalAnalysis() throws Exception {
        // Initialize the database
        chemicalAnalysisRepository.saveAndFlush(chemicalAnalysis);
        chemicalAnalysisSearchRepository.save(chemicalAnalysis);

        // Search the chemicalAnalysis
        restChemicalAnalysisMockMvc.perform(get("/api/_search/chemical-analyses?query=id:" + chemicalAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chemicalAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].nO2").value(hasItem(DEFAULT_N_O_2.toString())))
            .andExpect(jsonPath("$.[*].nO3").value(hasItem(DEFAULT_N_O_3.toString())))
            .andExpect(jsonPath("$.[*].nH4").value(hasItem(DEFAULT_N_H_4.toString())))
            .andExpect(jsonPath("$.[*].ph").value(hasItem(DEFAULT_PH.toString())))
            .andExpect(jsonPath("$.[*].tempVal").value(hasItem(DEFAULT_TEMP_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChemicalAnalysis.class);
    }
}
