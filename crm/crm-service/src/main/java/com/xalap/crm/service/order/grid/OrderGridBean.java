/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order.grid;

import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.orderline.OrderLineBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Usov Andrey
 * @since 2020-05-11
 */
public class OrderGridBean implements Serializable {

    private final OrderBean orderBean;

    public OrderGridBean(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    /**
     * Позиции заказа
     */
    private List<OrderLineBean> orderLineBeanList = new ArrayList<>();
    /**
     * сколько всего заплачено
     */
    private double clientPayAmount = 0;
    /**
     * сколько всего оплачено заказов
     */
    private int clientPayOrderCount = 0;
    /**
     * когда последняя оплата была
     */
    private Date clientLastOrderDate;

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public List<OrderLineBean> getOrderLineBeanList() {
        return orderLineBeanList;
    }

    public void setOrderLineBeanList(List<OrderLineBean> orderLineBeanList) {
        this.orderLineBeanList = orderLineBeanList;
    }

    public double getClientPayAmount() {
        return clientPayAmount;
    }

    public int getClientPayOrderCount() {
        return clientPayOrderCount;
    }

    public Date getClientLastOrderDate() {
        return clientLastOrderDate;
    }

    /**
     * Считаем разные цифры в зависимости от других заказов этого клиента
     */
    public void calcNumbersByOrders(List<OrderBean> contactOrders) {
        contactOrders.stream()
                .filter(bean -> bean.getStatus().paid())
                .forEach(bean -> {
                    if (bean.getRevenue() != null) {
                        clientPayAmount += bean.getRevenue();
                        clientPayOrderCount += 1;
                        if (clientLastOrderDate == null || clientLastOrderDate.before(bean.getCreateTime())) {
                            clientLastOrderDate = bean.getCreateTime();
                        }
                    }
                });
    }
}
