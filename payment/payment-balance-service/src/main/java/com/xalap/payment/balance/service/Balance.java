/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.balance.service;

import com.xalap.framework.data.audit.AbstractAuditingEntity;
import com.xalap.framework.domain.holder.IdHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Баланс
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Entity()
@Table(name = Balance.NAME)
public class Balance extends AbstractAuditingEntity implements IdHolder<Long> {

    public static final String NAME = "pm$balance";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Long id;
    @NotNull
    private String customer;
    @NotNull
    private BigDecimal amount;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
