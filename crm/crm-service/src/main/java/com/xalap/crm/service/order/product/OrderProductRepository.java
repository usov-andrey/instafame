/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order.product;

import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 18/01/2019
 */
@Repository
public interface OrderProductRepository extends PageableRepository<OrderProductBean, Integer> {

    @Query("select op from OrderProductBean op left join fetch op.product p left join fetch op.payment o " +
            "left join fetch o.lead l left join fetch l.contact c")
    List<OrderProductBean> findAll();

    List<OrderProductBean> findByPayment(OrderBean payment);

    List<OrderProductBean> findByProduct(ProductBean product);

    OrderProductBean findByProductAndPaymentAndQuantity(ProductBean product, OrderBean payment, int quantity);
}
