package com.jamal.payment.management.service;

import com.jamal.payment.management.service.dto.TransactionDetailsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.jamal.payment.management.domain.TransactionDetails}.
 */
public interface TransactionDetailsService {

    /**
     * Save a transactionDetails.
     *
     * @param transactionDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionDetailsDTO save(TransactionDetailsDTO transactionDetailsDTO);

    /**
     * Get all the transactionDetails.
     *
     * @return the list of entities.
     */
    List<TransactionDetailsDTO> findAll();
    /**
     * Get all the TransactionDetailsDTO where ClientInfo is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<TransactionDetailsDTO> findAllWhereClientInfoIsNull();


    /**
     * Get the "id" transactionDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" transactionDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
