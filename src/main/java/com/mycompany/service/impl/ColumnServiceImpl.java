package com.mycompany.service.impl;

import com.mycompany.service.ColumnService;
import com.mycompany.domain.Column;
import com.mycompany.repository.ColumnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Column.
 */
@Service
@Transactional
public class ColumnServiceImpl implements ColumnService {

    private final Logger log = LoggerFactory.getLogger(ColumnServiceImpl.class);

    private final ColumnRepository columnRepository;

    public ColumnServiceImpl(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    /**
     * Save a column.
     *
     * @param column the entity to save
     * @return the persisted entity
     */
    @Override
    public Column save(Column column) {
        log.debug("Request to save Column : {}", column);
        return columnRepository.save(column);
    }

    /**
     * Get all the columns.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Column> findAll(Pageable pageable) {
        log.debug("Request to get all Columns");
        return columnRepository.findAll(pageable);
    }


    /**
     * Get one column by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Column> findOne(Long id) {
        log.debug("Request to get Column : {}", id);
        return columnRepository.findById(id);
    }

    /**
     * Delete the column by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Column : {}", id);
        columnRepository.deleteById(id);
    }
}
