package com.mycompany.service;

import com.mycompany.domain.RoleType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RoleType.
 */
public interface RoleTypeService {

    /**
     * Save a roleType.
     *
     * @param roleType the entity to save
     * @return the persisted entity
     */
    RoleType save(RoleType roleType);

    /**
     * Get all the roleTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoleType> findAll(Pageable pageable);


    /**
     * Get the "id" roleType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoleType> findOne(Long id);

    /**
     * Delete the "id" roleType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
