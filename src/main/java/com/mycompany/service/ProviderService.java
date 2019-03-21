package com.mycompany.service;

import com.mycompany.domain.Provider;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Provider.
 */
public interface ProviderService {

    /**
     * Save a provider.
     *
     * @param provider the entity to save
     * @return the persisted entity
     */
    Provider save(Provider provider);

    /**
     * Get all the providers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Provider> findAll(Pageable pageable);


    /**
     * Get the "id" provider.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Provider> findOne(Long id);

    /**
     * Delete the "id" provider.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
