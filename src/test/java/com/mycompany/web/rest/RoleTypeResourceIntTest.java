package com.mycompany.web.rest;

import com.mycompany.ObraSocialApp;

import com.mycompany.domain.RoleType;
import com.mycompany.repository.RoleTypeRepository;
import com.mycompany.service.RoleTypeService;
import com.mycompany.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RoleTypeResource REST controller.
 *
 * @see RoleTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObraSocialApp.class)
public class RoleTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private RoleTypeRepository roleTypeRepository;

    @Autowired
    private RoleTypeService roleTypeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRoleTypeMockMvc;

    private RoleType roleType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleTypeResource roleTypeResource = new RoleTypeResource(roleTypeService);
        this.restRoleTypeMockMvc = MockMvcBuilders.standaloneSetup(roleTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleType createEntity(EntityManager em) {
        RoleType roleType = new RoleType()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return roleType;
    }

    @Before
    public void initTest() {
        roleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoleType() throws Exception {
        int databaseSizeBeforeCreate = roleTypeRepository.findAll().size();

        // Create the RoleType
        restRoleTypeMockMvc.perform(post("/api/role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isCreated());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RoleType testRoleType = roleTypeList.get(roleTypeList.size() - 1);
        assertThat(testRoleType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRoleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRoleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleTypeRepository.findAll().size();

        // Create the RoleType with an existing ID
        roleType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleTypeMockMvc.perform(post("/api/role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isBadRequest());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleTypeRepository.findAll().size();
        // set the field null
        roleType.setType(null);

        // Create the RoleType, which fails.

        restRoleTypeMockMvc.perform(post("/api/role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isBadRequest());

        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleTypeRepository.findAll().size();
        // set the field null
        roleType.setDescription(null);

        // Create the RoleType, which fails.

        restRoleTypeMockMvc.perform(post("/api/role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isBadRequest());

        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoleTypes() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        // Get all the roleTypeList
        restRoleTypeMockMvc.perform(get("/api/role-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getRoleType() throws Exception {
        // Initialize the database
        roleTypeRepository.saveAndFlush(roleType);

        // Get the roleType
        restRoleTypeMockMvc.perform(get("/api/role-types/{id}", roleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roleType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoleType() throws Exception {
        // Get the roleType
        restRoleTypeMockMvc.perform(get("/api/role-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoleType() throws Exception {
        // Initialize the database
        roleTypeService.save(roleType);

        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();

        // Update the roleType
        RoleType updatedRoleType = roleTypeRepository.findById(roleType.getId()).get();
        // Disconnect from session so that the updates on updatedRoleType are not directly saved in db
        em.detach(updatedRoleType);
        updatedRoleType
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION);

        restRoleTypeMockMvc.perform(put("/api/role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoleType)))
            .andExpect(status().isOk());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
        RoleType testRoleType = roleTypeList.get(roleTypeList.size() - 1);
        assertThat(testRoleType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRoleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingRoleType() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRepository.findAll().size();

        // Create the RoleType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeMockMvc.perform(put("/api/role-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleType)))
            .andExpect(status().isBadRequest());

        // Validate the RoleType in the database
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoleType() throws Exception {
        // Initialize the database
        roleTypeService.save(roleType);

        int databaseSizeBeforeDelete = roleTypeRepository.findAll().size();

        // Delete the roleType
        restRoleTypeMockMvc.perform(delete("/api/role-types/{id}", roleType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoleType> roleTypeList = roleTypeRepository.findAll();
        assertThat(roleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleType.class);
        RoleType roleType1 = new RoleType();
        roleType1.setId(1L);
        RoleType roleType2 = new RoleType();
        roleType2.setId(roleType1.getId());
        assertThat(roleType1).isEqualTo(roleType2);
        roleType2.setId(2L);
        assertThat(roleType1).isNotEqualTo(roleType2);
        roleType1.setId(null);
        assertThat(roleType1).isNotEqualTo(roleType2);
    }
}
