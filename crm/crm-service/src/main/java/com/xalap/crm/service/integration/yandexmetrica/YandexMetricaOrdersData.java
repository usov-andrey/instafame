/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.integration.yandexmetrica.contact.YandexContact;
import com.xalap.crm.service.integration.yandexmetrica.contact.YandexContacts;
import com.xalap.crm.service.integration.yandexmetrica.order.YandexOrder;
import com.xalap.crm.service.integration.yandexmetrica.order.YandexOrders;
import com.xalap.crm.service.integration.yandexmetrica.orderstatus.YandexOrderStatus;
import com.xalap.crm.service.integration.yandexmetrica.orderstatus.YandexOrderStatusMapping;
import com.xalap.crm.service.integration.yandexmetrica.orderstatus.YandexOrderStatuses;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.framework.utils.DateUtils;
import com.xalap.framework.utils.StringHelper;

import java.util.*;

/**
 * Информацию, которую нужно передать в яндекс для того, чтобы отобразились заказы
 *
 * @author Usov Andrey
 * @since 24.02.2022
 */
public class YandexMetricaOrdersData {

    private final YandexOrderStatuses statuses = new YandexOrderStatuses();
    private final List<YandexOrder> orderList = new ArrayList<>();
    private final Map<ContactBean, YandexContact> contactMap = new HashMap<>();

    public YandexMetricaOrdersData(List<OrderBean> orders) {
        fillStatuses();
        fillOrders(orders);
    }

    private void fillOrders(List<OrderBean> orders) {
        for (OrderBean orderBean : orders) {
            LeadBean lead = orderBean.getLead();
            String clientId = getYandexClientId(lead);
            if (!StringHelper.isEmpty(clientId)) {
                //Найден заказ, который нужно обработать
                addContact(clientId, lead.getContact());
                addOrder(orderBean);
            }
        }
    }

    private void fillStatuses() {
        ArrayList<YandexOrderStatus> orderStatuses = new ArrayList<>();
        statuses.setOrderStatuses(orderStatuses);
        for (YandexOrderStatusMapping value : YandexOrderStatusMapping.values()) {
            YandexOrderStatus yandexOrderStatus = new YandexOrderStatus();
            yandexOrderStatus.setId(value.getCrmValue());
            yandexOrderStatus.setType(value.getYandexValue());
            orderStatuses.add(yandexOrderStatus);
        }
    }

    private void addContact(String clientId, ContactBean contactBean) {
        YandexContact contact = new YandexContact();
        contact.setUniqId(contactBean.idToString());
        contact.setClientIds(Collections.singletonList(clientId));
        contact.setName(contactBean.getName());
        contact.setCreateDateTime(format(contactBean.getCreateTime()));
        //contact.setUpdateDateTime(format(new Date()));
        contactMap.put(contactBean, contact);
    }

    private void addOrder(OrderBean orderBean) {
        YandexOrder order = new YandexOrder();
        order.setId(orderBean.getOrderId());
        order.setClientUniqId(orderBean.getLead().getContact().idToString());
        order.setClientType("CONTACT");
        order.setCreateDateTime(format(orderBean.getCreateTime()));//"2020-04-14 13:17:00"
        order.setRevenue(orderBean.getRevenue());
        order.setOrderStatus(YandexOrderStatusMapping.getStatus(orderBean).getCrmValue());
        order.setCost(orderBean.getCostPrice());
        orderList.add(order);
    }

    private String format(Date date) {
        return DateUtils.format("yyyy-MM-dd HH:mm:ss", DateUtils.localDateTime(date));
    }

    private String getYandexClientId(LeadBean leadBean) {
        return leadBean.cookie("_ym_uid");
    }

    public boolean isEmpty() {
        return orderList.isEmpty();
    }

    public YandexOrderStatuses getStatuses() {
        return statuses;
    }

    public YandexOrders getYandexOrders() {
        YandexOrders yandexOrders = new YandexOrders();
        yandexOrders.setOrders(orderList);
        return yandexOrders;
    }

    public YandexContacts getYandexContacts() {
        YandexContacts yandexContacts = new YandexContacts();
        yandexContacts.setContacts(new ArrayList<>(contactMap.values()));
        return yandexContacts;
    }

    @Override
    public String toString() {
        return "YandexOrders{" +
                "statuses=" + statuses +
                ", orderList=" + orderList +
                ", contactMap=" + contactMap +
                '}';
    }
}
