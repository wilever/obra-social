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

import com.mycompany.domain.Column;
import com.mycompany.domain.*; // for static metamodels
import com.mycompany.repository.ColumnRepository;
import com.mycompany.service.dto.ColumnCriteria;

/**
 * Service for executing complex queries for Column entities in the database.
 * The main input is a {@link ColumnCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Column} or a {@link Page} of {@link Column} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ColumnQueryService extends QueryService<Column> {

    private final Logger log = LoggerFactory.getLogger(ColumnQueryService.class);

    private final ColumnRepository columnRepository;

    public ColumnQueryService(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    /**
     * Return a {@link List} of {@link Column} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Column> findByCriteria(ColumnCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Column> specification = createSpecification(criteria);
        return columnRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Column} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Column> findByCriteria(ColumnCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Column> specification = createSpecification(criteria);
        return columnRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ColumnCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Column> specification = createSpecification(criteria);
        return columnRepository.count(specification);
    }

    /**
     * Function to convert ColumnCriteria to a {@link Specification}
     */
    private Specification<Column> createSpecification(ColumnCriteria criteria) {
        Specification<Column> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Column_.id));
            }
            if (criteria.getTableName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTableName(), Column_.tableName));
            }
            if (criteria.getColumnName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColumnName(), Column_.columnName));
            }
            if (criteria.getActiveInd() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveInd(), Column_.activeInd));
            }
            if (criteria.getDataType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataType(), Column_.dataType));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Column_.description));
            }
            if (criteria.getDefaultValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultValue(), Column_.defaultValue));
            }
            if (criteria.getModuleId() != null) {
                specification = specification.and(buildSpecification(criteria.getModuleId(),
                    root -> root.join(Column_.module, JoinType.LEFT).get(Module_.id)));
            }
        }
        return specification;
    }
}
