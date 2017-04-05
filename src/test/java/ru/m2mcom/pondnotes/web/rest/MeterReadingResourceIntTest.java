package ru.m2mcom.pondnotes.web.rest;

import ru.m2mcom.pondnotes.PondNotesApp;

import ru.m2mcom.pondnotes.domain.MeterReading;
import ru.m2mcom.pondnotes.repository.MeterReadingRepository;
import ru.m2mcom.pondnotes.service.MeterReadingService;
import ru.m2mcom.pondnotes.repository.search.MeterReadingSearchRepository;
import ru.m2mcom.pondnotes.service.dto.MeterReadingDTO;
import ru.m2mcom.pondnotes.service.mapper.MeterReadingMapper;
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
 * Test class for the MeterReadingResource REST controller.
 *
 * @see MeterReadingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PondNotesApp.class)
public class MeterReadingResourceIntTest {

    private static final ZonedDateTime DEFAULT_READING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_READING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_READING = 1D;
    private static final Double UPDATED_READING = 2D;

    private static final Double DEFAULT_TEMP_VAL = 1D;
    private static final Double UPDATED_TEMP_VAL = 2D;

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    @Autowired
    private MeterReadingRepository meterReadingRepository;

    @Autowired
    private MeterReadingMapper meterReadingMapper;

    @Autowired
    private MeterReadingService meterReadingService;

    @Autowired
    private MeterReadingSearchRepository meterReadingSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeterReadingMockMvc;

    private MeterReading meterReading;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MeterReadingResource meterReadingResource = new MeterReadingResource(meterReadingService);
        this.restMeterReadingMockMvc = MockMvcBuilders.standaloneSetup(meterReadingResource)
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
    public static MeterReading createEntity(EntityManager em) {
        MeterReading meterReading = new MeterReading()
            .readingDate(DEFAULT_READING_DATE)
            .description(DEFAULT_DESCRIPTION)
            .reading(DEFAULT_READING)
            .tempVal(DEFAULT_TEMP_VAL)
            .timestamp(DEFAULT_TIMESTAMP)
            .userId(DEFAULT_USER_ID);
        return meterReading;
    }

