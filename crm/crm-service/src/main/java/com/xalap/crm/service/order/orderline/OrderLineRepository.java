/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order.orderline;

import com.xalap.crm.service.order.OrderBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 18/01/2019
 */
@Repository
public interface OrderLineRepository extends PageableRepository<OrderLineBean, Integer> {

    String QUERY = "select ol from OrderLineBean ol inner join fetch ol.order o " +
            "left join fetch o.lead l left join fetch l.contact c left join fetch l.stage s left join fetch s.pipeline p";

    @Query(QUERY)
    List<OrderLineBean> findAll();

    @Query(QUERY + " where o = :order")
    List<OrderLineBean> findByOrder(OrderBean order);

    @Query(QUERY + " where o in :orders")
    List<OrderLineBean> findByOrderIn(List<OrderBean> orders);

    OrderLineBean findByOrderAndLineTextAndQuantity(OrderBean orderBean, String productName, int quantity);
}
