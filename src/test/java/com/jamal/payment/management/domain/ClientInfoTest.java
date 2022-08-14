package com.jamal.payment.management.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jamal.payment.management.web.rest.TestUtil;

public class ClientInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientInfo.class);
        ClientInfo clientInfo1 = new ClientInfo();
        clientInfo1.setId(1L);
        ClientInfo clientInfo2 = new ClientInfo();
        clientInfo2.setId(clientInfo1.getId());
        assertThat(clientInfo1).isEqualTo(clientInfo2);
        clientInfo2.setId(2L);
        assertThat(clientInfo1).isNotEqualTo(clientInfo2);
        clientInfo1.setId(null);
        assertThat(clientInfo1).isNotEqualTo(clientInfo2);
    }
}
