package com.jamal.payment.management.service.impl;

import com.jamal.payment.management.service.ClientInfoService;
import com.jamal.payment.management.domain.ClientInfo;
import com.jamal.payment.management.repository.ClientInfoRepository;
import com.jamal.payment.management.service.dto.ClientInfoDTO;
import com.jamal.payment.management.service.mapper.ClientInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ClientInfo}.
 */
@Service
@Transactional
public class ClientInfoServiceImpl implements ClientInfoService {

    private final Logger log = LoggerFactory.getLogger(ClientInfoServiceImpl.class);

    private final ClientInfoRepository clientInfoRepository;

    private final ClientInfoMapper clientInfoMapper;

    public ClientInfoServiceImpl(ClientInfoRepository clientInfoRepository, ClientInfoMapper clientInfoMapper) {
        this.clientInfoRepository = clientInfoRepository;
        this.clientInfoMapper = clientInfoMapper;
    }

    @Override
    public ClientInfoDTO save(ClientInfoDTO clientInfoDTO) {
        log.debug("Request to save ClientInfo : {}", clientInfoDTO);
        ClientInfo clientInfo = clientInfoMapper.toEntity(clientInfoDTO);
        clientInfo = clientInfoRepository.save(clientInfo);
        return clientInfoMapper.toDto(clientInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientInfoDTO> findAll() {
        log.debug("Request to get all ClientInfos");
        return clientInfoRepository.findAll().stream()
            .map(clientInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ClientInfoDTO> findOne(Long id) {
        log.debug("Request to get ClientInfo : {}", id);
        return clientInfoRepository.findById(id)
            .map(clientInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientInfo : {}", id);
        clientInfoRepository.deleteById(id);
    }
}
