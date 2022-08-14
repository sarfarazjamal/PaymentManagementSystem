package com.jamal.payment.management.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.jamal.payment.management.web.rest.TestUtil;

public class TransactionDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionDetailsDTO.class);
        TransactionDetailsDTO transactionDetailsDTO1 = new TransactionDetailsDTO();
        transactionDetailsDTO1.setId(1L);
        TransactionDetailsDTO transactionDetailsDTO2 = new TransactionDetailsDTO();
        assertThat(transactionDetailsDTO1).isNotEqualTo(transactionDetailsDTO2);
        transactionDetailsDTO2.setId(transactionDetailsDTO1.getId());
        assertThat(transactionDetailsDTO1).isEqualTo(transactionDetailsDTO2);
        transactionDetailsDTO2.setId(2L);
        assertThat(transactionDetailsDTO1).isNotEqualTo(transactionDetailsDTO2);
        transactionDetailsDTO1.setId(null);
        assertThat(transactionDetailsDTO1).isNotEqualTo(transactionDetailsDTO2);
    }
}
