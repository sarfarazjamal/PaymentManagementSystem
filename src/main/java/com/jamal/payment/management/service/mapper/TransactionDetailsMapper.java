package com.jamal.payment.management.service.mapper;


import com.jamal.payment.management.domain.*;
import com.jamal.payment.management.service.dto.TransactionDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionDetails} and its DTO {@link TransactionDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionDetailsMapper extends EntityMapper<TransactionDetailsDTO, TransactionDetails> {


    @Mapping(target = "clientInfo", ignore = true)
    TransactionDetails toEntity(TransactionDetailsDTO transactionDetailsDTO);

    default TransactionDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setId(id);
        return transactionDetails;
    }
}
