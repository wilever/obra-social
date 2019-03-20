package com.mycompany.service.impl;

import com.mycompany.service.RoleTypeService;
import com.mycompany.domain.RoleType;
import com.mycompany.repository.RoleTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RoleType.
 */
@Service
@Transactional
public class RoleTypeServiceImpl implements RoleTypeService {

    private final Logger log = LoggerFactory.getLogger(RoleTypeServiceImpl.class);

    private final RoleTypeRepository roleTypeRepository;

    public RoleTypeServiceImpl(RoleTypeRepository roleTypeRepository) {
        this.roleTypeRepository = roleTypeRepository;
    }

    /**
     * Save a roleType.
     *
     * @param roleType the entity to save
     * @return the persisted entity
     */
    @Override
    public RoleType save(RoleType roleType) {
        log.debug("Request to save RoleType : {}", roleType);
        return roleTypeRepository.save(roleType);
    }

    /**
     * Get all the roleTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoleType> findAll(Pageable pageable) {
        log.debug("Request to get all RoleTypes");
        return roleTypeRepository.findAll(pageable);
    }


    /**
     * Get one roleType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoleType> findOne(Long id) {
        log.debug("Request to get RoleType : {}", id);
        return roleTypeRepository.findById(id);
    }

    /**
     * Delete the roleType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleType : {}", id);        roleTypeRepository.deleteById(id);
    }
}
