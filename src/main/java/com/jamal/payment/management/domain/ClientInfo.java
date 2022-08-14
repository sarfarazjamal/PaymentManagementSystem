package com.jamal.payment.management.domain;


import javax.persistence.*;

import java.io.Serializable;

import com.jamal.payment.management.domain.enumeration.StatusEnum;

import com.jamal.payment.management.domain.enumeration.BillingIntervalEnum;

import com.jamal.payment.management.domain.enumeration.FeesTypeEnum;

/**
 * A ClientInfo.
 */
@Entity
@Table(name = "client_info")
public class ClientInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client")
    private String client;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_interval")
    private BillingIntervalEnum billingInterval;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "fees_type")
    private FeesTypeEnum feesType;

    @Column(name = "fees")
    private Double fees;

    @OneToOne
    @JoinColumn(unique = true)
    private TransactionDetails transactionDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public ClientInfo client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public ClientInfo status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public BillingIntervalEnum getBillingInterval() {
        return billingInterval;
    }

    public ClientInfo billingInterval(BillingIntervalEnum billingInterval) {
        this.billingInterval = billingInterval;
        return this;
    }

    public void setBillingInterval(BillingIntervalEnum billingInterval) {
        this.billingInterval = billingInterval;
    }

    public String getEmail() {
        return email;
    }

    public ClientInfo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FeesTypeEnum getFeesType() {
        return feesType;
    }

    public ClientInfo feesType(FeesTypeEnum feesType) {
        this.feesType = feesType;
        return this;
    }

    public void setFeesType(FeesTypeEnum feesType) {
        this.feesType = feesType;
    }

    public Double getFees() {
        return fees;
    }

    public ClientInfo fees(Double fees) {
        this.fees = fees;
        return this;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public TransactionDetails getTransactionDetails() {
        return transactionDetails;
    }

    public ClientInfo transactionDetails(TransactionDetails transactionDetails) {
        this.transactionDetails = transactionDetails;
        return this;
    }

    public void setTransactionDetails(TransactionDetails transactionDetails) {
        this.transactionDetails = transactionDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientInfo)) {
            return false;
        }
        return id != null && id.equals(((ClientInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientInfo{" +
            "id=" + getId() +
            ", client='" + getClient() + "'" +
            ", status='" + getStatus() + "'" +
            ", billingInterval='" + getBillingInterval() + "'" +
            ", email='" + getEmail() + "'" +
            ", feesType='" + getFeesType() + "'" +
            ", fees=" + getFees() +
            "}";
    }
}
