/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order.orderline;

import com.xalap.crm.service.order.OrderBean;
import com.xalap.framework.data.CrudService;
import com.xalap.framework.utils.CollectionHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Усов Андрей
 * @since 29/04/2019
 */
@Service
public class OrderLineService extends CrudService<OrderLineBean, OrderLineRepository, Integer> {

    public OrderLineService() {
        super();
    }

    public List<OrderLineBean> getOrderLines(OrderBean bean) {
        return repository().findByOrder(bean);
    }

    public Map<OrderBean, List<OrderLineBean>> getOrderLineMap(List<OrderBean> orderBeans) {
        List<OrderLineBean> byOrder = getRepository().findByOrderIn(orderBeans);
        return CollectionHelper.createMultiMap(byOrder, OrderLineBean::getOrder);
    }

    public OrderLineBean getOrCreateOrderLine(OrderBean orderBean, String productName, int quantity, double price) {
        OrderLineBean bean = repository().findByOrderAndLineTextAndQuantity(orderBean, productName, quantity);
        if (bean == null) {
            bean = new OrderLineBean();
            bean.setLineText(productName);
            bean.setOrder(orderBean);
            bean.setPrice(price);
            bean.setQuantity(quantity);
            bean = save(bean);
        }
        return bean;
    }
}
