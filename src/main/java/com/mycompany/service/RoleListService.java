package com.mycompany.service;

import com.mycompany.domain.RoleList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RoleList.
 */
public interface RoleListService {

    /**
     * Save a roleList.
     *
     * @param roleList the entity to save
     * @return the persisted entity
     */
    RoleList save(RoleList roleList);

    /**
     * Get all the roleLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RoleList> findAll(Pageable pageable);


    /**
     * Get the "id" roleList.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoleList> findOne(Long id);

    /**
     * Delete the "id" roleList.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
