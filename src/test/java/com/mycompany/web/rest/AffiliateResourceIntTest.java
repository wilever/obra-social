package com.mycompany.web.rest;

import com.mycompany.ObraSocialApp;

import com.mycompany.domain.Affiliate;
import com.mycompany.domain.Company;
import com.mycompany.repository.AffiliateRepository;
import com.mycompany.service.AffiliateService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.mycompany.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AffiliateResource REST controller.
 *
 * @see AffiliateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ObraSocialApp.class)
public class AffiliateResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Boolean DEFAULT_PAY = false;
    private static final Boolean UPDATED_PAY = true;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private AffiliateRepository affiliateRepository;

    @Autowired
    private AffiliateService affiliateService;

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

    private MockMvc restAffiliateMockMvc;

    private Affiliate affiliate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AffiliateResource affiliateResource = new AffiliateResource(affiliateService);
        this.restAffiliateMockMvc = MockMvcBuilders.standaloneSetup(affiliateResource)
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
    public static Affiliate createEntity(EntityManager em) {
        Affiliate affiliate = new Affiliate()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .number(DEFAULT_NUMBER)
            .pay(DEFAULT_PAY)
            .remark(DEFAULT_REMARK);
        // Add required entity
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        affiliate.setCompany(company);
        return affiliate;
    }

    @Before
    public void initTest() {
        affiliate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAffiliate() throws Exception {
        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();

        // Create the Affiliate
        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isCreated());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate + 1);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAffiliate.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testAffiliate.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testAffiliate.isPay()).isEqualTo(DEFAULT_PAY);
        assertThat(testAffiliate.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createAffiliateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = affiliateRepository.findAll().size();

        // Create the Affiliate with an existing ID
        affiliate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setStartDate(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setEndDate(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setNumber(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayIsRequired() throws Exception {
        int databaseSizeBeforeTest = affiliateRepository.findAll().size();
        // set the field null
        affiliate.setPay(null);

        // Create the Affiliate, which fails.

        restAffiliateMockMvc.perform(post("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAffiliates() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get all the affiliateList
        restAffiliateMockMvc.perform(get("/api/affiliates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliate.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].pay").value(hasItem(DEFAULT_PAY.booleanValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }
    
    @Test
    @Transactional
    public void getAffiliate() throws Exception {
        // Initialize the database
        affiliateRepository.saveAndFlush(affiliate);

        // Get the affiliate
        restAffiliateMockMvc.perform(get("/api/affiliates/{id}", affiliate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(affiliate.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.pay").value(DEFAULT_PAY.booleanValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAffiliate() throws Exception {
        // Get the affiliate
        restAffiliateMockMvc.perform(get("/api/affiliates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAffiliate() throws Exception {
        // Initialize the database
        affiliateService.save(affiliate);

        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Update the affiliate
        Affiliate updatedAffiliate = affiliateRepository.findById(affiliate.getId()).get();
        // Disconnect from session so that the updates on updatedAffiliate are not directly saved in db
        em.detach(updatedAffiliate);
        updatedAffiliate
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .number(UPDATED_NUMBER)
            .pay(UPDATED_PAY)
            .remark(UPDATED_REMARK);

        restAffiliateMockMvc.perform(put("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAffiliate)))
            .andExpect(status().isOk());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
        Affiliate testAffiliate = affiliateList.get(affiliateList.size() - 1);
        assertThat(testAffiliate.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAffiliate.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testAffiliate.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testAffiliate.isPay()).isEqualTo(UPDATED_PAY);
        assertThat(testAffiliate.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingAffiliate() throws Exception {
        int databaseSizeBeforeUpdate = affiliateRepository.findAll().size();

        // Create the Affiliate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliateMockMvc.perform(put("/api/affiliates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(affiliate)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliate in the database
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAffiliate() throws Exception {
        // Initialize the database
        affiliateService.save(affiliate);

        int databaseSizeBeforeDelete = affiliateRepository.findAll().size();

        // Delete the affiliate
        restAffiliateMockMvc.perform(delete("/api/affiliates/{id}", affiliate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Affiliate> affiliateList = affiliateRepository.findAll();
        assertThat(affiliateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affiliate.class);
        Affiliate affiliate1 = new Affiliate();
        affiliate1.setId(1L);
        Affiliate affiliate2 = new Affiliate();
        affiliate2.setId(affiliate1.getId());
        assertThat(affiliate1).isEqualTo(affiliate2);
        affiliate2.setId(2L);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
        affiliate1.setId(null);
        assertThat(affiliate1).isNotEqualTo(affiliate2);
    }
}
