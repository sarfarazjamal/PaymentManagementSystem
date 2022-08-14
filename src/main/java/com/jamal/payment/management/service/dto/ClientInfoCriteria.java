package com.jamal.payment.management.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.jamal.payment.management.domain.enumeration.StatusEnum;
import com.jamal.payment.management.domain.enumeration.BillingIntervalEnum;
import com.jamal.payment.management.domain.enumeration.FeesTypeEnum;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.jamal.payment.management.domain.ClientInfo} entity. This class is used
 * in {@link com.jamal.payment.management.web.rest.ClientInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /client-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientInfoCriteria implements Serializable, Criteria {
    /**
     * Class for filtering StatusEnum
     */
    public static class StatusEnumFilter extends Filter<StatusEnum> {

        public StatusEnumFilter() {
        }

        public StatusEnumFilter(StatusEnumFilter filter) {
            super(filter);
        }

        @Override
        public StatusEnumFilter copy() {
            return new StatusEnumFilter(this);
        }

    }
    /**
     * Class for filtering BillingIntervalEnum
     */
    public static class BillingIntervalEnumFilter extends Filter<BillingIntervalEnum> {

        public BillingIntervalEnumFilter() {
        }

        public BillingIntervalEnumFilter(BillingIntervalEnumFilter filter) {
            super(filter);
        }

        @Override
        public BillingIntervalEnumFilter copy() {
            return new BillingIntervalEnumFilter(this);
        }

    }
    /**
     * Class for filtering FeesTypeEnum
     */
    public static class FeesTypeEnumFilter extends Filter<FeesTypeEnum> {

        public FeesTypeEnumFilter() {
        }

        public FeesTypeEnumFilter(FeesTypeEnumFilter filter) {
            super(filter);
        }

        @Override
        public FeesTypeEnumFilter copy() {
            return new FeesTypeEnumFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter client;

    private StatusEnumFilter status;

    private BillingIntervalEnumFilter billingInterval;

    private StringFilter email;

    private FeesTypeEnumFilter feesType;

    private DoubleFilter fees;

    private LongFilter transactionDetailsId;

    public ClientInfoCriteria() {
    }

    public ClientInfoCriteria(ClientInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.client = other.client == null ? null : other.client.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.billingInterval = other.billingInterval == null ? null : other.billingInterval.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.feesType = other.feesType == null ? null : other.feesType.copy();
        this.fees = other.fees == null ? null : other.fees.copy();
        this.transactionDetailsId = other.transactionDetailsId == null ? null : other.transactionDetailsId.copy();
    }

    @Override
    public ClientInfoCriteria copy() {
        return new ClientInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getClient() {
        return client;
    }

    public void setClient(StringFilter client) {
        this.client = client;
    }

    public StatusEnumFilter getStatus() {
        return status;
    }

    public void setStatus(StatusEnumFilter status) {
        this.status = status;
    }

    public BillingIntervalEnumFilter getBillingInterval() {
        return billingInterval;
    }

    public void setBillingInterval(BillingIntervalEnumFilter billingInterval) {
        this.billingInterval = billingInterval;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public FeesTypeEnumFilter getFeesType() {
        return feesType;
    }

    public void setFeesType(FeesTypeEnumFilter feesType) {
        this.feesType = feesType;
    }

    public DoubleFilter getFees() {
        return fees;
    }

    public void setFees(DoubleFilter fees) {
        this.fees = fees;
    }

    public LongFilter getTransactionDetailsId() {
        return transactionDetailsId;
    }

    public void setTransactionDetailsId(LongFilter transactionDetailsId) {
        this.transactionDetailsId = transactionDetailsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClientInfoCriteria that = (ClientInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(client, that.client) &&
            Objects.equals(status, that.status) &&
            Objects.equals(billingInterval, that.billingInterval) &&
            Objects.equals(email, that.email) &&
            Objects.equals(feesType, that.feesType) &&
            Objects.equals(fees, that.fees) &&
            Objects.equals(transactionDetailsId, that.transactionDetailsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        client,
        status,
        billingInterval,
        email,
        feesType,
        fees,
        transactionDetailsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (client != null ? "client=" + client + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (billingInterval != null ? "billingInterval=" + billingInterval + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (feesType != null ? "feesType=" + feesType + ", " : "") +
                (fees != null ? "fees=" + fees + ", " : "") +
                (transactionDetailsId != null ? "transactionDetailsId=" + transactionDetailsId + ", " : "") +
            "}";
    }

}
