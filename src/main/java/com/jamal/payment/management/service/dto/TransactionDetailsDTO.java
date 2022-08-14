package com.jamal.payment.management.service.dto;

import java.time.Instant;
import java.io.Serializable;
import com.jamal.payment.management.domain.enumeration.CurrencyEnum;
import com.jamal.payment.management.domain.enumeration.CardTypeEnum;
import com.jamal.payment.management.domain.enumeration.TransactionStatusEnum;

/**
 * A DTO for the {@link com.jamal.payment.management.domain.TransactionDetails} entity.
 */
public class TransactionDetailsDTO implements Serializable {
    
    private Long id;

    private Long orderid;

    private Instant datetime;

    private Double amount;

    private CurrencyEnum currency;

    private CardTypeEnum cardtype;

    private TransactionStatusEnum transactionStatus;

    private String client;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Instant getDatetime() {
        return datetime;
    }

    public void setDatetime(Instant datetime) {
        this.datetime = datetime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public CardTypeEnum getCardtype() {
        return cardtype;
    }

    public void setCardtype(CardTypeEnum cardtype) {
        this.cardtype = cardtype;
    }

    public TransactionStatusEnum getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusEnum transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((TransactionDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDetailsDTO{" +
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
