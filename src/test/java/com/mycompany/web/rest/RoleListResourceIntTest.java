package com.mycompany.web.rest;

import com.mycompany.ObraSocialApp;

import com.mycompany.domain.RoleList;
import com.mycompany.domain.User;
import com.mycompany.domain.RoleType;
import com.mycompany.repository.RoleListRepository;
import com.mycompany.service.RoleListService;
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
 * Test class for the RoleListResource REST controller.
 *
 * @see RoleListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObraSocialApp.class)
public class RoleListResourceIntTest {

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCE = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCE = "BBBBBBBBBB";

    @Autowired
    private RoleListRepository roleListRepository;

    @Autowired
    private RoleListService roleListService;

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

    private MockMvc restRoleListMockMvc;

    private RoleList roleList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoleListResource roleListResource = new RoleListResource(roleListService);
        this.restRoleListMockMvc = MockMvcBuilders.standaloneSetup(roleListResource)
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
    public static RoleList createEntity(EntityManager em) {
        RoleList roleList = new RoleList()
            .role(DEFAULT_ROLE)
            .description(DEFAULT_DESCRIPTION)
            .resource(DEFAULT_RESOURCE);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        roleList.setUser(user);
        // Add required entity
        RoleType roleType = RoleTypeResourceIntTest.createEntity(em);
        em.persist(roleType);
        em.flush();
        roleList.setRoleType(roleType);
        return roleList;
    }

    @Before
    public void initTest() {
        roleList = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoleList() throws Exception {
        int databaseSizeBeforeCreate = roleListRepository.findAll().size();

        // Create the RoleList
        restRoleListMockMvc.perform(post("/api/role-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleList)))
            .andExpect(status().isCreated());

        // Validate the RoleList in the database
        List<RoleList> roleListList = roleListRepository.findAll();
        assertThat(roleListList).hasSize(databaseSizeBeforeCreate + 1);
        RoleList testRoleList = roleListList.get(roleListList.size() - 1);
        assertThat(testRoleList.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testRoleList.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRoleList.getResource()).isEqualTo(DEFAULT_RESOURCE);
    }

    @Test
    @Transactional
    public void createRoleListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roleListRepository.findAll().size();

        // Create the RoleList with an existing ID
        roleList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleListMockMvc.perform(post("/api/role-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleList)))
            .andExpect(status().isBadRequest());

        // Validate the RoleList in the database
        List<RoleList> roleListList = roleListRepository.findAll();
        assertThat(roleListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleListRepository.findAll().size();
        // set the field null
        roleList.setRole(null);

        // Create the RoleList, which fails.

        restRoleListMockMvc.perform(post("/api/role-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleList)))
            .andExpect(status().isBadRequest());

        List<RoleList> roleListList = roleListRepository.findAll();
        assertThat(roleListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleListRepository.findAll().size();
        // set the field null
        roleList.setDescription(null);

        // Create the RoleList, which fails.

        restRoleListMockMvc.perform(post("/api/role-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleList)))
            .andExpect(status().isBadRequest());

        List<RoleList> roleListList = roleListRepository.findAll();
        assertThat(roleListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleListRepository.findAll().size();
        // set the field null
        roleList.setResource(null);

        // Create the RoleList, which fails.

        restRoleListMockMvc.perform(post("/api/role-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleList)))
            .andExpect(status().isBadRequest());

        List<RoleList> roleListList = roleListRepository.findAll();
        assertThat(roleListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoleLists() throws Exception {
        // Initialize the database
        roleListRepository.saveAndFlush(roleList);

        // Get all the roleListList
        restRoleListMockMvc.perform(get("/api/role-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleList.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].resource").value(hasItem(DEFAULT_RESOURCE.toString())));
    }
    
    @Test
    @Transactional
    public void getRoleList() throws Exception {
        // Initialize the database
        roleListRepository.saveAndFlush(roleList);

        // Get the roleList
        restRoleListMockMvc.perform(get("/api/role-lists/{id}", roleList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roleList.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.resource").value(DEFAULT_RESOURCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoleList() throws Exception {
        // Get the roleList
        restRoleListMockMvc.perform(get("/api/role-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoleList() throws Exception {
        // Initialize the database
        roleListService.save(roleList);

        int databaseSizeBeforeUpdate = roleListRepository.findAll().size();

        // Update the roleList
        RoleList updatedRoleList = roleListRepository.findById(roleList.getId()).get();
        // Disconnect from session so that the updates on updatedRoleList are not directly saved in db
        em.detach(updatedRoleList);
        updatedRoleList
            .role(UPDATED_ROLE)
            .description(UPDATED_DESCRIPTION)
            .resource(UPDATED_RESOURCE);

        restRoleListMockMvc.perform(put("/api/role-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoleList)))
            .andExpect(status().isOk());

        // Validate the RoleList in the database
        List<RoleList> roleListList = roleListRepository.findAll();
        assertThat(roleListList).hasSize(databaseSizeBeforeUpdate);
        RoleList testRoleList = roleListList.get(roleListList.size() - 1);
        assertThat(testRoleList.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testRoleList.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRoleList.getResource()).isEqualTo(UPDATED_RESOURCE);
    }

    @Test
    @Transactional
    public void updateNonExistingRoleList() throws Exception {
        int databaseSizeBeforeUpdate = roleListRepository.findAll().size();

        // Create the RoleList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleListMockMvc.perform(put("/api/role-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roleList)))
            .andExpect(status().isBadRequest());

        // Validate the RoleList in the database
        List<RoleList> roleListList = roleListRepository.findAll();
        assertThat(roleListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRoleList() throws Exception {
        // Initialize the database
        roleListService.save(roleList);

        int databaseSizeBeforeDelete = roleListRepository.findAll().size();

        // Delete the roleList
        restRoleListMockMvc.perform(delete("/api/role-lists/{id}", roleList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoleList> roleListList = roleListRepository.findAll();
        assertThat(roleListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleList.class);
        RoleList roleList1 = new RoleList();
        roleList1.setId(1L);
        RoleList roleList2 = new RoleList();
        roleList2.setId(roleList1.getId());
        assertThat(roleList1).isEqualTo(roleList2);
        roleList2.setId(2L);
        assertThat(roleList1).isNotEqualTo(roleList2);
        roleList1.setId(null);
        assertThat(roleList1).isNotEqualTo(roleList2);
    }
}