    @Before
    public void initTest() {
        meterReadingSearchRepository.deleteAll();
        meterReading = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeterReading() throws Exception {
        int databaseSizeBeforeCreate = meterReadingRepository.findAll().size();

        // Create the MeterReading
        MeterReadingDTO meterReadingDTO = meterReadingMapper.meterReadingToMeterReadingDTO(meterReading);
        restMeterReadingMockMvc.perform(post("/api/meter-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterReadingDTO)))
            .andExpect(status().isCreated());

        // Validate the MeterReading in the database
        List<MeterReading> meterReadingList = meterReadingRepository.findAll();
        assertThat(meterReadingList).hasSize(databaseSizeBeforeCreate + 1);
        MeterReading testMeterReading = meterReadingList.get(meterReadingList.size() - 1);
        assertThat(testMeterReading.getReadingDate()).isEqualTo(DEFAULT_READING_DATE);
        assertThat(testMeterReading.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMeterReading.getReading()).isEqualTo(DEFAULT_READING);
        assertThat(testMeterReading.getTempVal()).isEqualTo(DEFAULT_TEMP_VAL);
        assertThat(testMeterReading.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testMeterReading.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the MeterReading in Elasticsearch
        MeterReading meterReadingEs = meterReadingSearchRepository.findOne(testMeterReading.getId());
        assertThat(meterReadingEs).isEqualToComparingFieldByField(testMeterReading);
    }

    @Test
    @Transactional
    public void createMeterReadingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meterReadingRepository.findAll().size();

        // Create the MeterReading with an existing ID
        meterReading.setId(1L);
        MeterReadingDTO meterReadingDTO = meterReadingMapper.meterReadingToMeterReadingDTO(meterReading);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeterReadingMockMvc.perform(post("/api/meter-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterReadingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MeterReading> meterReadingList = meterReadingRepository.findAll();
        assertThat(meterReadingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkReadingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterReadingRepository.findAll().size();
        // set the field null
        meterReading.setReadingDate(null);

        // Create the MeterReading, which fails.
        MeterReadingDTO meterReadingDTO = meterReadingMapper.meterReadingToMeterReadingDTO(meterReading);

        restMeterReadingMockMvc.perform(post("/api/meter-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterReadingDTO)))
            .andExpect(status().isBadRequest());

        List<MeterReading> meterReadingList = meterReadingRepository.findAll();
        assertThat(meterReadingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReadingIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterReadingRepository.findAll().size();
        // set the field null
        meterReading.setReading(null);

        // Create the MeterReading, which fails.
        MeterReadingDTO meterReadingDTO = meterReadingMapper.meterReadingToMeterReadingDTO(meterReading);

        restMeterReadingMockMvc.perform(post("/api/meter-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterReadingDTO)))
            .andExpect(status().isBadRequest());

        List<MeterReading> meterReadingList = meterReadingRepository.findAll();
        assertThat(meterReadingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempValIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterReadingRepository.findAll().size();
        // set the field null
        meterReading.setTempVal(null);

        // Create the MeterReading, which fails.
        MeterReadingDTO meterReadingDTO = meterReadingMapper.meterReadingToMeterReadingDTO(meterReading);

        restMeterReadingMockMvc.perform(post("/api/meter-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterReadingDTO)))
            .andExpect(status().isBadRequest());

        List<MeterReading> meterReadingList = meterReadingRepository.findAll();
        assertThat(meterReadingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeterReadings() throws Exception {
        // Initialize the database
        meterReadingRepository.saveAndFlush(meterReading);

        // Get all the meterReadingList
        restMeterReadingMockMvc.perform(get("/api/meter-readings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meterReading.getId().intValue())))
            .andExpect(jsonPath("$.[*].readingDate").value(hasItem(sameInstant(DEFAULT_READING_DATE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].reading").value(hasItem(DEFAULT_READING.doubleValue())))
            .andExpect(jsonPath("$.[*].tempVal").value(hasItem(DEFAULT_TEMP_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    public void getMeterReading() throws Exception {
        // Initialize the database
        meterReadingRepository.saveAndFlush(meterReading);

        // Get the meterReading
        restMeterReadingMockMvc.perform(get("/api/meter-readings/{id}", meterReading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meterReading.getId().intValue()))
            .andExpect(jsonPath("$.readingDate").value(sameInstant(DEFAULT_READING_DATE)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.reading").value(DEFAULT_READING.doubleValue()))
            .andExpect(jsonPath("$.tempVal").value(DEFAULT_TEMP_VAL.doubleValue()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    public void getNonExistingMeterReading() throws Exception {
        // Get the meterReading
        restMeterReadingMockMvc.perform(get("/api/meter-readings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeterReading() throws Exception {
        // Initialize the database
        meterReadingRepository.saveAndFlush(meterReading);
        meterReadingSearchRepository.save(meterReading);
        int databaseSizeBeforeUpdate = meterReadingRepository.findAll().size();

        // Update the meterReading
        MeterReading updatedMeterReading = meterReadingRepository.findOne(meterReading.getId());
        updatedMeterReading
            .readingDate(UPDATED_READING_DATE)
            .description(UPDATED_DESCRIPTION)
            .reading(UPDATED_READING)
            .tempVal(UPDATED_TEMP_VAL)
            .timestamp(UPDATED_TIMESTAMP)
            .userId(UPDATED_USER_ID);
        MeterReadingDTO meterReadingDTO = meterReadingMapper.meterReadingToMeterReadingDTO(updatedMeterReading);

        restMeterReadingMockMvc.perform(put("/api/meter-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterReadingDTO)))
            .andExpect(status().isOk());

        // Validate the MeterReading in the database
        List<MeterReading> meterReadingList = meterReadingRepository.findAll();
        assertThat(meterReadingList).hasSize(databaseSizeBeforeUpdate);
        MeterReading testMeterReading = meterReadingList.get(meterReadingList.size() - 1);
        assertThat(testMeterReading.getReadingDate()).isEqualTo(UPDATED_READING_DATE);
        assertThat(testMeterReading.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMeterReading.getReading()).isEqualTo(UPDATED_READING);
        assertThat(testMeterReading.getTempVal()).isEqualTo(UPDATED_TEMP_VAL);
        assertThat(testMeterReading.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testMeterReading.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the MeterReading in Elasticsearch
        MeterReading meterReadingEs = meterReadingSearchRepository.findOne(testMeterReading.getId());
        assertThat(meterReadingEs).isEqualToComparingFieldByField(testMeterReading);
    }

    @Test
    @Transactional
    public void updateNonExistingMeterReading() throws Exception {
        int databaseSizeBeforeUpdate = meterReadingRepository.findAll().size();

        // Create the MeterReading
        MeterReadingDTO meterReadingDTO = meterReadingMapper.meterReadingToMeterReadingDTO(meterReading);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeterReadingMockMvc.perform(put("/api/meter-readings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterReadingDTO)))
            .andExpect(status().isCreated());

        // Validate the MeterReading in the database
        List<MeterReading> meterReadingList = meterReadingRepository.findAll();
        assertThat(meterReadingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeterReading() throws Exception {
        // Initialize the database
        meterReadingRepository.saveAndFlush(meterReading);
        meterReadingSearchRepository.save(meterReading);
        int databaseSizeBeforeDelete = meterReadingRepository.findAll().size();

        // Get the meterReading
        restMeterReadingMockMvc.perform(delete("/api/meter-readings/{id}", meterReading.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean meterReadingExistsInEs = meterReadingSearchRepository.exists(meterReading.getId());
        assertThat(meterReadingExistsInEs).isFalse();

        // Validate the database is empty
        List<MeterReading> meterReadingList = meterReadingRepository.findAll();
        assertThat(meterReadingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMeterReading() throws Exception {
        // Initialize the database
        meterReadingRepository.saveAndFlush(meterReading);
        meterReadingSearchRepository.save(meterReading);

        // Search the meterReading
        restMeterReadingMockMvc.perform(get("/api/_search/meter-readings?query=id:" + meterReading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meterReading.getId().intValue())))
            .andExpect(jsonPath("$.[*].readingDate").value(hasItem(sameInstant(DEFAULT_READING_DATE))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].reading").value(hasItem(DEFAULT_READING.doubleValue())))
            .andExpect(jsonPath("$.[*].tempVal").value(hasItem(DEFAULT_TEMP_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeterReading.class);
    }
}
