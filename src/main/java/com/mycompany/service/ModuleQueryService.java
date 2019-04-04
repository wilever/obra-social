package com.mycompany.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.domain.Module;
import com.mycompany.domain.*; // for static metamodels
import com.mycompany.repository.ModuleRepository;
import com.mycompany.service.dto.ModuleCriteria;

/**
 * Service for executing complex queries for Module entities in the database.
 * The main input is a {@link ModuleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Module} or a {@link Page} of {@link Module} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ModuleQueryService extends QueryService<Module> {

    private final Logger log = LoggerFactory.getLogger(ModuleQueryService.class);

    private final ModuleRepository moduleRepository;

    public ModuleQueryService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    /**
     * Return a {@link List} of {@link Module} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Module> findByCriteria(ModuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Module> specification = createSpecification(criteria);
        return moduleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Module} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Module> findByCriteria(ModuleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Module> specification = createSpecification(criteria);
        return moduleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ModuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Module> specification = createSpecification(criteria);
        return moduleRepository.count(specification);
    }

    /**
     * Function to convert ModuleCriteria to a {@link Specification}
     */
    private Specification<Module> createSpecification(ModuleCriteria criteria) {
        Specification<Module> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Module_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Module_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Module_.description));
            }
            if (criteria.getModuleId() != null) {
                specification = specification.and(buildSpecification(criteria.getModuleId(),
                    root -> root.join(Module_.module, JoinType.LEFT).get(Module_.id)));
            }
            if (criteria.getModuleId() != null) {
                specification = specification.and(buildSpecification(criteria.getModuleId(),
                    root -> root.join(Module_.modules, JoinType.LEFT).get(Module_.id)));
            }
            if (criteria.getModuleId() != null) {
                specification = specification.and(buildSpecification(criteria.getModuleId(),
                    root -> root.join(Module_.modules, JoinType.LEFT).get(ColumnModule_.id)));
            }
            if (criteria.getModuleTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getModuleTypeId(),
                    root -> root.join(Module_.moduleType, JoinType.LEFT).get(ModuleType_.id)));
            }
            if (criteria.getTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getTagId(),
                    root -> root.join(Module_.tag, JoinType.LEFT).get(Tag_.id)));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(Module_.company, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}
