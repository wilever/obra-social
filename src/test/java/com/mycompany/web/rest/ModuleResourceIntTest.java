package com.mycompany.web.rest;

import com.mycompany.ObraSocialApp;

import com.mycompany.domain.Module;
import com.mycompany.domain.Module;
import com.mycompany.domain.ColumnModule;
import com.mycompany.domain.ModuleType;
import com.mycompany.domain.Tag;
import com.mycompany.domain.Company;
import com.mycompany.repository.ModuleRepository;
import com.mycompany.service.ModuleService;
import com.mycompany.web.rest.errors.ExceptionTranslator;
import com.mycompany.service.dto.ModuleCriteria;
import com.mycompany.service.ModuleQueryService;

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
 * Test class for the ModuleResource REST controller.
 *
 * @see ModuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObraSocialApp.class)
public class ModuleResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleQueryService moduleQueryService;

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

    private MockMvc restModuleMockMvc;

    private Module module;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModuleResource moduleResource = new ModuleResource(moduleService, moduleQueryService);
        this.restModuleMockMvc = MockMvcBuilders.standaloneSetup(moduleResource)
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
    public static Module createEntity(EntityManager em) {
        Module module = new Module()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return module;
    }

    @Before
    public void initTest() {
        module = createEntity(em);
    }

    @Test
    @Transactional
    public void createModule() throws Exception {
        int databaseSizeBeforeCreate = moduleRepository.findAll().size();

        // Create the Module
        restModuleMockMvc.perform(post("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(module)))
            .andExpect(status().isCreated());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeCreate + 1);
        Module testModule = moduleList.get(moduleList.size() - 1);
        assertThat(testModule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduleRepository.findAll().size();

        // Create the Module with an existing ID
        module.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleMockMvc.perform(post("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(module)))
            .andExpect(status().isBadRequest());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = moduleRepository.findAll().size();
        // set the field null
        module.setName(null);

        // Create the Module, which fails.

        restModuleMockMvc.perform(post("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(module)))
            .andExpect(status().isBadRequest());

        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllModules() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the moduleList
        restModuleMockMvc.perform(get("/api/modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(module.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", module.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(module.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllModulesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the moduleList where name equals to DEFAULT_NAME
        defaultModuleShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the moduleList where name equals to UPDATED_NAME
        defaultModuleShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModulesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the moduleList where name in DEFAULT_NAME or UPDATED_NAME
        defaultModuleShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the moduleList where name equals to UPDATED_NAME
        defaultModuleShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllModulesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the moduleList where name is not null
        defaultModuleShouldBeFound("name.specified=true");

        // Get all the moduleList where name is null
        defaultModuleShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllModulesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the moduleList where description equals to DEFAULT_DESCRIPTION
        defaultModuleShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the moduleList where description equals to UPDATED_DESCRIPTION
        defaultModuleShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllModulesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the moduleList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultModuleShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the moduleList where description equals to UPDATED_DESCRIPTION
        defaultModuleShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllModulesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the moduleList where description is not null
        defaultModuleShouldBeFound("description.specified=true");

        // Get all the moduleList where description is null
        defaultModuleShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllModulesByModuleIsEqualToSomething() throws Exception {
        // Initialize the database
        Module module = ModuleResourceIntTest.createEntity(em);
        em.persist(module);
        em.flush();
        module.setModule(module);
        moduleRepository.saveAndFlush(module);
        Long moduleId = module.getId();

        // Get all the moduleList where module equals to moduleId
        defaultModuleShouldBeFound("moduleId.equals=" + moduleId);

        // Get all the moduleList where module equals to moduleId + 1
        defaultModuleShouldNotBeFound("moduleId.equals=" + (moduleId + 1));
    }


    @Test
    @Transactional
    public void getAllModulesByModuleIsEqualToSomething() throws Exception {
        // Initialize the database
        Module module = ModuleResourceIntTest.createEntity(em);
        em.persist(module);
        em.flush();
        module.addModule(module);
        moduleRepository.saveAndFlush(module);
        Long moduleId = module.getId();

        // Get all the moduleList where module equals to moduleId
        defaultModuleShouldBeFound("moduleId.equals=" + moduleId);

        // Get all the moduleList where module equals to moduleId + 1
        defaultModuleShouldNotBeFound("moduleId.equals=" + (moduleId + 1));
    }


    @Test
    @Transactional
    public void getAllModulesByModuleIsEqualToSomething() throws Exception {
        // Initialize the database
        ColumnModule module = ColumnModuleResourceIntTest.createEntity(em);
        em.persist(module);
        em.flush();
        module.addModule(module);
        moduleRepository.saveAndFlush(module);
        Long moduleId = module.getId();

        // Get all the moduleList where module equals to moduleId
        defaultModuleShouldBeFound("moduleId.equals=" + moduleId);

        // Get all the moduleList where module equals to moduleId + 1
        defaultModuleShouldNotBeFound("moduleId.equals=" + (moduleId + 1));
    }


    @Test
    @Transactional
    public void getAllModulesByModuleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ModuleType moduleType = ModuleTypeResourceIntTest.createEntity(em);
        em.persist(moduleType);
        em.flush();
        module.setModuleType(moduleType);
        moduleRepository.saveAndFlush(module);
        Long moduleTypeId = moduleType.getId();

        // Get all the moduleList where moduleType equals to moduleTypeId
        defaultModuleShouldBeFound("moduleTypeId.equals=" + moduleTypeId);

        // Get all the moduleList where moduleType equals to moduleTypeId + 1
        defaultModuleShouldNotBeFound("moduleTypeId.equals=" + (moduleTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllModulesByTagIsEqualToSomething() throws Exception {
        // Initialize the database
        Tag tag = TagResourceIntTest.createEntity(em);
        em.persist(tag);
        em.flush();
        module.setTag(tag);
        moduleRepository.saveAndFlush(module);
        Long tagId = tag.getId();

        // Get all the moduleList where tag equals to tagId
        defaultModuleShouldBeFound("tagId.equals=" + tagId);

        // Get all the moduleList where tag equals to tagId + 1
        defaultModuleShouldNotBeFound("tagId.equals=" + (tagId + 1));
    }


    @Test
    @Transactional
    public void getAllModulesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        module.setCompany(company);
        moduleRepository.saveAndFlush(module);
        Long companyId = company.getId();

        // Get all the moduleList where company equals to companyId
        defaultModuleShouldBeFound("companyId.equals=" + companyId);

        // Get all the moduleList where company equals to companyId + 1
        defaultModuleShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultModuleShouldBeFound(String filter) throws Exception {
        restModuleMockMvc.perform(get("/api/modules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(module.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restModuleMockMvc.perform(get("/api/modules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultModuleShouldNotBeFound(String filter) throws Exception {
        restModuleMockMvc.perform(get("/api/modules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restModuleMockMvc.perform(get("/api/modules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingModule() throws Exception {
        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModule() throws Exception {
        // Initialize the database
        moduleService.save(module);

        int databaseSizeBeforeUpdate = moduleRepository.findAll().size();

        // Update the module
        Module updatedModule = moduleRepository.findById(module.getId()).get();
        // Disconnect from session so that the updates on updatedModule are not directly saved in db
        em.detach(updatedModule);
        updatedModule
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restModuleMockMvc.perform(put("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModule)))
            .andExpect(status().isOk());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeUpdate);
        Module testModule = moduleList.get(moduleList.size() - 1);
        assertThat(testModule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingModule() throws Exception {
        int databaseSizeBeforeUpdate = moduleRepository.findAll().size();

        // Create the Module

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleMockMvc.perform(put("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(module)))
            .andExpect(status().isBadRequest());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModule() throws Exception {
        // Initialize the database
        moduleService.save(module);

        int databaseSizeBeforeDelete = moduleRepository.findAll().size();

        // Delete the module
        restModuleMockMvc.perform(delete("/api/modules/{id}", module.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Module.class);
        Module module1 = new Module();
        module1.setId(1L);
        Module module2 = new Module();
        module2.setId(module1.getId());
        assertThat(module1).isEqualTo(module2);
        module2.setId(2L);
        assertThat(module1).isNotEqualTo(module2);
        module1.setId(null);
        assertThat(module1).isNotEqualTo(module2);
    }
}
