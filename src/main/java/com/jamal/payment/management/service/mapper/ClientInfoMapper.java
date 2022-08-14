package com.jamal.payment.management.service.mapper;


import com.jamal.payment.management.domain.*;
import com.jamal.payment.management.service.dto.ClientInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClientInfo} and its DTO {@link ClientInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {TransactionDetailsMapper.class})
public interface ClientInfoMapper extends EntityMapper<ClientInfoDTO, ClientInfo> {

    @Mapping(source = "transactionDetails.id", target = "transactionDetailsId")
    ClientInfoDTO toDto(ClientInfo clientInfo);

    @Mapping(source = "transactionDetailsId", target = "transactionDetails")
    ClientInfo toEntity(ClientInfoDTO clientInfoDTO);

    default ClientInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setId(id);
        return clientInfo;
    }
}
