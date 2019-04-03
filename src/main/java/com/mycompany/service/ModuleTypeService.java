package com.mycompany.service;

import com.mycompany.domain.ModuleType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ModuleType.
 */
public interface ModuleTypeService {

    /**
     * Save a moduleType.
     *
     * @param moduleType the entity to save
     * @return the persisted entity
     */
    ModuleType save(ModuleType moduleType);

    /**
     * Get all the moduleTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ModuleType> findAll(Pageable pageable);


    /**
     * Get the "id" moduleType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ModuleType> findOne(Long id);

    /**
     * Delete the "id" moduleType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
