package com.mycompany.web.rest;
import com.mycompany.domain.ColumnModule;
import com.mycompany.service.ColumnModuleService;
import com.mycompany.web.rest.errors.BadRequestAlertException;
import com.mycompany.web.rest.util.HeaderUtil;
import com.mycompany.web.rest.util.PaginationUtil;
import com.mycompany.service.dto.ColumnModuleCriteria;
import com.mycompany.service.ColumnModuleQueryService;
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
 * REST controller for managing ColumnModule.
 */
@RestController
@RequestMapping("/api")
public class ColumnModuleResource {

    private final Logger log = LoggerFactory.getLogger(ColumnModuleResource.class);

    private static final String ENTITY_NAME = "columnModule";

    private final ColumnModuleService columnModuleService;

    private final ColumnModuleQueryService columnModuleQueryService;

    public ColumnModuleResource(ColumnModuleService columnModuleService, ColumnModuleQueryService columnModuleQueryService) {
        this.columnModuleService = columnModuleService;
        this.columnModuleQueryService = columnModuleQueryService;
    }

    /**
     * POST  /column-modules : Create a new columnModule.
     *
     * @param columnModule the columnModule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new columnModule, or with status 400 (Bad Request) if the columnModule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/column-modules")
    public ResponseEntity<ColumnModule> createColumnModule(@RequestBody ColumnModule columnModule) throws URISyntaxException {
        log.debug("REST request to save ColumnModule : {}", columnModule);
        if (columnModule.getId() != null) {
            throw new BadRequestAlertException("A new columnModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ColumnModule result = columnModuleService.save(columnModule);
        return ResponseEntity.created(new URI("/api/column-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /column-modules : Updates an existing columnModule.
     *
     * @param columnModule the columnModule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated columnModule,
     * or with status 400 (Bad Request) if the columnModule is not valid,
     * or with status 500 (Internal Server Error) if the columnModule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/column-modules")
    public ResponseEntity<ColumnModule> updateColumnModule(@RequestBody ColumnModule columnModule) throws URISyntaxException {
        log.debug("REST request to update ColumnModule : {}", columnModule);
        if (columnModule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ColumnModule result = columnModuleService.save(columnModule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, columnModule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /column-modules : get all the columnModules.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of columnModules in body
     */
    @GetMapping("/column-modules")
    public ResponseEntity<List<ColumnModule>> getAllColumnModules(ColumnModuleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ColumnModules by criteria: {}", criteria);
        Page<ColumnModule> page = columnModuleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/column-modules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /column-modules/count : count all the columnModules.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/column-modules/count")
    public ResponseEntity<Long> countColumnModules(ColumnModuleCriteria criteria) {
        log.debug("REST request to count ColumnModules by criteria: {}", criteria);
        return ResponseEntity.ok().body(columnModuleQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /column-modules/:id : get the "id" columnModule.
     *
     * @param id the id of the columnModule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the columnModule, or with status 404 (Not Found)
     */
    @GetMapping("/column-modules/{id}")
    public ResponseEntity<ColumnModule> getColumnModule(@PathVariable Long id) {
        log.debug("REST request to get ColumnModule : {}", id);
        Optional<ColumnModule> columnModule = columnModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(columnModule);
    }

    /**
     * DELETE  /column-modules/:id : delete the "id" columnModule.
     *
     * @param id the id of the columnModule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/column-modules/{id}")
    public ResponseEntity<Void> deleteColumnModule(@PathVariable Long id) {
        log.debug("REST request to delete ColumnModule : {}", id);
        columnModuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
