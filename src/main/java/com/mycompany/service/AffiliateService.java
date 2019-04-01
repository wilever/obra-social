package com.mycompany.service;

import com.mycompany.domain.Affiliate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Affiliate.
 */
public interface AffiliateService {

    /**
     * Save a affiliate.
     *
     * @param affiliate the entity to save
     * @return the persisted entity
     */
    Affiliate save(Affiliate affiliate);

    /**
     * Get all the affiliates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Affiliate> findAll(Pageable pageable);


    /**
     * Get the "id" affiliate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Affiliate> findOne(Long id);

    /**
     * Delete the "id" affiliate.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
