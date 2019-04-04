package com.mycompany.web.rest;

import com.mycompany.ObraSocialApp;

import com.mycompany.domain.ModuleType;
import com.mycompany.domain.Module;
import com.mycompany.repository.ModuleTypeRepository;
import com.mycompany.service.ModuleTypeService;
import com.mycompany.web.rest.errors.ExceptionTranslator;
import com.mycompany.service.dto.ModuleTypeCriteria;
import com.mycompany.service.ModuleTypeQueryService;

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
 * Test class for the ModuleTypeResource REST controller.
 *
 * @see ModuleTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObraSocialApp.class)
public class ModuleTypeResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ModuleTypeRepository moduleTypeRepository;

    @Autowired
    private ModuleTypeService moduleTypeService;

    @Autowired
    private ModuleTypeQueryService moduleTypeQueryService;

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

    private MockMvc restModuleTypeMockMvc;

    private ModuleType moduleType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModuleTypeResource moduleTypeResource = new ModuleTypeResource(moduleTypeService, moduleTypeQueryService);
        this.restModuleTypeMockMvc = MockMvcBuilders.standaloneSetup(moduleTypeResource)
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
    public static ModuleType createEntity(EntityManager em) {
        ModuleType moduleType = new ModuleType()
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Module module = ModuleResourceIntTest.createEntity(em);
        em.persist(module);
        em.flush();
        moduleType.getModuleTypes().add(module);
        return moduleType;
    }

    @Before
    public void initTest() {
        moduleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createModuleType() throws Exception {
        int databaseSizeBeforeCreate = moduleTypeRepository.findAll().size();

        // Create the ModuleType
        restModuleTypeMockMvc.perform(post("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleType)))
            .andExpect(status().isCreated());

        // Validate the ModuleType in the database
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ModuleType testModuleType = moduleTypeList.get(moduleTypeList.size() - 1);
        assertThat(testModuleType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testModuleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createModuleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduleTypeRepository.findAll().size();

        // Create the ModuleType with an existing ID
        moduleType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleTypeMockMvc.perform(post("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleType)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleType in the database
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllModuleTypes() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get all the moduleTypeList
        restModuleTypeMockMvc.perform(get("/api/module-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getModuleType() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get the moduleType
        restModuleTypeMockMvc.perform(get("/api/module-types/{id}", moduleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(moduleType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllModuleTypesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get all the moduleTypeList where type equals to DEFAULT_TYPE
        defaultModuleTypeShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the moduleTypeList where type equals to UPDATED_TYPE
        defaultModuleTypeShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllModuleTypesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get all the moduleTypeList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultModuleTypeShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the moduleTypeList where type equals to UPDATED_TYPE
        defaultModuleTypeShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllModuleTypesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get all the moduleTypeList where type is not null
        defaultModuleTypeShouldBeFound("type.specified=true");

        // Get all the moduleTypeList where type is null
        defaultModuleTypeShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllModuleTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get all the moduleTypeList where description equals to DEFAULT_DESCRIPTION
        defaultModuleTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the moduleTypeList where description equals to UPDATED_DESCRIPTION
        defaultModuleTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllModuleTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get all the moduleTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultModuleTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the moduleTypeList where description equals to UPDATED_DESCRIPTION
        defaultModuleTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllModuleTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        moduleTypeRepository.saveAndFlush(moduleType);

        // Get all the moduleTypeList where description is not null
        defaultModuleTypeShouldBeFound("description.specified=true");

        // Get all the moduleTypeList where description is null
        defaultModuleTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllModuleTypesByModuleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        Module moduleType = ModuleResourceIntTest.createEntity(em);
        em.persist(moduleType);
        em.flush();
        moduleType.addModuleType(moduleType);
        moduleTypeRepository.saveAndFlush(moduleType);
        Long moduleTypeId = moduleType.getId();

        // Get all the moduleTypeList where moduleType equals to moduleTypeId
        defaultModuleTypeShouldBeFound("moduleTypeId.equals=" + moduleTypeId);

        // Get all the moduleTypeList where moduleType equals to moduleTypeId + 1
        defaultModuleTypeShouldNotBeFound("moduleTypeId.equals=" + (moduleTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultModuleTypeShouldBeFound(String filter) throws Exception {
        restModuleTypeMockMvc.perform(get("/api/module-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(moduleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restModuleTypeMockMvc.perform(get("/api/module-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultModuleTypeShouldNotBeFound(String filter) throws Exception {
        restModuleTypeMockMvc.perform(get("/api/module-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restModuleTypeMockMvc.perform(get("/api/module-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingModuleType() throws Exception {
        // Get the moduleType
        restModuleTypeMockMvc.perform(get("/api/module-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModuleType() throws Exception {
        // Initialize the database
        moduleTypeService.save(moduleType);

        int databaseSizeBeforeUpdate = moduleTypeRepository.findAll().size();

        // Update the moduleType
        ModuleType updatedModuleType = moduleTypeRepository.findById(moduleType.getId()).get();
        // Disconnect from session so that the updates on updatedModuleType are not directly saved in db
        em.detach(updatedModuleType);
        updatedModuleType
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION);

        restModuleTypeMockMvc.perform(put("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModuleType)))
            .andExpect(status().isOk());

        // Validate the ModuleType in the database
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeUpdate);
        ModuleType testModuleType = moduleTypeList.get(moduleTypeList.size() - 1);
        assertThat(testModuleType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testModuleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingModuleType() throws Exception {
        int databaseSizeBeforeUpdate = moduleTypeRepository.findAll().size();

        // Create the ModuleType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleTypeMockMvc.perform(put("/api/module-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(moduleType)))
            .andExpect(status().isBadRequest());

        // Validate the ModuleType in the database
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModuleType() throws Exception {
        // Initialize the database
        moduleTypeService.save(moduleType);

        int databaseSizeBeforeDelete = moduleTypeRepository.findAll().size();

        // Delete the moduleType
        restModuleTypeMockMvc.perform(delete("/api/module-types/{id}", moduleType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ModuleType> moduleTypeList = moduleTypeRepository.findAll();
        assertThat(moduleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModuleType.class);
        ModuleType moduleType1 = new ModuleType();
        moduleType1.setId(1L);
        ModuleType moduleType2 = new ModuleType();
        moduleType2.setId(moduleType1.getId());
        assertThat(moduleType1).isEqualTo(moduleType2);
        moduleType2.setId(2L);
        assertThat(moduleType1).isNotEqualTo(moduleType2);
        moduleType1.setId(null);
        assertThat(moduleType1).isNotEqualTo(moduleType2);
    }
}
