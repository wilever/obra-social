package com.mycompany.service;

import com.mycompany.domain.Column;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Column.
 */
public interface ColumnService {

    /**
     * Save a column.
     *
     * @param column the entity to save
     * @return the persisted entity
     */
    Column save(Column column);

    /**
     * Get all the columns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Column> findAll(Pageable pageable);


    /**
     * Get the "id" column.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Column> findOne(Long id);

    /**
     * Delete the "id" column.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
