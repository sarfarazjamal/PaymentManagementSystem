package com.jamal.payment.management.service.impl;

import com.jamal.payment.management.service.TransactionDetailsService;
import com.jamal.payment.management.domain.TransactionDetails;
import com.jamal.payment.management.repository.TransactionDetailsRepository;
import com.jamal.payment.management.service.dto.TransactionDetailsDTO;
import com.jamal.payment.management.service.mapper.TransactionDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link TransactionDetails}.
 */
@Service
@Transactional
public class TransactionDetailsServiceImpl implements TransactionDetailsService {

    private final Logger log = LoggerFactory.getLogger(TransactionDetailsServiceImpl.class);

    private final TransactionDetailsRepository transactionDetailsRepository;

    private final TransactionDetailsMapper transactionDetailsMapper;

    public TransactionDetailsServiceImpl(TransactionDetailsRepository transactionDetailsRepository, TransactionDetailsMapper transactionDetailsMapper) {
        this.transactionDetailsRepository = transactionDetailsRepository;
        this.transactionDetailsMapper = transactionDetailsMapper;
    }

    @Override
    public TransactionDetailsDTO save(TransactionDetailsDTO transactionDetailsDTO) {
        log.debug("Request to save TransactionDetails : {}", transactionDetailsDTO);
        TransactionDetails transactionDetails = transactionDetailsMapper.toEntity(transactionDetailsDTO);
        transactionDetails = transactionDetailsRepository.save(transactionDetails);
        return transactionDetailsMapper.toDto(transactionDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionDetailsDTO> findAll() {
        log.debug("Request to get all TransactionDetails");
        return transactionDetailsRepository.findAll().stream()
            .map(transactionDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the transactionDetails where ClientInfo is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<TransactionDetailsDTO> findAllWhereClientInfoIsNull() {
        log.debug("Request to get all transactionDetails where ClientInfo is null");
        return StreamSupport
            .stream(transactionDetailsRepository.findAll().spliterator(), false)
            .filter(transactionDetails -> transactionDetails.getClientInfo() == null)
            .map(transactionDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionDetailsDTO> findOne(Long id) {
        log.debug("Request to get TransactionDetails : {}", id);
        return transactionDetailsRepository.findById(id)
            .map(transactionDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransactionDetails : {}", id);
        transactionDetailsRepository.deleteById(id);
    }
}
