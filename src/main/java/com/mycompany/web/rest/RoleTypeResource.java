package com.mycompany.web.rest;
import com.mycompany.domain.RoleType;
import com.mycompany.service.RoleTypeService;
import com.mycompany.web.rest.errors.BadRequestAlertException;
import com.mycompany.web.rest.util.HeaderUtil;
import com.mycompany.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RoleType.
 */
@RestController
@RequestMapping("/api")
public class RoleTypeResource {

    private final Logger log = LoggerFactory.getLogger(RoleTypeResource.class);

    private static final String ENTITY_NAME = "roleType";

    private final RoleTypeService roleTypeService;

    public RoleTypeResource(RoleTypeService roleTypeService) {
        this.roleTypeService = roleTypeService;
    }

    /**
     * POST  /role-types : Create a new roleType.
     *
     * @param roleType the roleType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleType, or with status 400 (Bad Request) if the roleType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/role-types")
    public ResponseEntity<RoleType> createRoleType(@Valid @RequestBody RoleType roleType) throws URISyntaxException {
        log.debug("REST request to save RoleType : {}", roleType);
        if (roleType.getId() != null) {
            throw new BadRequestAlertException("A new roleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleType result = roleTypeService.save(roleType);
        return ResponseEntity.created(new URI("/api/role-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /role-types : Updates an existing roleType.
     *
     * @param roleType the roleType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleType,
     * or with status 400 (Bad Request) if the roleType is not valid,
     * or with status 500 (Internal Server Error) if the roleType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/role-types")
    public ResponseEntity<RoleType> updateRoleType(@Valid @RequestBody RoleType roleType) throws URISyntaxException {
        log.debug("REST request to update RoleType : {}", roleType);
        if (roleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleType result = roleTypeService.save(roleType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roleType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /role-types : get all the roleTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roleTypes in body
     */
    @GetMapping("/role-types")
    public ResponseEntity<List<RoleType>> getAllRoleTypes(Pageable pageable) {
        log.debug("REST request to get a page of RoleTypes");
        Page<RoleType> page = roleTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/role-types");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /role-types/:id : get the "id" roleType.
     *
     * @param id the id of the roleType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleType, or with status 404 (Not Found)
     */
    @GetMapping("/role-types/{id}")
    public ResponseEntity<RoleType> getRoleType(@PathVariable Long id) {
        log.debug("REST request to get RoleType : {}", id);
        Optional<RoleType> roleType = roleTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleType);
    }

    /**
     * DELETE  /role-types/:id : delete the "id" roleType.
     *
     * @param id the id of the roleType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/role-types/{id}")
    public ResponseEntity<Void> deleteRoleType(@PathVariable Long id) {
        log.debug("REST request to delete RoleType : {}", id);
        roleTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
