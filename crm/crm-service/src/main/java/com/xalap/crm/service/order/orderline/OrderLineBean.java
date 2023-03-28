/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order.orderline;


import com.xalap.crm.service.order.OrderBean;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author Усов Андрей
 * @since 18/01/2019
 */
@Entity()
@Table(name = OrderLineBean.NAME)
public class OrderLineBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$ORDERLINE";
    public static final String QUANTITY = "quantity";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderBean order;
    @Column(length = 5000)
    private String lineText;
    private Double price;
    @FieldName(QUANTITY)
    private int quantity;
    private Double cost;//Себестоимость, TODO сделать расчет

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderLineBean{" +
                "id=" + id +
                ", order=" + order +
                ", lineText='" + lineText + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                '}';
    }
}
