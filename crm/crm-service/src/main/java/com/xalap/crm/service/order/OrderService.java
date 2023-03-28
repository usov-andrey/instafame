/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.integration.google.GoogleMeasureProtocolService;
import com.xalap.crm.service.integration.robokassa.RobokassaService;
import com.xalap.crm.service.integration.yandexmetrica.YandexMetricaService;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.crm.service.order.product.OrderProductBean;
import com.xalap.crm.service.order.product.OrderProductService;
import com.xalap.crm.service.scheduler.SchedulerService;
import com.xalap.framework.data.CrudService;
import com.xalap.framework.logging.HasLog;
import com.xalap.framework.utils.CollectionHelper;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Service
public class OrderService extends CrudService<OrderBean, OrderRepository, Integer> implements HasLog {

    private final OrderLineService orderLineService;
    private final OrderProductService orderProductService;
    private final GoogleMeasureProtocolService googleMeasureProtocolService;
    private final YandexMetricaService yandexMetricaService;
    private final RobokassaService robokassaService;
    private final SchedulerService schedulerService;
    private boolean testMode = false;

    public OrderService(OrderLineService orderLineService, OrderProductService orderProductService,
                        GoogleMeasureProtocolService googleMeasureProtocolService,
                        YandexMetricaService yandexMetricaService, RobokassaService robokassaService,
                        SchedulerService schedulerService) {
        super();
        this.orderLineService = orderLineService;
        this.orderProductService = orderProductService;
        this.googleMeasureProtocolService = googleMeasureProtocolService;
        this.yandexMetricaService = yandexMetricaService;
        this.robokassaService = robokassaService;
        this.schedulerService = schedulerService;
    }

    @org.springframework.context.event.EventListener({ContextRefreshedEvent.class})
    public void init() {
        schedulerService.register(ForgotOrderNotificationSchedulerTask.class, "Напоминание о неоплаченных заказах");
    }

    /**
     * Отправка информации о транзакции в GA через Google Measurement Protocol
     */
    public void sendToAnalytics(OrderBean bean) {
        if (!testMode) {
            sendToGoogle(bean);
            sendToYandex(bean);
        }    
    }

    public void sendToGoogle(OrderBean bean) {
        googleMeasureProtocolService.transaction(bean, orderLineService.getOrderLines(bean));
        googleMeasureProtocolService.goal(bean.getLead(), "order", "buy");
    }

    public void sendToYandex(OrderBean bean) {
        try {
            yandexMetricaService.sendOrder(bean);
        } catch (IOException e) {
            log().error("Error on send to yandex metrica for order:" + bean, e);
        }
    }

    public void sendToYandex(List<OrderBean> beans) {
        try {
            yandexMetricaService.sendOrders(beans);
        } catch (IOException e) {
            log().error("Error on send to yandex metrica for order:" + beans, e);
        }
    }

    public OrderBean getOrderBean(String orderId) {
        OrderBean orderBean = repository().findByOrderId(orderId);
        if (orderBean == null) {
            throw new IllegalStateException("Not found payment by orderId:" + orderId);
        }
        return orderBean;
    }

    public void delete(OrderBean orderBean) {
        for (OrderProductBean orderProductBean : orderProductService.getProducts(orderBean)) {
            orderProductService.delete(orderProductBean);
        }
        super.delete(orderBean);
    }

    public void runOrder(OrderBean orderBean) {
        orderBean.setStatus(OrderStatus.Running);
        save(orderBean);
    }

    public Map<LeadBean, OrderBean> getOrderByLeadMap() {
        Map<LeadBean, OrderBean> result = new HashMap<>();
        for (OrderBean orderBean : getAll()) {
            result.put(orderBean.getLead(), orderBean);
        }
        return result;
    }

    /**
     * Завершить выполнение заказа и отправить чек
     */
    @Async
    public void asyncCompleteAndSendReceipt(OrderBean bean) {
        robokassaService.sendReceiptAfterFinishService(bean, orderLineService.getOrderLines(bean));
        bean.setStatus(OrderStatus.CompletedAndReceipt);
        save(bean);
    }

    public Collection<OrderBean> getOrders(ContactBean contactBean) {
        return repository().findByContact(contactBean);
    }

    public Map<ContactBean, List<OrderBean>> getOrders(Collection<OrderBean> orderBeans) {
        Set<ContactBean> contactBeanSet = orderBeans.stream().map(orderBean -> orderBean.getLead().getContact())
                .collect(Collectors.toSet());
        List<OrderBean> byContacts = getRepository().findByContacts(contactBeanSet);
        return CollectionHelper.createMultiMap(byContacts, orderBean -> orderBean.getLead().getContact());
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public OrderBean createOrder(LeadBean leadBean, double payAmount, String promoCode, Integer discountPercent, String orderId) {
        OrderBean orderBean = new OrderBean();
        orderBean.setCreateTime(new Date());
        orderBean.setClientCost(payAmount);
        orderBean.setOrderId(orderId);
        orderBean.setLead(leadBean);
        orderBean.setStatus(OrderStatus.Created);
        orderBean.setDiscountPercent(discountPercent);
        orderBean.setPromoCode(promoCode);
        return save(orderBean);
    }


    /**
     * @return если есть неоплаченные заказы с момента from
     */
    boolean haveNotPayOrders(Date from) {
        List<OrderBean> orderBeans = getRepository().findFromAndInStatuses(from, OrderStatus.notPayed());
        Map<ContactBean, List<OrderBean>> ordersByContact = getOrders(orderBeans);
        for (OrderBean orderBean : orderBeans) {
            List<OrderBean> contactOrders = ordersByContact.get(orderBean.getLead().getContact());
            if (!orderBean.havePayOrders(contactOrders)) {
                return true;
            }
        }
        return false;
    }

    public List<OrderBean> getOrdersAfter(Date date) {
        return getAll().stream()
                .filter(orderBean -> orderBean.getCreateTime().after(date))
                .collect(Collectors.toList());
    }
}
