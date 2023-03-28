/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order.product;


import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.product.ProductBean;
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
@Table(name = OrderProductBean.NAME)
public class OrderProductBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$PAYMENTPRODUCT";
    public static final String QUANTITY = "quantity";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderBean payment;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductBean product;
    @FieldName(QUANTITY)
    private int quantity;
    private Double price;//Сколько заплатил за продукт клиент
    private Double cost;//Себестоимость

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public OrderBean getPayment() {
        return payment;
    }

    public void setPayment(OrderBean payment) {
        this.payment = payment;
    }

    public ProductBean getProduct() {
        return product;
    }

    public void setProduct(ProductBean product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "OrderProductBean{" +
                "id=" + id +
                ", payment=" + payment +
                ", product=" + product +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }
}
