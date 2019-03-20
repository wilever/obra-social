package com.mycompany.web.rest;
import com.mycompany.domain.RoleList;
import com.mycompany.service.RoleListService;
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
 * REST controller for managing RoleList.
 */
@RestController
@RequestMapping("/api")
public class RoleListResource {

    private final Logger log = LoggerFactory.getLogger(RoleListResource.class);

    private static final String ENTITY_NAME = "roleList";

    private final RoleListService roleListService;

    public RoleListResource(RoleListService roleListService) {
        this.roleListService = roleListService;
    }

    /**
     * POST  /role-lists : Create a new roleList.
     *
     * @param roleList the roleList to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roleList, or with status 400 (Bad Request) if the roleList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/role-lists")
    public ResponseEntity<RoleList> createRoleList(@Valid @RequestBody RoleList roleList) throws URISyntaxException {
        log.debug("REST request to save RoleList : {}", roleList);
        if (roleList.getId() != null) {
            throw new BadRequestAlertException("A new roleList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleList result = roleListService.save(roleList);
        return ResponseEntity.created(new URI("/api/role-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /role-lists : Updates an existing roleList.
     *
     * @param roleList the roleList to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roleList,
     * or with status 400 (Bad Request) if the roleList is not valid,
     * or with status 500 (Internal Server Error) if the roleList couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/role-lists")
    public ResponseEntity<RoleList> updateRoleList(@Valid @RequestBody RoleList roleList) throws URISyntaxException {
        log.debug("REST request to update RoleList : {}", roleList);
        if (roleList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoleList result = roleListService.save(roleList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roleList.getId().toString()))
            .body(result);
    }

    /**
     * GET  /role-lists : get all the roleLists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of roleLists in body
     */
    @GetMapping("/role-lists")
    public ResponseEntity<List<RoleList>> getAllRoleLists(Pageable pageable) {
        log.debug("REST request to get a page of RoleLists");
        Page<RoleList> page = roleListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/role-lists");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /role-lists/:id : get the "id" roleList.
     *
     * @param id the id of the roleList to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roleList, or with status 404 (Not Found)
     */
    @GetMapping("/role-lists/{id}")
    public ResponseEntity<RoleList> getRoleList(@PathVariable Long id) {
        log.debug("REST request to get RoleList : {}", id);
        Optional<RoleList> roleList = roleListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleList);
    }

    /**
     * DELETE  /role-lists/:id : delete the "id" roleList.
     *
     * @param id the id of the roleList to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/role-lists/{id}")
    public ResponseEntity<Void> deleteRoleList(@PathVariable Long id) {
        log.debug("REST request to delete RoleList : {}", id);
        roleListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
