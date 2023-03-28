/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order;


import com.xalap.crm.service.lead.LeadBean;
import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolderWithName;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Entity()
@Table(name = OrderBean.NAME)
public class OrderBean implements IdHolderWithName<Integer> {

    public static final String NAME = "CRM$PAYMENT";
    public static final String CREATE_TIME = "createTime";
    public static final String STATUS = "status";
    public static final String CLIENT_COST = "clientCost";
    public static final String REVENUE = "revenue";
    public static final String COST_PRICE = "costPrice";
    public static final String ORDER_ID = "orderId";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @Caption("ID Платежа")
    @FieldName(ORDER_ID)
    private String orderId;//Ид заказа в платежной системе
    @Caption("Время создания")
    @FieldName(CREATE_TIME)
    private Date createTime;
    @Caption("Статус")
    @FieldName(STATUS)
    private OrderStatus status;
    @Caption("Клиент оплатил")
    @FieldName(CLIENT_COST)
    private Double clientCost;//Сколько заплатил клиент
    @Caption("Доход")
    @FieldName(REVENUE)
    private Double revenue;//Сколько мы получили в итоге после коммиссий и налогов
    @Caption("Себестоимость")
    @FieldName(COST_PRICE)
    private Double costPrice;//Себестоимость
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LeadBean lead;
    private String paymentMethod;
    private String currencyLabel;
    private String email;
    private String promoCode;
    private Integer discountPercent;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getClientCost() {
        return clientCost;
    }

    /**
     * Сколько клиент заплатил реально(сколько мы получили + коммиссия платежной системы)
     */
    public void setClientCost(Double clientCost) {
        this.clientCost = clientCost;
    }

    public Double getTax() {
        return getClientCost() != null && getRevenue() != null ? getClientCost() - getRevenue() : 0d;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public LeadBean getLead() {
        return lead;
    }

    public void setLead(LeadBean lead) {
        this.lead = lead;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    /**
     * Себестоимость оказания услуги
     */
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCurrencyLabel() {
        return currencyLabel;
    }

    public void setCurrencyLabel(String currencyLabel) {
        this.currencyLabel = currencyLabel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return DateHelper.getDate(createTime) + ": " + getLead().getName() + "-" + StringHelper.toString(getRevenue());
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", clientCost=" + clientCost +
                ", revenue=" + revenue +
                ", costPrice=" + costPrice +
                ", lead=" + lead +
                '}';
    }

    public double profit() {
        return getRevenue() != null ? getRevenue() -
                (getCostPrice() != null ? getCostPrice() : 0) : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderBean orderBean = (OrderBean) o;

        return id.equals(orderBean.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * @return true, если после текущего заказа был какой-то оплаченный заказ от этого же клиента
     */
    boolean havePayOrders(Collection<OrderBean> orders) {
        return orders.stream()
                .anyMatch(bean -> bean.getStatus().paid()
                        && bean.getRevenue() != null
                        && bean.getLead().getContact().equals(getLead().getContact())
                        && bean.getCreateTime().after(getCreateTime())
                );
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }
}
