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

import com.jamal.payment.management.domain.TransactionDetails;
import com.jamal.payment.management.domain.*; // for static metamodels
import com.jamal.payment.management.repository.TransactionDetailsRepository;
import com.jamal.payment.management.service.dto.TransactionDetailsCriteria;
import com.jamal.payment.management.service.dto.TransactionDetailsDTO;
import com.jamal.payment.management.service.mapper.TransactionDetailsMapper;

/**
 * Service for executing complex queries for {@link TransactionDetails} entities in the database.
 * The main input is a {@link TransactionDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionDetailsDTO} or a {@link Page} of {@link TransactionDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionDetailsQueryService extends QueryService<TransactionDetails> {

    private final Logger log = LoggerFactory.getLogger(TransactionDetailsQueryService.class);

    private final TransactionDetailsRepository transactionDetailsRepository;

    private final TransactionDetailsMapper transactionDetailsMapper;

    public TransactionDetailsQueryService(TransactionDetailsRepository transactionDetailsRepository, TransactionDetailsMapper transactionDetailsMapper) {
        this.transactionDetailsRepository = transactionDetailsRepository;
        this.transactionDetailsMapper = transactionDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionDetailsDTO> findByCriteria(TransactionDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransactionDetails> specification = createSpecification(criteria);
        return transactionDetailsMapper.toDto(transactionDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionDetailsDTO> findByCriteria(TransactionDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransactionDetails> specification = createSpecification(criteria);
        return transactionDetailsRepository.findAll(specification, page)
            .map(transactionDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransactionDetails> specification = createSpecification(criteria);
        return transactionDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransactionDetails> createSpecification(TransactionDetailsCriteria criteria) {
        Specification<TransactionDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransactionDetails_.id));
            }
            if (criteria.getOrderid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderid(), TransactionDetails_.orderid));
            }
            if (criteria.getDatetime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatetime(), TransactionDetails_.datetime));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), TransactionDetails_.amount));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildSpecification(criteria.getCurrency(), TransactionDetails_.currency));
            }
            if (criteria.getCardtype() != null) {
                specification = specification.and(buildSpecification(criteria.getCardtype(), TransactionDetails_.cardtype));
            }
            if (criteria.getTransactionStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getTransactionStatus(), TransactionDetails_.transactionStatus));
            }
            if (criteria.getClient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClient(), TransactionDetails_.client));
            }
            if (criteria.getClientInfoId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientInfoId(),
                    root -> root.join(TransactionDetails_.clientInfo, JoinType.LEFT).get(ClientInfo_.id)));
            }
        }
        return specification;
    }
}
