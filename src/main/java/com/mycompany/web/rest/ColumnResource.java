package com.mycompany.web.rest;
import com.mycompany.domain.Column;
import com.mycompany.service.ColumnService;
import com.mycompany.web.rest.errors.BadRequestAlertException;
import com.mycompany.web.rest.util.HeaderUtil;
import com.mycompany.web.rest.util.PaginationUtil;
import com.mycompany.service.dto.ColumnCriteria;
import com.mycompany.service.ColumnQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Column.
 */
@RestController
@RequestMapping("/api")
public class ColumnResource {

    private final Logger log = LoggerFactory.getLogger(ColumnResource.class);

    private static final String ENTITY_NAME = "column";

    private final ColumnService columnService;

    private final ColumnQueryService columnQueryService;

    public ColumnResource(ColumnService columnService, ColumnQueryService columnQueryService) {
        this.columnService = columnService;
        this.columnQueryService = columnQueryService;
    }

    /**
     * POST  /columns : Create a new column.
     *
     * @param column the column to create
     * @return the ResponseEntity with status 201 (Created) and with body the new column, or with status 400 (Bad Request) if the column has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/columns")
    public ResponseEntity<Column> createColumn(@RequestBody Column column) throws URISyntaxException {
        log.debug("REST request to save Column : {}", column);
        if (column.getId() != null) {
            throw new BadRequestAlertException("A new column cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Column result = columnService.save(column);
        return ResponseEntity.created(new URI("/api/columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /columns : Updates an existing column.
     *
     * @param column the column to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated column,
     * or with status 400 (Bad Request) if the column is not valid,
     * or with status 500 (Internal Server Error) if the column couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/columns")
    public ResponseEntity<Column> updateColumn(@RequestBody Column column) throws URISyntaxException {
        log.debug("REST request to update Column : {}", column);
        if (column.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Column result = columnService.save(column);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, column.getId().toString()))
            .body(result);
    }

    /**
     * GET  /columns : get all the columns.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of columns in body
     */
    @GetMapping("/columns")
    public ResponseEntity<List<Column>> getAllColumns(ColumnCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Columns by criteria: {}", criteria);
        Page<Column> page = columnQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/columns");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /columns/count : count all the columns.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/columns/count")
    public ResponseEntity<Long> countColumns(ColumnCriteria criteria) {
        log.debug("REST request to count Columns by criteria: {}", criteria);
        return ResponseEntity.ok().body(columnQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /columns/:id : get the "id" column.
     *
     * @param id the id of the column to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the column, or with status 404 (Not Found)
     */
    @GetMapping("/columns/{id}")
    public ResponseEntity<Column> getColumn(@PathVariable Long id) {
        log.debug("REST request to get Column : {}", id);
        Optional<Column> column = columnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(column);
    }

    /**
     * DELETE  /columns/:id : delete the "id" column.
     *
     * @param id the id of the column to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/columns/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long id) {
        log.debug("REST request to delete Column : {}", id);
        columnService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
