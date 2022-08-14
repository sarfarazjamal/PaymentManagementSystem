package com.jamal.payment.management.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionDetailsMapperTest {

    private TransactionDetailsMapper transactionDetailsMapper;

    @BeforeEach
    public void setUp() {
        transactionDetailsMapper = new TransactionDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transactionDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transactionDetailsMapper.fromId(null)).isNull();
    }
}
