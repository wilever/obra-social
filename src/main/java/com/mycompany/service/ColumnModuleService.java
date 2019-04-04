package com.mycompany.service;

import com.mycompany.domain.ColumnModule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ColumnModule.
 */
public interface ColumnModuleService {

    /**
     * Save a columnModule.
     *
     * @param columnModule the entity to save
     * @return the persisted entity
     */
    ColumnModule save(ColumnModule columnModule);

    /**
     * Get all the columnModules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ColumnModule> findAll(Pageable pageable);


    /**
     * Get the "id" columnModule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ColumnModule> findOne(Long id);

    /**
     * Delete the "id" columnModule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
