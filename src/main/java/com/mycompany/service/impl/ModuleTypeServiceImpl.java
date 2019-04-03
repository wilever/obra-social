package com.mycompany.service.impl;

import com.mycompany.service.ModuleTypeService;
import com.mycompany.domain.ModuleType;
import com.mycompany.repository.ModuleTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ModuleType.
 */
@Service
@Transactional
public class ModuleTypeServiceImpl implements ModuleTypeService {

    private final Logger log = LoggerFactory.getLogger(ModuleTypeServiceImpl.class);

    private final ModuleTypeRepository moduleTypeRepository;

    public ModuleTypeServiceImpl(ModuleTypeRepository moduleTypeRepository) {
        this.moduleTypeRepository = moduleTypeRepository;
    }

    /**
     * Save a moduleType.
     *
     * @param moduleType the entity to save
     * @return the persisted entity
     */
    @Override
    public ModuleType save(ModuleType moduleType) {
        log.debug("Request to save ModuleType : {}", moduleType);
        return moduleTypeRepository.save(moduleType);
    }

    /**
     * Get all the moduleTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModuleType> findAll(Pageable pageable) {
        log.debug("Request to get all ModuleTypes");
        return moduleTypeRepository.findAll(pageable);
    }


    /**
     * Get one moduleType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ModuleType> findOne(Long id) {
        log.debug("Request to get ModuleType : {}", id);
        return moduleTypeRepository.findById(id);
    }

    /**
     * Delete the moduleType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModuleType : {}", id);        moduleTypeRepository.deleteById(id);
    }
}
