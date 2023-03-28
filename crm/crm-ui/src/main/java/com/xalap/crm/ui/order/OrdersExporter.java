/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order;

import com.helger.commons.csv.CSVWriter;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 30/04/2019
 */
public class OrdersExporter {

    private List<OrderBean> orders;

    public InputStream export() {
        //OutputStream
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outStream))) {
            writer.setApplyQuotesToAll(false);
            writer.setSeparatorChar(';');
            for (OrderBean order : orders) {
                String time = DateHelper.format(order.getCreateTime(), "dd.MM.yyyy");
                String orderId = Integer.toString(order.getId());
                String price = toString(order.getRevenue());
                String cost = toString(order.getCostPrice());
                String clientId = Integer.toString(order.getLead().getContact().getId());
                writer.writeNext(time, orderId, price, cost, clientId);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return new ByteArrayInputStream(outStream.toByteArray());
    }

    private String toString(Double value) {
        return StringHelper.toString(value).replace(".", ",");
    }

    public OrdersExporter setOrders(Collection<OrderBean> orders) {
        this.orders = new ArrayList<>();
        this.orders.addAll(orders);
        Collections.sort(this.orders, (o1, o2) -> o1.getCreateTime().compareTo(o2.getCreateTime()));
        return this;
    }
}
