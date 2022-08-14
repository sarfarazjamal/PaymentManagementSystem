package com.jamal.payment.management.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientInfoMapperTest {

    private ClientInfoMapper clientInfoMapper;

    @BeforeEach
    public void setUp() {
        clientInfoMapper = new ClientInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(clientInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(clientInfoMapper.fromId(null)).isNull();
    }
}
