package com.mycompany.web.rest;

import com.mycompany.ObraSocialApp;

import com.mycompany.domain.ModuleType;
import com.mycompany.domain.Module;
import com.mycompany.repository.ModuleTypeRepository;
import com.mycompany.service.ModuleTypeService;
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
        final ModuleTypeResource moduleTypeResource = new ModuleTypeResource(moduleTypeService);
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
        moduleType.getTypes().add(module);
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
