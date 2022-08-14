package com.jamal.payment.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.jamal.payment.management.domain.enumeration.CurrencyEnum;

import com.jamal.payment.management.domain.enumeration.CardTypeEnum;

import com.jamal.payment.management.domain.enumeration.TransactionStatusEnum;

/**
 * A TransactionDetails.
 */
@Entity
@Table(name = "transaction_details")
public class TransactionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orderid")
    private Long orderid;

    @Column(name = "datetime")
    private Instant datetime;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CurrencyEnum currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "cardtype")
    private CardTypeEnum cardtype;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatusEnum transactionStatus;

    @Column(name = "client")
    private String client;

    @OneToOne(mappedBy = "transactionDetails")
    @JsonIgnore
    private ClientInfo clientInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderid() {
        return orderid;
    }

    public TransactionDetails orderid(Long orderid) {
        this.orderid = orderid;
        return this;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public TransactionDetails datetime(Instant datetime) {
        this.datetime = datetime;
        return this;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public Double getAmount() {
        return amount;
    }

    public TransactionDetails amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public TransactionDetails currency(CurrencyEnum currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public CardTypeEnum getCardtype() {
        return cardtype;
    }

    public TransactionDetails cardtype(CardTypeEnum cardtype) {
        this.cardtype = cardtype;
        return this;
    }

    public void setCardtype(CardTypeEnum cardtype) {
        this.cardtype = cardtype;
    }

    public TransactionStatusEnum getTransactionStatus() {
        return transactionStatus;
    }

    public TransactionDetails transactionStatus(TransactionStatusEnum transactionStatus) {
        this.transactionStatus = transactionStatus;
        return this;
    }

    public void setTransactionStatus(TransactionStatusEnum transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getClient() {
        return client;
    }

    public TransactionDetails client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public TransactionDetails clientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
        return this;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDetails)) {
            return false;
        }
        return id != null && id.equals(((TransactionDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDetails{" +
            "id=" + getId() +
            ", orderid=" + getOrderid() +
            ", datetime='" + getDatetime() + "'" +
            ", amount=" + getAmount() +
            ", currency='" + getCurrency() + "'" +
            ", cardtype='" + getCardtype() + "'" +
            ", transactionStatus='" + getTransactionStatus() + "'" +
            ", client='" + getClient() + "'" +
            "}";
    }
}
