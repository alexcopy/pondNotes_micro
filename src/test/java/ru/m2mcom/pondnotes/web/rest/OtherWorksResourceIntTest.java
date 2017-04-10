package ru.m2mcom.pondnotes.web.rest;

import ru.m2mcom.pondnotes.PondNotesApp;

import ru.m2mcom.pondnotes.domain.OtherWorks;
import ru.m2mcom.pondnotes.repository.OtherWorksRepository;
import ru.m2mcom.pondnotes.service.OtherWorksService;
import ru.m2mcom.pondnotes.repository.search.OtherWorksSearchRepository;
import ru.m2mcom.pondnotes.service.dto.OtherWorksDTO;
import ru.m2mcom.pondnotes.service.mapper.OtherWorksMapper;
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
 * Test class for the OtherWorksResource REST controller.
 *
 * @see OtherWorksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PondNotesApp.class)
public class OtherWorksResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final Integer DEFAULT_QTY = 1;
    private static final Integer UPDATED_QTY = 2;

    private static final String DEFAULT_DESCRIPTON = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTON = "BBBBBBBBBB";

    private static final Double DEFAULT_TEMP_VAL = 1D;
    private static final Double UPDATED_TEMP_VAL = 2D;

    private static final Long DEFAULT_TIMESTAMP = 1L;
    private static final Long UPDATED_TIMESTAMP = 2L;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private OtherWorksRepository otherWorksRepository;

    @Autowired
    private OtherWorksMapper otherWorksMapper;

    @Autowired
    private OtherWorksService otherWorksService;

    @Autowired
    private OtherWorksSearchRepository otherWorksSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOtherWorksMockMvc;

    private OtherWorks otherWorks;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OtherWorksResource otherWorksResource = new OtherWorksResource(otherWorksService);
        this.restOtherWorksMockMvc = MockMvcBuilders.standaloneSetup(otherWorksResource)
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
    public static OtherWorks createEntity(EntityManager em) {
        OtherWorks otherWorks = new OtherWorks()
            .date(DEFAULT_DATE)
            .reason(DEFAULT_REASON)
            .qty(DEFAULT_QTY)
            .descripton(DEFAULT_DESCRIPTON)
            .tempVal(DEFAULT_TEMP_VAL)
            .timestamp(DEFAULT_TIMESTAMP)
            .userId(DEFAULT_USER_ID);
        return otherWorks;
    }

    @Before
    public void initTest() {
        otherWorksSearchRepository.deleteAll();
        otherWorks = createEntity(em);
    }

    @Test
    @Transactional
    public void createOtherWorks() throws Exception {
        int databaseSizeBeforeCreate = otherWorksRepository.findAll().size();

        // Create the OtherWorks
        OtherWorksDTO otherWorksDTO = otherWorksMapper.otherWorksToOtherWorksDTO(otherWorks);
        restOtherWorksMockMvc.perform(post("/api/other-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(otherWorksDTO)))
            .andExpect(status().isCreated());

        // Validate the OtherWorks in the database
        List<OtherWorks> otherWorksList = otherWorksRepository.findAll();
        assertThat(otherWorksList).hasSize(databaseSizeBeforeCreate + 1);
        OtherWorks testOtherWorks = otherWorksList.get(otherWorksList.size() - 1);
        assertThat(testOtherWorks.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOtherWorks.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testOtherWorks.getQty()).isEqualTo(DEFAULT_QTY);
        assertThat(testOtherWorks.getDescripton()).isEqualTo(DEFAULT_DESCRIPTON);
        assertThat(testOtherWorks.getTempVal()).isEqualTo(DEFAULT_TEMP_VAL);
        assertThat(testOtherWorks.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testOtherWorks.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the OtherWorks in Elasticsearch
        OtherWorks otherWorksEs = otherWorksSearchRepository.findOne(testOtherWorks.getId());
        assertThat(otherWorksEs).isEqualToComparingFieldByField(testOtherWorks);
    }

    @Test
    @Transactional
    public void createOtherWorksWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = otherWorksRepository.findAll().size();

        // Create the OtherWorks with an existing ID
        otherWorks.setId(1L);
        OtherWorksDTO otherWorksDTO = otherWorksMapper.otherWorksToOtherWorksDTO(otherWorks);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtherWorksMockMvc.perform(post("/api/other-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(otherWorksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OtherWorks> otherWorksList = otherWorksRepository.findAll();
        assertThat(otherWorksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = otherWorksRepository.findAll().size();
        // set the field null
        otherWorks.setDate(null);

        // Create the OtherWorks, which fails.
        OtherWorksDTO otherWorksDTO = otherWorksMapper.otherWorksToOtherWorksDTO(otherWorks);

        restOtherWorksMockMvc.perform(post("/api/other-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(otherWorksDTO)))
            .andExpect(status().isBadRequest());

        List<OtherWorks> otherWorksList = otherWorksRepository.findAll();
        assertThat(otherWorksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempValIsRequired() throws Exception {
        int databaseSizeBeforeTest = otherWorksRepository.findAll().size();
        // set the field null
        otherWorks.setTempVal(null);

        // Create the OtherWorks, which fails.
        OtherWorksDTO otherWorksDTO = otherWorksMapper.otherWorksToOtherWorksDTO(otherWorks);

        restOtherWorksMockMvc.perform(post("/api/other-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(otherWorksDTO)))
            .andExpect(status().isBadRequest());

        List<OtherWorks> otherWorksList = otherWorksRepository.findAll();
        assertThat(otherWorksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOtherWorks() throws Exception {
        // Initialize the database
        otherWorksRepository.saveAndFlush(otherWorks);

        // Get all the otherWorksList
        restOtherWorksMockMvc.perform(get("/api/other-works?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otherWorks.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].descripton").value(hasItem(DEFAULT_DESCRIPTON.toString())))
            .andExpect(jsonPath("$.[*].tempVal").value(hasItem(DEFAULT_TEMP_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getOtherWorks() throws Exception {
        // Initialize the database
        otherWorksRepository.saveAndFlush(otherWorks);

        // Get the otherWorks
        restOtherWorksMockMvc.perform(get("/api/other-works/{id}", otherWorks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(otherWorks.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.qty").value(DEFAULT_QTY))
            .andExpect(jsonPath("$.descripton").value(DEFAULT_DESCRIPTON.toString()))
            .andExpect(jsonPath("$.tempVal").value(DEFAULT_TEMP_VAL.doubleValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOtherWorks() throws Exception {
        // Get the otherWorks
        restOtherWorksMockMvc.perform(get("/api/other-works/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOtherWorks() throws Exception {
        // Initialize the database
        otherWorksRepository.saveAndFlush(otherWorks);
        otherWorksSearchRepository.save(otherWorks);
        int databaseSizeBeforeUpdate = otherWorksRepository.findAll().size();

        // Update the otherWorks
        OtherWorks updatedOtherWorks = otherWorksRepository.findOne(otherWorks.getId());
        updatedOtherWorks
            .date(UPDATED_DATE)
            .reason(UPDATED_REASON)
            .qty(UPDATED_QTY)
            .descripton(UPDATED_DESCRIPTON)
            .tempVal(UPDATED_TEMP_VAL)
            .timestamp(UPDATED_TIMESTAMP)
            .userId(UPDATED_USER_ID);
        OtherWorksDTO otherWorksDTO = otherWorksMapper.otherWorksToOtherWorksDTO(updatedOtherWorks);

        restOtherWorksMockMvc.perform(put("/api/other-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(otherWorksDTO)))
            .andExpect(status().isOk());

        // Validate the OtherWorks in the database
        List<OtherWorks> otherWorksList = otherWorksRepository.findAll();
        assertThat(otherWorksList).hasSize(databaseSizeBeforeUpdate);
        OtherWorks testOtherWorks = otherWorksList.get(otherWorksList.size() - 1);
        assertThat(testOtherWorks.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOtherWorks.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testOtherWorks.getQty()).isEqualTo(UPDATED_QTY);
        assertThat(testOtherWorks.getDescripton()).isEqualTo(UPDATED_DESCRIPTON);
        assertThat(testOtherWorks.getTempVal()).isEqualTo(UPDATED_TEMP_VAL);
        assertThat(testOtherWorks.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testOtherWorks.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the OtherWorks in Elasticsearch
        OtherWorks otherWorksEs = otherWorksSearchRepository.findOne(testOtherWorks.getId());
        assertThat(otherWorksEs).isEqualToComparingFieldByField(testOtherWorks);
    }

    @Test
    @Transactional
    public void updateNonExistingOtherWorks() throws Exception {
        int databaseSizeBeforeUpdate = otherWorksRepository.findAll().size();

        // Create the OtherWorks
        OtherWorksDTO otherWorksDTO = otherWorksMapper.otherWorksToOtherWorksDTO(otherWorks);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOtherWorksMockMvc.perform(put("/api/other-works")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(otherWorksDTO)))
            .andExpect(status().isCreated());

        // Validate the OtherWorks in the database
        List<OtherWorks> otherWorksList = otherWorksRepository.findAll();
        assertThat(otherWorksList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOtherWorks() throws Exception {
        // Initialize the database
        otherWorksRepository.saveAndFlush(otherWorks);
        otherWorksSearchRepository.save(otherWorks);
        int databaseSizeBeforeDelete = otherWorksRepository.findAll().size();

        // Get the otherWorks
        restOtherWorksMockMvc.perform(delete("/api/other-works/{id}", otherWorks.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean otherWorksExistsInEs = otherWorksSearchRepository.exists(otherWorks.getId());
        assertThat(otherWorksExistsInEs).isFalse();

        // Validate the database is empty
        List<OtherWorks> otherWorksList = otherWorksRepository.findAll();
        assertThat(otherWorksList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOtherWorks() throws Exception {
        // Initialize the database
        otherWorksRepository.saveAndFlush(otherWorks);
        otherWorksSearchRepository.save(otherWorks);

        // Search the otherWorks
        restOtherWorksMockMvc.perform(get("/api/_search/other-works?query=id:" + otherWorks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otherWorks.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].qty").value(hasItem(DEFAULT_QTY)))
            .andExpect(jsonPath("$.[*].descripton").value(hasItem(DEFAULT_DESCRIPTON.toString())))
            .andExpect(jsonPath("$.[*].tempVal").value(hasItem(DEFAULT_TEMP_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtherWorks.class);
    }
}
