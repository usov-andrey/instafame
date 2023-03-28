/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.yandexmetrica;

import com.xalap.crm.service.integration.yandexmetrica.contact.SendYandexContactsHttp;
import com.xalap.crm.service.integration.yandexmetrica.order.SendYandexOrdersHttp;
import com.xalap.crm.service.integration.yandexmetrica.orderstatus.SendYandexOrderStatusesHttp;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.ReflectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 31/05/2019
 */
@Service
public class YandexMetricaService {

    private static final Logger log = LoggerFactory.getLogger(YandexMetricaService.class);

    @Value("${yandex.token:}")
    private String token;
    @Value("${yandex.counter:}")
    private String counter;
    private final JsonService jsonService;

    public YandexMetricaService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public void sendOrder(OrderBean orderBean) throws IOException {
        sendOrders(Collections.singletonList(orderBean));
    }

    public void sendOrders(List<OrderBean> orders) throws IOException {
        YandexMetricaOrdersData yandexOrders = new YandexMetricaOrdersData(orders);
        if (yandexOrders.isEmpty()) {
            log.debug("Can't send to Yandex Order, client id is null:" + yandexOrders);
        }
        //Отправляем статусы
        SendYandexOrderStatusesHttp statusesHttp = createHttp(SendYandexOrderStatusesHttp.class);
        statusesHttp.send(yandexOrders.getStatuses());
        //Отправляем контакты
        SendYandexContactsHttp contactsHttp = createHttp(SendYandexContactsHttp.class);
        contactsHttp.send(yandexOrders.getYandexContacts());
        //Отправляем заказы
        SendYandexOrdersHttp ordersHttp = createHttp(SendYandexOrdersHttp.class);
        ordersHttp.send(yandexOrders.getYandexOrders());
    }

    private <T extends SendYandexHttp> T createHttp(Class<T> httpClass) {
        T http = ReflectHelper.newInstance(httpClass);
        http.init(jsonService, token, counter);
        return http;
    }

}
