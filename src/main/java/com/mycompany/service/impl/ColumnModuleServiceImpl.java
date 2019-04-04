package com.mycompany.service.impl;

import com.mycompany.service.ColumnModuleService;
import com.mycompany.domain.ColumnModule;
import com.mycompany.repository.ColumnModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ColumnModule.
 */
@Service
@Transactional
public class ColumnModuleServiceImpl implements ColumnModuleService {

    private final Logger log = LoggerFactory.getLogger(ColumnModuleServiceImpl.class);

    private final ColumnModuleRepository columnModuleRepository;

    public ColumnModuleServiceImpl(ColumnModuleRepository columnModuleRepository) {
        this.columnModuleRepository = columnModuleRepository;
    }

    /**
     * Save a columnModule.
     *
     * @param columnModule the entity to save
     * @return the persisted entity
     */
    @Override
    public ColumnModule save(ColumnModule columnModule) {
        log.debug("Request to save ColumnModule : {}", columnModule);
        return columnModuleRepository.save(columnModule);
    }

    /**
     * Get all the columnModules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ColumnModule> findAll(Pageable pageable) {
        log.debug("Request to get all ColumnModules");
        return columnModuleRepository.findAll(pageable);
    }


    /**
     * Get one columnModule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ColumnModule> findOne(Long id) {
        log.debug("Request to get ColumnModule : {}", id);
        return columnModuleRepository.findById(id);
    }

    /**
     * Delete the columnModule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ColumnModule : {}", id);
        columnModuleRepository.deleteById(id);
    }
}
