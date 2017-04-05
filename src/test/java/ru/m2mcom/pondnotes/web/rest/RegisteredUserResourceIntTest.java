package ru.m2mcom.pondnotes.web.rest;

import ru.m2mcom.pondnotes.PondNotesApp;

import ru.m2mcom.pondnotes.domain.RegisteredUser;
import ru.m2mcom.pondnotes.repository.RegisteredUserRepository;
import ru.m2mcom.pondnotes.service.RegisteredUserService;
import ru.m2mcom.pondnotes.repository.search.RegisteredUserSearchRepository;
import ru.m2mcom.pondnotes.service.dto.RegisteredUserDTO;
import ru.m2mcom.pondnotes.service.mapper.RegisteredUserMapper;
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

/**
 * Test class for the RegisteredUserResource REST controller.
 *
 * @see RegisteredUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PondNotesApp.class)
public class RegisteredUserResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RegisteredUserRepository registeredUserRepository;

    @Autowired
    private RegisteredUserMapper registeredUserMapper;

    @Autowired
    private RegisteredUserService registeredUserService;

    @Autowired
    private RegisteredUserSearchRepository registeredUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegisteredUserMockMvc;

    private RegisteredUser registeredUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RegisteredUserResource registeredUserResource = new RegisteredUserResource(registeredUserService);
        this.restRegisteredUserMockMvc = MockMvcBuilders.standaloneSetup(registeredUserResource)
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
    public static RegisteredUser createEntity(EntityManager em) {
        RegisteredUser registeredUser = new RegisteredUser()
            .userName(DEFAULT_USER_NAME)
            .description(DEFAULT_DESCRIPTION);
        return registeredUser;
    }

    @Before
    public void initTest() {
        registeredUserSearchRepository.deleteAll();
        registeredUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegisteredUser() throws Exception {
        int databaseSizeBeforeCreate = registeredUserRepository.findAll().size();

        // Create the RegisteredUser
        RegisteredUserDTO registeredUserDTO = registeredUserMapper.registeredUserToRegisteredUserDTO(registeredUser);
        restRegisteredUserMockMvc.perform(post("/api/registered-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredUserDTO)))
            .andExpect(status().isCreated());

        // Validate the RegisteredUser in the database
        List<RegisteredUser> registeredUserList = registeredUserRepository.findAll();
        assertThat(registeredUserList).hasSize(databaseSizeBeforeCreate + 1);
        RegisteredUser testRegisteredUser = registeredUserList.get(registeredUserList.size() - 1);
        assertThat(testRegisteredUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testRegisteredUser.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the RegisteredUser in Elasticsearch
        RegisteredUser registeredUserEs = registeredUserSearchRepository.findOne(testRegisteredUser.getId());
        assertThat(registeredUserEs).isEqualToComparingFieldByField(testRegisteredUser);
    }

    @Test
    @Transactional
    public void createRegisteredUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registeredUserRepository.findAll().size();

        // Create the RegisteredUser with an existing ID
        registeredUser.setId(1L);
        RegisteredUserDTO registeredUserDTO = registeredUserMapper.registeredUserToRegisteredUserDTO(registeredUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegisteredUserMockMvc.perform(post("/api/registered-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RegisteredUser> registeredUserList = registeredUserRepository.findAll();
        assertThat(registeredUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRegisteredUsers() throws Exception {
        // Initialize the database
        registeredUserRepository.saveAndFlush(registeredUser);

        // Get all the registeredUserList
        restRegisteredUserMockMvc.perform(get("/api/registered-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registeredUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRegisteredUser() throws Exception {
        // Initialize the database
        registeredUserRepository.saveAndFlush(registeredUser);

        // Get the registeredUser
        restRegisteredUserMockMvc.perform(get("/api/registered-users/{id}", registeredUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registeredUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRegisteredUser() throws Exception {
        // Get the registeredUser
        restRegisteredUserMockMvc.perform(get("/api/registered-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegisteredUser() throws Exception {
        // Initialize the database
        registeredUserRepository.saveAndFlush(registeredUser);
        registeredUserSearchRepository.save(registeredUser);
        int databaseSizeBeforeUpdate = registeredUserRepository.findAll().size();

        // Update the registeredUser
        RegisteredUser updatedRegisteredUser = registeredUserRepository.findOne(registeredUser.getId());
        updatedRegisteredUser
            .userName(UPDATED_USER_NAME)
            .description(UPDATED_DESCRIPTION);
        RegisteredUserDTO registeredUserDTO = registeredUserMapper.registeredUserToRegisteredUserDTO(updatedRegisteredUser);

        restRegisteredUserMockMvc.perform(put("/api/registered-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredUserDTO)))
            .andExpect(status().isOk());

        // Validate the RegisteredUser in the database
        List<RegisteredUser> registeredUserList = registeredUserRepository.findAll();
        assertThat(registeredUserList).hasSize(databaseSizeBeforeUpdate);
        RegisteredUser testRegisteredUser = registeredUserList.get(registeredUserList.size() - 1);
        assertThat(testRegisteredUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testRegisteredUser.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the RegisteredUser in Elasticsearch
        RegisteredUser registeredUserEs = registeredUserSearchRepository.findOne(testRegisteredUser.getId());
        assertThat(registeredUserEs).isEqualToComparingFieldByField(testRegisteredUser);
    }

    @Test
    @Transactional
    public void updateNonExistingRegisteredUser() throws Exception {
        int databaseSizeBeforeUpdate = registeredUserRepository.findAll().size();

        // Create the RegisteredUser
        RegisteredUserDTO registeredUserDTO = registeredUserMapper.registeredUserToRegisteredUserDTO(registeredUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegisteredUserMockMvc.perform(put("/api/registered-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredUserDTO)))
            .andExpect(status().isCreated());

        // Validate the RegisteredUser in the database
        List<RegisteredUser> registeredUserList = registeredUserRepository.findAll();
        assertThat(registeredUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRegisteredUser() throws Exception {
        // Initialize the database
        registeredUserRepository.saveAndFlush(registeredUser);
        registeredUserSearchRepository.save(registeredUser);
        int databaseSizeBeforeDelete = registeredUserRepository.findAll().size();

        // Get the registeredUser
        restRegisteredUserMockMvc.perform(delete("/api/registered-users/{id}", registeredUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean registeredUserExistsInEs = registeredUserSearchRepository.exists(registeredUser.getId());
        assertThat(registeredUserExistsInEs).isFalse();

        // Validate the database is empty
        List<RegisteredUser> registeredUserList = registeredUserRepository.findAll();
        assertThat(registeredUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRegisteredUser() throws Exception {
        // Initialize the database
        registeredUserRepository.saveAndFlush(registeredUser);
        registeredUserSearchRepository.save(registeredUser);

        // Search the registeredUser
        restRegisteredUserMockMvc.perform(get("/api/_search/registered-users?query=id:" + registeredUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registeredUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegisteredUser.class);
    }
}
