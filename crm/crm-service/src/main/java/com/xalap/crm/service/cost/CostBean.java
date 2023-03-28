/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.cost;


import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Entity()
@Table(name = CostBean.NAME)
public class CostBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$COST";
    public static final String COST_TIME = "costTime";
    public static final String COST_F = "cost";
    public static final String COST_TYPE = "costType";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @FieldName(COST_TIME)
    @NotNull
    private LocalDate costTime;
    @FieldName(COST_F)
    @NotNull
    private BigDecimal cost;//Стоимость
    @FieldName(COST_TYPE)
    private CostType costType;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        var costBean = (CostBean) o;

        return id != null ? id.equals(costBean.id) : costBean.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public LocalDate getCostTime() {
        return costTime;
    }

    public void setCostTime(LocalDate costTime) {
        this.costTime = costTime;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public CostType getCostType() {
        return costType;
    }

    public void setCostType(CostType costType) {
        this.costType = costType;
    }
}
