package com.jamal.payment.management.service.dto;

import java.io.Serializable;
import com.jamal.payment.management.domain.enumeration.StatusEnum;
import com.jamal.payment.management.domain.enumeration.BillingIntervalEnum;
import com.jamal.payment.management.domain.enumeration.FeesTypeEnum;

/**
 * A DTO for the {@link com.jamal.payment.management.domain.ClientInfo} entity.
 */
public class ClientInfoDTO implements Serializable {
    
    private Long id;

    private String client;

    private StatusEnum status;

    private BillingIntervalEnum billingInterval;

    private String email;

    private FeesTypeEnum feesType;

    private Double fees;


    private Long transactionDetailsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public BillingIntervalEnum getBillingInterval() {
        return billingInterval;
    }

    public void setBillingInterval(BillingIntervalEnum billingInterval) {
        this.billingInterval = billingInterval;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FeesTypeEnum getFeesType() {
        return feesType;
    }

    public void setFeesType(FeesTypeEnum feesType) {
        this.feesType = feesType;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public Long getTransactionDetailsId() {
        return transactionDetailsId;
    }

    public void setTransactionDetailsId(Long transactionDetailsId) {
        this.transactionDetailsId = transactionDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientInfoDTO)) {
            return false;
        }

        return id != null && id.equals(((ClientInfoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientInfoDTO{" +
            "id=" + getId() +
            ", client='" + getClient() + "'" +
            ", status='" + getStatus() + "'" +
            ", billingInterval='" + getBillingInterval() + "'" +
            ", email='" + getEmail() + "'" +
            ", feesType='" + getFeesType() + "'" +
            ", fees=" + getFees() +
            ", transactionDetailsId=" + getTransactionDetailsId() +
            "}";
    }
}
