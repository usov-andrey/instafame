/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.invoice.service;

import com.xalap.framework.data.audit.AbstractAuditingEntity;
import com.xalap.framework.domain.holder.IdHolderWithName;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.payment.invoice.api.InvoiceId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * В каждой платежной системе информация о счете может иметь свой тип,
 * например, в robokassa это Integer, а например, в Yandex Kassa это называется ключ идемпотентности
 * и имеет тип строка.
 *
 * При этом возможен вариант, когда этот идентификатор формируется внешней системой и нам его нужно просто сохранить.
 * Также возможна ситуация, когда мы и внешняя система генерируем один и тот же идентификатор.
 *
 * Поэтому в Invoice мы храним ид PaymentServiceProvider и внешний id уникальный в рамках каждого PSP
 *
 * @author Usov Andrey
 * @since 2020-04-16
 */
@Entity()
@Table(name = Invoice.NAME)
public class Invoice extends AbstractAuditingEntity implements IdHolderWithName<Long>, InvoiceDto {

    public static final String NAME = "pm$invoice";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Long id;
    @Embedded
    @NotNull
    private InvoiceId invoiceId;
    @NotNull
    private String handler;//Кто дальше будет обрабатывать оплату этого счета
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String customer;
    private String customerEmail;
    @NotNull
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(InvoiceId invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String getName() {
        return "Номер:" + id + " для " + customer;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", handler='" + handler + '\'' +
                ", amount=" + amount +
                ", customer='" + customer + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
