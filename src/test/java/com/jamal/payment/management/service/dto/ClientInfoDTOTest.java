package com.jamal.payment.management.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jamal.payment.management.web.rest.TestUtil;

public class ClientInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientInfoDTO.class);
        ClientInfoDTO clientInfoDTO1 = new ClientInfoDTO();
        clientInfoDTO1.setId(1L);
        ClientInfoDTO clientInfoDTO2 = new ClientInfoDTO();
        assertThat(clientInfoDTO1).isNotEqualTo(clientInfoDTO2);
        clientInfoDTO2.setId(clientInfoDTO1.getId());
        assertThat(clientInfoDTO1).isEqualTo(clientInfoDTO2);
        clientInfoDTO2.setId(2L);
        assertThat(clientInfoDTO1).isNotEqualTo(clientInfoDTO2);
        clientInfoDTO1.setId(null);
        assertThat(clientInfoDTO1).isNotEqualTo(clientInfoDTO2);
    }
}
