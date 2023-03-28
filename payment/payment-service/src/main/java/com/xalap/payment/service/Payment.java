/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.service;

import com.xalap.framework.data.audit.AbstractAuditingEntity;
import com.xalap.framework.domain.holder.IdHolderWithName;
import com.xalap.payment.api.PaymentDto;
import com.xalap.payment.api.PaymentStatus;
import com.xalap.payment.invoice.api.InvoiceId;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Оплата
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Entity()
@Table(name = Payment.NAME)
public class Payment extends AbstractAuditingEntity implements IdHolderWithName<Long>, PaymentDto {

    public static final String NAME = "pm$payment";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Long id;

    @Embedded
    private InvoiceId invoiceId;
    @Column(length = 10)
    private String currencyLabel;
    @Column(length = 20)
    private String paymentMethod;
    private BigDecimal fee;
    private BigDecimal customerPayAmount;
    private String customer;
    private String customerEmail;
    private PaymentStatus status = PaymentStatus.created;

    @Override
    public String getName() {
        return "Номер " + id + " на сумму " + fee + " от " + customerEmail;
    }

    @Override
    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(InvoiceId invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getCurrencyLabel() {
        return currencyLabel;
    }

    public void setCurrencyLabel(String currencyLabel) {
        this.currencyLabel = currencyLabel;
    }

    @Override
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Override
    public BigDecimal getCustomerPayAmount() {
        return customerPayAmount;
    }

    public void setCustomerPayAmount(BigDecimal customerPayAmount) {
        this.customerPayAmount = customerPayAmount;
    }

    @Override
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", currencyLabel='" + currencyLabel + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", fee=" + fee +
                ", customerPayAmount=" + customerPayAmount +
                ", customerEmail='" + customerEmail + '\'' +
                ", status=" + status +
                '}';
    }
}
