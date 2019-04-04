package com.mycompany.web.rest;

import com.mycompany.ObraSocialApp;

import com.mycompany.domain.Column;
import com.mycompany.domain.Module;
import com.mycompany.repository.ColumnRepository;
import com.mycompany.service.ColumnService;
import com.mycompany.web.rest.errors.ExceptionTranslator;
import com.mycompany.service.dto.ColumnCriteria;
import com.mycompany.service.ColumnQueryService;

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
 * Test class for the ColumnResource REST controller.
 *
 * @see ColumnResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObraSocialApp.class)
public class ColumnResourceIntTest {

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
    private ColumnRepository columnRepository;

    @Autowired
    private ColumnService columnService;

    @Autowired
    private ColumnQueryService columnQueryService;

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

    private MockMvc restColumnMockMvc;

    private Column column;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColumnResource columnResource = new ColumnResource(columnService, columnQueryService);
        this.restColumnMockMvc = MockMvcBuilders.standaloneSetup(columnResource)
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
    public static Column createEntity(EntityManager em) {
        Column column = new Column()
            .tableName(DEFAULT_TABLE_NAME)
            .columnName(DEFAULT_COLUMN_NAME)
            .activeInd(DEFAULT_ACTIVE_IND)
            .dataType(DEFAULT_DATA_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .defaultValue(DEFAULT_DEFAULT_VALUE);
        return column;
    }

    @Before
    public void initTest() {
        column = createEntity(em);
    }

    @Test
    @Transactional
    public void createColumn() throws Exception {
        int databaseSizeBeforeCreate = columnRepository.findAll().size();

        // Create the Column
        restColumnMockMvc.perform(post("/api/columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(column)))
            .andExpect(status().isCreated());

        // Validate the Column in the database
        List<Column> columnList = columnRepository.findAll();
        assertThat(columnList).hasSize(databaseSizeBeforeCreate + 1);
        Column testColumn = columnList.get(columnList.size() - 1);
        assertThat(testColumn.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testColumn.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
        assertThat(testColumn.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testColumn.getDataType()).isEqualTo(DEFAULT_DATA_TYPE);
        assertThat(testColumn.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testColumn.getDefaultValue()).isEqualTo(DEFAULT_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createColumnWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = columnRepository.findAll().size();

        // Create the Column with an existing ID
        column.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColumnMockMvc.perform(post("/api/columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(column)))
            .andExpect(status().isBadRequest());

        // Validate the Column in the database
        List<Column> columnList = columnRepository.findAll();
        assertThat(columnList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllColumns() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList
        restColumnMockMvc.perform(get("/api/columns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(column.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME.toString())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getColumn() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get the column
        restColumnMockMvc.perform(get("/api/columns/{id}", column.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(column.getId().intValue()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME.toString()))
            .andExpect(jsonPath("$.columnName").value(DEFAULT_COLUMN_NAME.toString()))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.dataType").value(DEFAULT_DATA_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.defaultValue").value(DEFAULT_DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllColumnsByTableNameIsEqualToSomething() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where tableName equals to DEFAULT_TABLE_NAME
        defaultColumnShouldBeFound("tableName.equals=" + DEFAULT_TABLE_NAME);

        // Get all the columnList where tableName equals to UPDATED_TABLE_NAME
        defaultColumnShouldNotBeFound("tableName.equals=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnsByTableNameIsInShouldWork() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where tableName in DEFAULT_TABLE_NAME or UPDATED_TABLE_NAME
        defaultColumnShouldBeFound("tableName.in=" + DEFAULT_TABLE_NAME + "," + UPDATED_TABLE_NAME);

        // Get all the columnList where tableName equals to UPDATED_TABLE_NAME
        defaultColumnShouldNotBeFound("tableName.in=" + UPDATED_TABLE_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnsByTableNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where tableName is not null
        defaultColumnShouldBeFound("tableName.specified=true");

        // Get all the columnList where tableName is null
        defaultColumnShouldNotBeFound("tableName.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnsByColumnNameIsEqualToSomething() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where columnName equals to DEFAULT_COLUMN_NAME
        defaultColumnShouldBeFound("columnName.equals=" + DEFAULT_COLUMN_NAME);

        // Get all the columnList where columnName equals to UPDATED_COLUMN_NAME
        defaultColumnShouldNotBeFound("columnName.equals=" + UPDATED_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnsByColumnNameIsInShouldWork() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where columnName in DEFAULT_COLUMN_NAME or UPDATED_COLUMN_NAME
        defaultColumnShouldBeFound("columnName.in=" + DEFAULT_COLUMN_NAME + "," + UPDATED_COLUMN_NAME);

        // Get all the columnList where columnName equals to UPDATED_COLUMN_NAME
        defaultColumnShouldNotBeFound("columnName.in=" + UPDATED_COLUMN_NAME);
    }

    @Test
    @Transactional
    public void getAllColumnsByColumnNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where columnName is not null
        defaultColumnShouldBeFound("columnName.specified=true");

        // Get all the columnList where columnName is null
        defaultColumnShouldNotBeFound("columnName.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnsByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultColumnShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the columnList where activeInd equals to UPDATED_ACTIVE_IND
        defaultColumnShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllColumnsByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultColumnShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the columnList where activeInd equals to UPDATED_ACTIVE_IND
        defaultColumnShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllColumnsByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where activeInd is not null
        defaultColumnShouldBeFound("activeInd.specified=true");

        // Get all the columnList where activeInd is null
        defaultColumnShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnsByDataTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where dataType equals to DEFAULT_DATA_TYPE
        defaultColumnShouldBeFound("dataType.equals=" + DEFAULT_DATA_TYPE);

        // Get all the columnList where dataType equals to UPDATED_DATA_TYPE
        defaultColumnShouldNotBeFound("dataType.equals=" + UPDATED_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllColumnsByDataTypeIsInShouldWork() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where dataType in DEFAULT_DATA_TYPE or UPDATED_DATA_TYPE
        defaultColumnShouldBeFound("dataType.in=" + DEFAULT_DATA_TYPE + "," + UPDATED_DATA_TYPE);

        // Get all the columnList where dataType equals to UPDATED_DATA_TYPE
        defaultColumnShouldNotBeFound("dataType.in=" + UPDATED_DATA_TYPE);
    }

    @Test
    @Transactional
    public void getAllColumnsByDataTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where dataType is not null
        defaultColumnShouldBeFound("dataType.specified=true");

        // Get all the columnList where dataType is null
        defaultColumnShouldNotBeFound("dataType.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where description equals to DEFAULT_DESCRIPTION
        defaultColumnShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the columnList where description equals to UPDATED_DESCRIPTION
        defaultColumnShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllColumnsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultColumnShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the columnList where description equals to UPDATED_DESCRIPTION
        defaultColumnShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllColumnsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where description is not null
        defaultColumnShouldBeFound("description.specified=true");

        // Get all the columnList where description is null
        defaultColumnShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnsByDefaultValueIsEqualToSomething() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where defaultValue equals to DEFAULT_DEFAULT_VALUE
        defaultColumnShouldBeFound("defaultValue.equals=" + DEFAULT_DEFAULT_VALUE);

        // Get all the columnList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultColumnShouldNotBeFound("defaultValue.equals=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllColumnsByDefaultValueIsInShouldWork() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where defaultValue in DEFAULT_DEFAULT_VALUE or UPDATED_DEFAULT_VALUE
        defaultColumnShouldBeFound("defaultValue.in=" + DEFAULT_DEFAULT_VALUE + "," + UPDATED_DEFAULT_VALUE);

        // Get all the columnList where defaultValue equals to UPDATED_DEFAULT_VALUE
        defaultColumnShouldNotBeFound("defaultValue.in=" + UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllColumnsByDefaultValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        columnRepository.saveAndFlush(column);

        // Get all the columnList where defaultValue is not null
        defaultColumnShouldBeFound("defaultValue.specified=true");

        // Get all the columnList where defaultValue is null
        defaultColumnShouldNotBeFound("defaultValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllColumnsByModuleIsEqualToSomething() throws Exception {
        // Initialize the database
        Module module = ModuleResourceIntTest.createEntity(em);
        em.persist(module);
        em.flush();
        column.setModule(module);
        columnRepository.saveAndFlush(column);
        Long moduleId = module.getId();

        // Get all the columnList where module equals to moduleId
        defaultColumnShouldBeFound("moduleId.equals=" + moduleId);

        // Get all the columnList where module equals to moduleId + 1
        defaultColumnShouldNotBeFound("moduleId.equals=" + (moduleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultColumnShouldBeFound(String filter) throws Exception {
        restColumnMockMvc.perform(get("/api/columns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(column.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].dataType").value(hasItem(DEFAULT_DATA_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].defaultValue").value(hasItem(DEFAULT_DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restColumnMockMvc.perform(get("/api/columns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultColumnShouldNotBeFound(String filter) throws Exception {
        restColumnMockMvc.perform(get("/api/columns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restColumnMockMvc.perform(get("/api/columns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingColumn() throws Exception {
        // Get the column
        restColumnMockMvc.perform(get("/api/columns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColumn() throws Exception {
        // Initialize the database
        columnService.save(column);

        int databaseSizeBeforeUpdate = columnRepository.findAll().size();

        // Update the column
        Column updatedColumn = columnRepository.findById(column.getId()).get();
        // Disconnect from session so that the updates on updatedColumn are not directly saved in db
        em.detach(updatedColumn);
        updatedColumn
            .tableName(UPDATED_TABLE_NAME)
            .columnName(UPDATED_COLUMN_NAME)
            .activeInd(UPDATED_ACTIVE_IND)
            .dataType(UPDATED_DATA_TYPE)
            .description(UPDATED_DESCRIPTION)
            .defaultValue(UPDATED_DEFAULT_VALUE);

        restColumnMockMvc.perform(put("/api/columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedColumn)))
            .andExpect(status().isOk());

        // Validate the Column in the database
        List<Column> columnList = columnRepository.findAll();
        assertThat(columnList).hasSize(databaseSizeBeforeUpdate);
        Column testColumn = columnList.get(columnList.size() - 1);
        assertThat(testColumn.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testColumn.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testColumn.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testColumn.getDataType()).isEqualTo(UPDATED_DATA_TYPE);
        assertThat(testColumn.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testColumn.getDefaultValue()).isEqualTo(UPDATED_DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingColumn() throws Exception {
        int databaseSizeBeforeUpdate = columnRepository.findAll().size();

        // Create the Column

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColumnMockMvc.perform(put("/api/columns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(column)))
            .andExpect(status().isBadRequest());

        // Validate the Column in the database
        List<Column> columnList = columnRepository.findAll();
        assertThat(columnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteColumn() throws Exception {
        // Initialize the database
        columnService.save(column);

        int databaseSizeBeforeDelete = columnRepository.findAll().size();

        // Delete the column
        restColumnMockMvc.perform(delete("/api/columns/{id}", column.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Column> columnList = columnRepository.findAll();
        assertThat(columnList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Column.class);
        Column column1 = new Column();
        column1.setId(1L);
        Column column2 = new Column();
        column2.setId(column1.getId());
        assertThat(column1).isEqualTo(column2);
        column2.setId(2L);
        assertThat(column1).isNotEqualTo(column2);
        column1.setId(null);
        assertThat(column1).isNotEqualTo(column2);
    }
}
