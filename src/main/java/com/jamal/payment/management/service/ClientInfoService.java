package com.jamal.payment.management.service;

import com.jamal.payment.management.service.dto.ClientInfoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.jamal.payment.management.domain.ClientInfo}.
 */
public interface ClientInfoService {

    /**
     * Save a clientInfo.
     *
     * @param clientInfoDTO the entity to save.
     * @return the persisted entity.
     */
    ClientInfoDTO save(ClientInfoDTO clientInfoDTO);

    /**
     * Get all the clientInfos.
     *
     * @return the list of entities.
     */
    List<ClientInfoDTO> findAll();


    /**
     * Get the "id" clientInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientInfoDTO> findOne(Long id);

    /**
     * Delete the "id" clientInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
