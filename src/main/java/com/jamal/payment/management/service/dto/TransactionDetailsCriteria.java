package com.jamal.payment.management.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.jamal.payment.management.domain.enumeration.CurrencyEnum;
import com.jamal.payment.management.domain.enumeration.CardTypeEnum;
import com.jamal.payment.management.domain.enumeration.TransactionStatusEnum;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.jamal.payment.management.domain.TransactionDetails} entity. This class is used
 * in {@link com.jamal.payment.management.web.rest.TransactionDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transaction-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionDetailsCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CurrencyEnum
     */
    public static class CurrencyEnumFilter extends Filter<CurrencyEnum> {

        public CurrencyEnumFilter() {
        }

        public CurrencyEnumFilter(CurrencyEnumFilter filter) {
            super(filter);
        }

        @Override
        public CurrencyEnumFilter copy() {
            return new CurrencyEnumFilter(this);
        }

    }
    /**
     * Class for filtering CardTypeEnum
     */
    public static class CardTypeEnumFilter extends Filter<CardTypeEnum> {

        public CardTypeEnumFilter() {
        }

        public CardTypeEnumFilter(CardTypeEnumFilter filter) {
            super(filter);
        }

        @Override
        public CardTypeEnumFilter copy() {
            return new CardTypeEnumFilter(this);
        }

    }
    /**
     * Class for filtering TransactionStatusEnum
     */
    public static class TransactionStatusEnumFilter extends Filter<TransactionStatusEnum> {

        public TransactionStatusEnumFilter() {
        }

        public TransactionStatusEnumFilter(TransactionStatusEnumFilter filter) {
            super(filter);
        }

        @Override
        public TransactionStatusEnumFilter copy() {
            return new TransactionStatusEnumFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter orderid;

    private InstantFilter datetime;

    private DoubleFilter amount;

    private CurrencyEnumFilter currency;

    private CardTypeEnumFilter cardtype;

    private TransactionStatusEnumFilter transactionStatus;

    private StringFilter client;

    private LongFilter clientInfoId;

    public TransactionDetailsCriteria() {
    }

    public TransactionDetailsCriteria(TransactionDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderid = other.orderid == null ? null : other.orderid.copy();
        this.datetime = other.datetime == null ? null : other.datetime.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.currency = other.currency == null ? null : other.currency.copy();
        this.cardtype = other.cardtype == null ? null : other.cardtype.copy();
        this.transactionStatus = other.transactionStatus == null ? null : other.transactionStatus.copy();
        this.client = other.client == null ? null : other.client.copy();
        this.clientInfoId = other.clientInfoId == null ? null : other.clientInfoId.copy();
    }

    @Override
    public TransactionDetailsCriteria copy() {
        return new TransactionDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getOrderid() {
        return orderid;
    }

    public void setOrderid(LongFilter orderid) {
        this.orderid = orderid;
    }

    public InstantFilter getDatetime() {
        return datetime;
    }

    public void setDatetime(InstantFilter datetime) {
        this.datetime = datetime;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public CurrencyEnumFilter getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnumFilter currency) {
        this.currency = currency;
    }

    public CardTypeEnumFilter getCardtype() {
        return cardtype;
    }

    public void setCardtype(CardTypeEnumFilter cardtype) {
        this.cardtype = cardtype;
    }

    public TransactionStatusEnumFilter getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatusEnumFilter transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public StringFilter getClient() {
        return client;
    }

    public void setClient(StringFilter client) {
        this.client = client;
    }

    public LongFilter getClientInfoId() {
        return clientInfoId;
    }

    public void setClientInfoId(LongFilter clientInfoId) {
        this.clientInfoId = clientInfoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransactionDetailsCriteria that = (TransactionDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orderid, that.orderid) &&
            Objects.equals(datetime, that.datetime) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(cardtype, that.cardtype) &&
            Objects.equals(transactionStatus, that.transactionStatus) &&
            Objects.equals(client, that.client) &&
            Objects.equals(clientInfoId, that.clientInfoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderid,
        datetime,
        amount,
        currency,
        cardtype,
        transactionStatus,
        client,
        clientInfoId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderid != null ? "orderid=" + orderid + ", " : "") +
                (datetime != null ? "datetime=" + datetime + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (cardtype != null ? "cardtype=" + cardtype + ", " : "") +
                (transactionStatus != null ? "transactionStatus=" + transactionStatus + ", " : "") +
                (client != null ? "client=" + client + ", " : "") +
                (clientInfoId != null ? "clientInfoId=" + clientInfoId + ", " : "") +
            "}";
    }

}
