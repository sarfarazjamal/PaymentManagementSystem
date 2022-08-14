package com.jamal.payment.management.web.rest;

import com.jamal.payment.management.service.TransactionDetailsService;
import com.jamal.payment.management.web.rest.errors.BadRequestAlertException;
import com.jamal.payment.management.service.dto.TransactionDetailsDTO;
import com.jamal.payment.management.service.dto.TransactionDetailsCriteria;
import com.jamal.payment.management.service.TransactionDetailsQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.jamal.payment.management.domain.TransactionDetails}.
 */
@RestController
@RequestMapping("/api")
public class TransactionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(TransactionDetailsResource.class);

    private static final String ENTITY_NAME = "transactionDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionDetailsService transactionDetailsService;

    private final TransactionDetailsQueryService transactionDetailsQueryService;

    public TransactionDetailsResource(TransactionDetailsService transactionDetailsService, TransactionDetailsQueryService transactionDetailsQueryService) {
        this.transactionDetailsService = transactionDetailsService;
        this.transactionDetailsQueryService = transactionDetailsQueryService;
    }

    /**
     * {@code POST  /transaction-details} : Create a new transactionDetails.
     *
     * @param transactionDetailsDTO the transactionDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionDetailsDTO, or with status {@code 400 (Bad Request)} if the transactionDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-details")
    public ResponseEntity<TransactionDetailsDTO> createTransactionDetails(@RequestBody TransactionDetailsDTO transactionDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionDetails : {}", transactionDetailsDTO);
        if (transactionDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionDetailsDTO result = transactionDetailsService.save(transactionDetailsDTO);
        return ResponseEntity.created(new URI("/api/transaction-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-details} : Updates an existing transactionDetails.
     *
     * @param transactionDetailsDTO the transactionDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the transactionDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-details")
    public ResponseEntity<TransactionDetailsDTO> updateTransactionDetails(@RequestBody TransactionDetailsDTO transactionDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionDetails : {}", transactionDetailsDTO);
        if (transactionDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionDetailsDTO result = transactionDetailsService.save(transactionDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-details} : get all the transactionDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionDetails in body.
     */
    @GetMapping("/transaction-details")
    public ResponseEntity<List<TransactionDetailsDTO>> getAllTransactionDetails(TransactionDetailsCriteria criteria) {
        log.debug("REST request to get TransactionDetails by criteria: {}", criteria);
        List<TransactionDetailsDTO> entityList = transactionDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /transaction-details/count} : count all the transactionDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transaction-details/count")
    public ResponseEntity<Long> countTransactionDetails(TransactionDetailsCriteria criteria) {
        log.debug("REST request to count TransactionDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transaction-details/:id} : get the "id" transactionDetails.
     *
     * @param id the id of the transactionDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-details/{id}")
    public ResponseEntity<TransactionDetailsDTO> getTransactionDetails(@PathVariable Long id) {
        log.debug("REST request to get TransactionDetails : {}", id);
        Optional<TransactionDetailsDTO> transactionDetailsDTO = transactionDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionDetailsDTO);
    }

    /**
     * {@code DELETE  /transaction-details/:id} : delete the "id" transactionDetails.
     *
     * @param id the id of the transactionDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-details/{id}")
    public ResponseEntity<Void> deleteTransactionDetails(@PathVariable Long id) {
        log.debug("REST request to delete TransactionDetails : {}", id);
        transactionDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
