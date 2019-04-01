package com.mycompany.service.impl;

import com.mycompany.service.ProviderService;
import com.mycompany.domain.Provider;
import com.mycompany.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Provider.
 */
@Service
@Transactional
public class ProviderServiceImpl implements ProviderService {

    private final Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);

    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    /**
     * Save a provider.
     *
     * @param provider the entity to save
     * @return the persisted entity
     */
    @Override
    public Provider save(Provider provider) {
        log.debug("Request to save Provider : {}", provider);
        return providerRepository.save(provider);
    }

    /**
     * Get all the providers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Provider> findAll(Pageable pageable) {
        log.debug("Request to get all Providers");
        return providerRepository.findAll(pageable);
    }


    /**
     * Get one provider by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Provider> findOne(Long id) {
        log.debug("Request to get Provider : {}", id);
        return providerRepository.findById(id);
    }

    /**
     * Delete the provider by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Provider : {}", id);        providerRepository.deleteById(id);
    }
}
