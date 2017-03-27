package ru.m2mcom.pondnotes.web.rest;

import ru.m2mcom.pondnotes.PondNotesApp;

import ru.m2mcom.pondnotes.domain.Device;
import ru.m2mcom.pondnotes.repository.DeviceRepository;
import ru.m2mcom.pondnotes.service.DeviceService;
import ru.m2mcom.pondnotes.repository.search.DeviceSearchRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.m2mcom.pondnotes.domain.enumeration.DeviceType;
/**
 * Test class for the DeviceResource REST controller.
 *
 * @see DeviceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PondNotesApp.class)
public class DeviceResourceIntTest {

    private static final String DEFAULT_DEVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_NAME = "BBBBBBBBBB";

    private static final DeviceType DEFAULT_DEVICE_TYPE = DeviceType.PUMP;
    private static final DeviceType UPDATED_DEVICE_TYPE = DeviceType.FILTER;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIMESTAMP = 1;
    private static final Integer UPDATED_TIMESTAMP = 2;

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceSearchRepository deviceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeviceMockMvc;

    private Device device;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeviceResource deviceResource = new DeviceResource(deviceService);
        this.restDeviceMockMvc = MockMvcBuilders.standaloneSetup(deviceResource)
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
    public static Device createEntity(EntityManager em) {
        Device device = new Device()
            .deviceName(DEFAULT_DEVICE_NAME)
            .deviceType(DEFAULT_DEVICE_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .timestamp(DEFAULT_TIMESTAMP)
            .userId(DEFAULT_USER_ID);
        return device;
    }

    @Before
    public void initTest() {
        deviceSearchRepository.deleteAll();
        device = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevice() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(device)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate + 1);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getDeviceName()).isEqualTo(DEFAULT_DEVICE_NAME);
        assertThat(testDevice.getDeviceType()).isEqualTo(DEFAULT_DEVICE_TYPE);
        assertThat(testDevice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDevice.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testDevice.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the Device in Elasticsearch
        Device deviceEs = deviceSearchRepository.findOne(testDevice.getId());
        assertThat(deviceEs).isEqualToComparingFieldByField(testDevice);
    }

    @Test
    @Transactional
    public void createDeviceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceRepository.findAll().size();

        // Create the Device with an existing ID
        device.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(device)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDeviceNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceRepository.findAll().size();
        // set the field null
        device.setDeviceName(null);

        // Create the Device, which fails.

        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(device)))
            .andExpect(status().isBadRequest());

        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeviceTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceRepository.findAll().size();
        // set the field null
        device.setDeviceType(null);

        // Create the Device, which fails.

        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(device)))
            .andExpect(status().isBadRequest());

        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = deviceRepository.findAll().size();
        // set the field null
        device.setDescription(null);

        // Create the Device, which fails.

        restDeviceMockMvc.perform(post("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(device)))
            .andExpect(status().isBadRequest());

        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDevices() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get all the deviceList
        restDeviceMockMvc.perform(get("/api/devices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    public void getDevice() throws Exception {
        // Initialize the database
        deviceRepository.saveAndFlush(device);

        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(device.getId().intValue()))
            .andExpect(jsonPath("$.deviceName").value(DEFAULT_DEVICE_NAME.toString()))
            .andExpect(jsonPath("$.deviceType").value(DEFAULT_DEVICE_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID));
    }

    @Test
    @Transactional
    public void getNonExistingDevice() throws Exception {
        // Get the device
        restDeviceMockMvc.perform(get("/api/devices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevice() throws Exception {
        // Initialize the database
        deviceService.save(device);

        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Update the device
        Device updatedDevice = deviceRepository.findOne(device.getId());
        updatedDevice
            .deviceName(UPDATED_DEVICE_NAME)
            .deviceType(UPDATED_DEVICE_TYPE)
            .description(UPDATED_DESCRIPTION)
            .timestamp(UPDATED_TIMESTAMP)
            .userId(UPDATED_USER_ID);

        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDevice)))
            .andExpect(status().isOk());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate);
        Device testDevice = deviceList.get(deviceList.size() - 1);
        assertThat(testDevice.getDeviceName()).isEqualTo(UPDATED_DEVICE_NAME);
        assertThat(testDevice.getDeviceType()).isEqualTo(UPDATED_DEVICE_TYPE);
        assertThat(testDevice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDevice.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testDevice.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the Device in Elasticsearch
        Device deviceEs = deviceSearchRepository.findOne(testDevice.getId());
        assertThat(deviceEs).isEqualToComparingFieldByField(testDevice);
    }

    @Test
    @Transactional
    public void updateNonExistingDevice() throws Exception {
        int databaseSizeBeforeUpdate = deviceRepository.findAll().size();

        // Create the Device

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDeviceMockMvc.perform(put("/api/devices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(device)))
            .andExpect(status().isCreated());

        // Validate the Device in the database
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDevice() throws Exception {
        // Initialize the database
        deviceService.save(device);

        int databaseSizeBeforeDelete = deviceRepository.findAll().size();

        // Get the device
        restDeviceMockMvc.perform(delete("/api/devices/{id}", device.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean deviceExistsInEs = deviceSearchRepository.exists(device.getId());
        assertThat(deviceExistsInEs).isFalse();

        // Validate the database is empty
        List<Device> deviceList = deviceRepository.findAll();
        assertThat(deviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDevice() throws Exception {
        // Initialize the database
        deviceService.save(device);

        // Search the device
        restDeviceMockMvc.perform(get("/api/_search/devices?query=id:" + device.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(device.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceName").value(hasItem(DEFAULT_DEVICE_NAME.toString())))
            .andExpect(jsonPath("$.[*].deviceType").value(hasItem(DEFAULT_DEVICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Device.class);
    }
}
