package com.mycompany.web.rest;
import com.mycompany.domain.Provider;
import com.mycompany.service.ProviderService;
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
 * REST controller for managing Provider.
 */
@RestController
@RequestMapping("/api")
public class ProviderResource {

    private final Logger log = LoggerFactory.getLogger(ProviderResource.class);

    private static final String ENTITY_NAME = "provider";

    private final ProviderService providerService;

    public ProviderResource(ProviderService providerService) {
        this.providerService = providerService;
    }

    /**
     * POST  /providers : Create a new provider.
     *
     * @param provider the provider to create
     * @return the ResponseEntity with status 201 (Created) and with body the new provider, or with status 400 (Bad Request) if the provider has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/providers")
    public ResponseEntity<Provider> createProvider(@Valid @RequestBody Provider provider) throws URISyntaxException {
        log.debug("REST request to save Provider : {}", provider);
        if (provider.getId() != null) {
            throw new BadRequestAlertException("A new provider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Provider result = providerService.save(provider);
        return ResponseEntity.created(new URI("/api/providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /providers : Updates an existing provider.
     *
     * @param provider the provider to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated provider,
     * or with status 400 (Bad Request) if the provider is not valid,
     * or with status 500 (Internal Server Error) if the provider couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/providers")
    public ResponseEntity<Provider> updateProvider(@Valid @RequestBody Provider provider) throws URISyntaxException {
        log.debug("REST request to update Provider : {}", provider);
        if (provider.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Provider result = providerService.save(provider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, provider.getId().toString()))
            .body(result);
    }

    /**
     * GET  /providers : get all the providers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of providers in body
     */
    @GetMapping("/providers")
    public ResponseEntity<List<Provider>> getAllProviders(Pageable pageable) {
        log.debug("REST request to get a page of Providers");
        Page<Provider> page = providerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/providers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /providers/:id : get the "id" provider.
     *
     * @param id the id of the provider to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the provider, or with status 404 (Not Found)
     */
    @GetMapping("/providers/{id}")
    public ResponseEntity<Provider> getProvider(@PathVariable Long id) {
        log.debug("REST request to get Provider : {}", id);
        Optional<Provider> provider = providerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(provider);
    }

    /**
     * DELETE  /providers/:id : delete the "id" provider.
     *
     * @param id the id of the provider to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/providers/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        log.debug("REST request to delete Provider : {}", id);
        providerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
