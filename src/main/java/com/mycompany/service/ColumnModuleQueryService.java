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

import com.mycompany.domain.ColumnModule;
import com.mycompany.domain.*; // for static metamodels
import com.mycompany.repository.ColumnModuleRepository;
import com.mycompany.service.dto.ColumnModuleCriteria;

/**
 * Service for executing complex queries for ColumnModule entities in the database.
 * The main input is a {@link ColumnModuleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ColumnModule} or a {@link Page} of {@link ColumnModule} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ColumnModuleQueryService extends QueryService<ColumnModule> {

    private final Logger log = LoggerFactory.getLogger(ColumnModuleQueryService.class);

    private final ColumnModuleRepository columnModuleRepository;

    public ColumnModuleQueryService(ColumnModuleRepository columnModuleRepository) {
        this.columnModuleRepository = columnModuleRepository;
    }

    /**
     * Return a {@link List} of {@link ColumnModule} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ColumnModule> findByCriteria(ColumnModuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ColumnModule> specification = createSpecification(criteria);
        return columnModuleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ColumnModule} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ColumnModule> findByCriteria(ColumnModuleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ColumnModule> specification = createSpecification(criteria);
        return columnModuleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ColumnModuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ColumnModule> specification = createSpecification(criteria);
        return columnModuleRepository.count(specification);
    }

    /**
     * Function to convert ColumnModuleCriteria to a {@link Specification}
     */
    private Specification<ColumnModule> createSpecification(ColumnModuleCriteria criteria) {
        Specification<ColumnModule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ColumnModule_.id));
            }
            if (criteria.getTableName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTableName(), ColumnModule_.tableName));
            }
            if (criteria.getColumnName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColumnName(), ColumnModule_.columnName));
            }
            if (criteria.getActiveInd() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveInd(), ColumnModule_.activeInd));
            }
            if (criteria.getDataType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataType(), ColumnModule_.dataType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ColumnModule_.description));
            }
            if (criteria.getDefaultValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultValue(), ColumnModule_.defaultValue));
            }
            if (criteria.getModuleId() != null) {
                specification = specification.and(buildSpecification(criteria.getModuleId(),
                    root -> root.join(ColumnModule_.module, JoinType.LEFT).get(Module_.id)));
            }
        }
        return specification;
    }
}
