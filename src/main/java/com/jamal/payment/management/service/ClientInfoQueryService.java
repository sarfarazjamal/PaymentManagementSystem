package com.jamal.payment.management.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.jamal.payment.management.domain.ClientInfo;
import com.jamal.payment.management.domain.*; // for static metamodels
import com.jamal.payment.management.repository.ClientInfoRepository;
import com.jamal.payment.management.service.dto.ClientInfoCriteria;
import com.jamal.payment.management.service.dto.ClientInfoDTO;
import com.jamal.payment.management.service.mapper.ClientInfoMapper;

/**
 * Service for executing complex queries for {@link ClientInfo} entities in the database.
 * The main input is a {@link ClientInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientInfoDTO} or a {@link Page} of {@link ClientInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientInfoQueryService extends QueryService<ClientInfo> {

    private final Logger log = LoggerFactory.getLogger(ClientInfoQueryService.class);

    private final ClientInfoRepository clientInfoRepository;

    private final ClientInfoMapper clientInfoMapper;

    public ClientInfoQueryService(ClientInfoRepository clientInfoRepository, ClientInfoMapper clientInfoMapper) {
        this.clientInfoRepository = clientInfoRepository;
        this.clientInfoMapper = clientInfoMapper;
    }

    /**
     * Return a {@link List} of {@link ClientInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientInfoDTO> findByCriteria(ClientInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClientInfo> specification = createSpecification(criteria);
        return clientInfoMapper.toDto(clientInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClientInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientInfoDTO> findByCriteria(ClientInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClientInfo> specification = createSpecification(criteria);
        return clientInfoRepository.findAll(specification, page)
            .map(clientInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClientInfo> specification = createSpecification(criteria);
        return clientInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link ClientInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClientInfo> createSpecification(ClientInfoCriteria criteria) {
        Specification<ClientInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClientInfo_.id));
            }
            if (criteria.getClient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClient(), ClientInfo_.client));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ClientInfo_.status));
            }
            if (criteria.getBillingInterval() != null) {
                specification = specification.and(buildSpecification(criteria.getBillingInterval(), ClientInfo_.billingInterval));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ClientInfo_.email));
            }
            if (criteria.getFeesType() != null) {
                specification = specification.and(buildSpecification(criteria.getFeesType(), ClientInfo_.feesType));
            }
            if (criteria.getFees() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFees(), ClientInfo_.fees));
            }
            if (criteria.getTransactionDetailsId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionDetailsId(),
                    root -> root.join(ClientInfo_.transactionDetails, JoinType.LEFT).get(TransactionDetails_.id)));
            }
        }
        return specification;
    }
}
