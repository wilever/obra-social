package com.mycompany.web.rest;

import com.mycompany.ObraSocialApp;

import com.mycompany.domain.ColumnModule;
import com.mycompany.domain.Module;
import com.mycompany.repository.ColumnModuleRepository;
import com.mycompany.service.ColumnModuleService;
import com.mycompany.web.rest.errors.ExceptionTranslator;
import com.mycompany.service.dto.ColumnModuleCriteria;
import com.mycompany.service.ColumnModuleQueryService;

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
 * Test class for the ColumnModuleResource REST controller.
 *
 * @see ColumnModuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObraSocialApp.class)
public class ColumnModuleResourceIntTest {

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final String DEFAULT_DATA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_VALUE = "BBBBBBBBBB";

    @Autowired
    private ColumnModuleRepository columnModuleRepository;

    @Autowired
    private ColumnModuleService columnModuleService;

    @Autowired
    private ColumnModuleQueryService columnModuleQueryService;

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

    private MockMvc restColumnModuleMockMvc;

    private ColumnModule columnModule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColumnModuleResource columnModuleResource = new ColumnModuleResource(columnModuleService, columnModuleQueryService);
        this.restColumnModuleMockMvc = MockMvcBuilders.standaloneSetup(columnModuleResource)
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
    public static ColumnModule createEntity(EntityManager em) {
        ColumnModule columnModule = new ColumnModule()
            .tableName(DEFAULT_TABLE_NAME)
            .columnName(DEFAULT_COLUMN_NAME)
            .activeInd(DEFAULT_ACTIVE_IND)
            .dataType(DEFAULT_DATA_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .defaultValue(DEFAULT_DEFAULT_VALUE);
        return columnModule;
    }

    @Before
    public void initTest() {
        columnModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createColumnModule() throws Exception {
        int databaseSizeBeforeCreate = columnModuleRepository.findAll().size();

        // Create the ColumnModule
        restColumnModuleMockMvc.perform(post("/api/column-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnModule)))
            .andExpect(status().isCreated());

        // Validate the ColumnModule in the database
        List<ColumnModule> columnModuleList = columnModuleRepository.findAll();
        assertThat(columnModuleList).hasSize(databaseSizeBeforeCreate + 1);
        ColumnModule testColumnModule = columnModuleList.get(columnModuleList.size() - 1);
        assertThat(testColumnModule.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testColumnModule.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
        assertThat(testColumnModule.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testColumnModule.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testColumnModule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testColumnModule.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createColumnModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = columnModuleRepository.findAll().size();

        // Create the ColumnModule with an existing ID
        columnModule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColumnModuleMockMvc.perform(post("/api/column-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnModule)))
            .andExpect(status().isBadRequest());

        // Validate the ColumnModule in the database
        List<ColumnModule> columnModuleList = columnModuleRepository.findAll();
        assertThat(columnModuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllColumnModules() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList
        restColumnModuleMockMvc.perform(get("/api/column-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(columnModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME.toString())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getColumnModule() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get the columnModule
        restColumnModuleMockMvc.perform(get("/api/column-modules/{id}", columnModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(columnModule.getId().intValue()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME.toString()))
            .andExpect(jsonPath("$.columnName").value(DEFAULT_COLUMN_NAME.toString()))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllColumnModulesByTableNameIsEqualToSomething() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where tableName equals to DEFAULT_TABLE_NAME
        defaultColumnModuleShouldBeFound("tableName.equals=" + DEFAULT_TABLE_NAME);

        // Get all the columnModuleList where tableName equals to UPDATED_TABLE_NAME
        defaultColumnModuleShouldNotBeFound("tableName.equals=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByTableNameIsInShouldWork() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where tableName in DEFAULT_TABLE_NAME or UPDATED_TABLE_NAME
        defaultColumnModuleShouldBeFound("tableName.in=" + DEFAULT_TABLE_NAME + "," + UPDATED_TABLE_NAME);

        // Get all the columnModuleList where tableName equals to UPDATED_TABLE_NAME
        defaultColumnModuleShouldNotBeFound("tableName.in=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByTableNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where tableName is not null
        defaultColumnModuleShouldBeFound("tableName.specified=true");

        // Get all the columnModuleList where tableName is null
        defaultColumnModuleShouldNotBeFound("tableName.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnModulesByColumnNameIsEqualToSomething() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where columnName equals to DEFAULT_COLUMN_NAME
        defaultColumnModuleShouldBeFound("columnName.equals=" + DEFAULT_COLUMN_NAME);

        // Get all the columnModuleList where columnName equals to UPDATED_COLUMN_NAME
        defaultColumnModuleShouldNotBeFound("columnName.equals=" + UPDATED_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByColumnNameIsInShouldWork() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where columnName in DEFAULT_COLUMN_NAME or UPDATED_COLUMN_NAME
        defaultColumnModuleShouldBeFound("columnName.in=" + DEFAULT_COLUMN_NAME + "," + UPDATED_COLUMN_NAME);

        // Get all the columnModuleList where columnName equals to UPDATED_COLUMN_NAME
        defaultColumnModuleShouldNotBeFound("columnName.in=" + UPDATED_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByColumnNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where columnName is not null
        defaultColumnModuleShouldBeFound("columnName.specified=true");

        // Get all the columnModuleList where columnName is null
        defaultColumnModuleShouldNotBeFound("columnName.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnModulesByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultColumnModuleShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the columnModuleList where activeInd equals to UPDATED_ACTIVE_IND
        defaultColumnModuleShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultColumnModuleShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the columnModuleList where activeInd equals to UPDATED_ACTIVE_IND
        defaultColumnModuleShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where activeInd is not null
        defaultColumnModuleShouldBeFound("activeInd.specified=true");

        // Get all the columnModuleList where activeInd is null
        defaultColumnModuleShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnModulesByDataTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where dataType equals to DEFAULT_DATA_TYPE
        defaultColumnModuleShouldBeFound("dataType.equals=" + DEFAULT_DATA_TYPE);

        // Get all the columnModuleList where dataType equals to UPDATED_DATA_TYPE
        defaultColumnModuleShouldNotBeFound("dataType.equals=" + UPDATED_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByDataTypeIsInShouldWork() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where dataType in DEFAULT_DATA_TYPE or UPDATED_DATA_TYPE
        defaultColumnModuleShouldBeFound("dataType.in=" + DEFAULT_DATA_TYPE + "," + UPDATED_DATA_TYPE);

        // Get all the columnModuleList where dataType equals to UPDATED_DATA_TYPE
        defaultColumnModuleShouldNotBeFound("dataType.in=" + UPDATED_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByDataTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where dataType is not null
        defaultColumnModuleShouldBeFound("dataType.specified=true");

        // Get all the columnModuleList where dataType is null
        defaultColumnModuleShouldNotBeFound("dataType.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnModulesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where description equals to DEFAULT_DESCRIPTION
        defaultColumnModuleShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the columnModuleList where description equals to UPDATED_DESCRIPTION
        defaultColumnModuleShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultColumnModuleShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the columnModuleList where description equals to UPDATED_DESCRIPTION
        defaultColumnModuleShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where description is not null
        defaultColumnModuleShouldBeFound("description.specified=true");

        // Get all the columnModuleList where description is null
        defaultColumnModuleShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnModulesByDefaultValueIsEqualToSomething() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where defaultValue equals to DEFAULT_DEFAULT_VALUE
        defaultColumnModuleShouldBeFound("defaultValue.equals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the columnModuleList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultColumnModuleShouldNotBeFound("defaultValue.equals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByDefaultValueIsInShouldWork() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where defaultValue in DEFAULT_DEFAULT_VALUE or UPDATED_DEFAULT_VALUE
        defaultColumnModuleShouldBeFound("defaultValue.in=" + DEFAULT_DEFAULT_VALUE + "," + UPDATED_DEFAULT_VALUE);

        // Get all the columnModuleList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultColumnModuleShouldNotBeFound("defaultValue.in=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllColumnModulesByDefaultValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnModuleRepository.saveAndFlush(columnModule);

        // Get all the columnModuleList where defaultValue is not null
        defaultColumnModuleShouldBeFound("defaultValue.specified=true");

        // Get all the columnModuleList where defaultValue is null
        defaultColumnModuleShouldNotBeFound("defaultValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnModulesByModuleIsEqualToSomething() throws Exception {
        // Initialize the database
        Module module = ModuleResourceIntTest.createEntity(em);
        em.persist(module);
        em.flush();
        columnModule.setModule(module);
        columnModuleRepository.saveAndFlush(columnModule);
        Long moduleId = module.getId();

        // Get all the columnModuleList where module equals to moduleId
        defaultColumnModuleShouldBeFound("moduleId.equals=" + moduleId);

        // Get all the columnModuleList where module equals to moduleId + 1
        defaultColumnModuleShouldNotBeFound("moduleId.equals=" + (moduleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultColumnModuleShouldBeFound(String filter) throws Exception {
        restColumnModuleMockMvc.perform(get("/api/column-modules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(columnModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restColumnModuleMockMvc.perform(get("/api/column-modules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultColumnModuleShouldNotBeFound(String filter) throws Exception {
        restColumnModuleMockMvc.perform(get("/api/column-modules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restColumnModuleMockMvc.perform(get("/api/column-modules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingColumnModule() throws Exception {
        // Get the columnModule
        restColumnModuleMockMvc.perform(get("/api/column-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColumnModule() throws Exception {
        // Initialize the database
        columnModuleService.save(columnModule);

        int databaseSizeBeforeUpdate = columnModuleRepository.findAll().size();

        // Update the columnModule
        ColumnModule updatedColumnModule = columnModuleRepository.findById(columnModule.getId()).get();
        // Disconnect from session so that the updates on updatedColumnModule are not directly saved in db
        em.detach(updatedColumnModule);
        updatedColumnModule
            .tableName(UPDATED_TABLE_NAME)
            .columnName(UPDATED_COLUMN_NAME)
            .activeInd(UPDATED_ACTIVE_IND)
            .dataType(UPDATED_DATA_TYPE)
            .description(UPDATED_DESCRIPTION)
            .defaultValue(UPDATED_DEFAULT_VALUE);

        restColumnModuleMockMvc.perform(put("/api/column-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedColumnModule)))
            .andExpect(status().isOk());

        // Validate the ColumnModule in the database
        List<ColumnModule> columnModuleList = columnModuleRepository.findAll();
        assertThat(columnModuleList).hasSize(databaseSizeBeforeUpdate);
        ColumnModule testColumnModule = columnModuleList.get(columnModuleList.size() - 1);
        assertThat(testColumnModule.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testColumnModule.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testColumnModule.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testColumnModule.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testColumnModule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testColumnModule.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingColumnModule() throws Exception {
        int databaseSizeBeforeUpdate = columnModuleRepository.findAll().size();

        // Create the ColumnModule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColumnModuleMockMvc.perform(put("/api/column-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(columnModule)))
            .andExpect(status().isBadRequest());

        // Validate the ColumnModule in the database
        List<ColumnModule> columnModuleList = columnModuleRepository.findAll();
        assertThat(columnModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColumnModule() throws Exception {
        // Initialize the database
        columnModuleService.save(columnModule);

        int databaseSizeBeforeDelete = columnModuleRepository.findAll().size();

        // Delete the columnModule
        restColumnModuleMockMvc.perform(delete("/api/column-modules/{id}", columnModule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ColumnModule> columnModuleList = columnModuleRepository.findAll();
        assertThat(columnModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ColumnModule.class);
        ColumnModule columnModule1 = new ColumnModule();
        columnModule1.setId(1L);
        ColumnModule columnModule2 = new ColumnModule();
        columnModule2.setId(columnModule1.getId());
        assertThat(columnModule1).isEqualTo(columnModule2);
        columnModule2.setId(2L);
        assertThat(columnModule1).isNotEqualTo(columnModule2);
        columnModule1.setId(null);
        assertThat(columnModule1).isNotEqualTo(columnModule2);
    }
}
