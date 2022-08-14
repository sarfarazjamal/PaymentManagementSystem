package com.jamal.payment.management.web.rest;

import com.jamal.payment.management.service.ClientInfoService;
import com.jamal.payment.management.web.rest.errors.BadRequestAlertException;
import com.jamal.payment.management.service.dto.ClientInfoDTO;
import com.jamal.payment.management.service.dto.ClientInfoCriteria;
import com.jamal.payment.management.service.ClientInfoQueryService;

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

/**
 * REST controller for managing {@link com.jamal.payment.management.domain.ClientInfo}.
 */
@RestController
@RequestMapping("/api")
public class ClientInfoResource {

    private final Logger log = LoggerFactory.getLogger(ClientInfoResource.class);

    private static final String ENTITY_NAME = "clientInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientInfoService clientInfoService;

    private final ClientInfoQueryService clientInfoQueryService;

    public ClientInfoResource(ClientInfoService clientInfoService, ClientInfoQueryService clientInfoQueryService) {
        this.clientInfoService = clientInfoService;
        this.clientInfoQueryService = clientInfoQueryService;
    }

    /**
     * {@code POST  /client-infos} : Create a new clientInfo.
     *
     * @param clientInfoDTO the clientInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientInfoDTO, or with status {@code 400 (Bad Request)} if the clientInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-infos")
    public ResponseEntity<ClientInfoDTO> createClientInfo(@RequestBody ClientInfoDTO clientInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ClientInfo : {}", clientInfoDTO);
        if (clientInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new clientInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientInfoDTO result = clientInfoService.save(clientInfoDTO);
        return ResponseEntity.created(new URI("/api/client-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-infos} : Updates an existing clientInfo.
     *
     * @param clientInfoDTO the clientInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientInfoDTO,
     * or with status {@code 400 (Bad Request)} if the clientInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-infos")
    public ResponseEntity<ClientInfoDTO> updateClientInfo(@RequestBody ClientInfoDTO clientInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ClientInfo : {}", clientInfoDTO);
        if (clientInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientInfoDTO result = clientInfoService.save(clientInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-infos} : get all the clientInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientInfos in body.
     */
    @GetMapping("/client-infos")
    public ResponseEntity<List<ClientInfoDTO>> getAllClientInfos(ClientInfoCriteria criteria) {
        log.debug("REST request to get ClientInfos by criteria: {}", criteria);
        List<ClientInfoDTO> entityList = clientInfoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /client-infos/count} : count all the clientInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/client-infos/count")
    public ResponseEntity<Long> countClientInfos(ClientInfoCriteria criteria) {
        log.debug("REST request to count ClientInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(clientInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /client-infos/:id} : get the "id" clientInfo.
     *
     * @param id the id of the clientInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-infos/{id}")
    public ResponseEntity<ClientInfoDTO> getClientInfo(@PathVariable Long id) {
        log.debug("REST request to get ClientInfo : {}", id);
        Optional<ClientInfoDTO> clientInfoDTO = clientInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientInfoDTO);
    }

    /**
     * {@code DELETE  /client-infos/:id} : delete the "id" clientInfo.
     *
     * @param id the id of the clientInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-infos/{id}")
    public ResponseEntity<Void> deleteClientInfo(@PathVariable Long id) {
        log.debug("REST request to delete ClientInfo : {}", id);
        clientInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
