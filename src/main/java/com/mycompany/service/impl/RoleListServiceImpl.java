package com.mycompany.service.impl;

import com.mycompany.service.RoleListService;
import com.mycompany.domain.RoleList;
import com.mycompany.repository.RoleListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RoleList.
 */
@Service
@Transactional
public class RoleListServiceImpl implements RoleListService {

    private final Logger log = LoggerFactory.getLogger(RoleListServiceImpl.class);

    private final RoleListRepository roleListRepository;

    public RoleListServiceImpl(RoleListRepository roleListRepository) {
        this.roleListRepository = roleListRepository;
    }

    /**
     * Save a roleList.
     *
     * @param roleList the entity to save
     * @return the persisted entity
     */
    @Override
    public RoleList save(RoleList roleList) {
        log.debug("Request to save RoleList : {}", roleList);
        return roleListRepository.save(roleList);
    }

    /**
     * Get all the roleLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoleList> findAll(Pageable pageable) {
        log.debug("Request to get all RoleLists");
        return roleListRepository.findAll(pageable);
    }


    /**
     * Get one roleList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoleList> findOne(Long id) {
        log.debug("Request to get RoleList : {}", id);
        return roleListRepository.findById(id);
    }

    /**
     * Delete the roleList by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleList : {}", id);        roleListRepository.deleteById(id);
    }
}
