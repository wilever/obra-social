package com.mycompany.service.impl;

import com.mycompany.service.AffiliateService;
import com.mycompany.domain.Affiliate;
import com.mycompany.repository.AffiliateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Affiliate.
 */
@Service
@Transactional
public class AffiliateServiceImpl implements AffiliateService {

    private final Logger log = LoggerFactory.getLogger(AffiliateServiceImpl.class);

    private final AffiliateRepository affiliateRepository;

    public AffiliateServiceImpl(AffiliateRepository affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    /**
     * Save a affiliate.
     *
     * @param affiliate the entity to save
     * @return the persisted entity
     */
    @Override
    public Affiliate save(Affiliate affiliate) {
        log.debug("Request to save Affiliate : {}", affiliate);
        return affiliateRepository.save(affiliate);
    }

    /**
     * Get all the affiliates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Affiliate> findAll(Pageable pageable) {
        log.debug("Request to get all Affiliates");
        return affiliateRepository.findAll(pageable);
    }


    /**
     * Get one affiliate by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Affiliate> findOne(Long id) {
        log.debug("Request to get Affiliate : {}", id);
        return affiliateRepository.findById(id);
    }

    /**
     * Delete the affiliate by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Affiliate : {}", id);        affiliateRepository.deleteById(id);
    }
}
