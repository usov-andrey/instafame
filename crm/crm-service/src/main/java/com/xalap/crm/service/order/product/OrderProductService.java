/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order.product;

import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.crm.service.product.ProductService;
import com.xalap.framework.data.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Усов Андрей
 * @since 29/04/2019
 */
@Service
public class OrderProductService extends CrudService<OrderProductBean, OrderProductRepository, Integer> {

    @Autowired
    private ProductService productService;

    public OrderProductService() {
        super();
    }

    public List<OrderProductBean> getProducts(OrderBean orderBean) {
        return repository().findByPayment(orderBean);
    }

    public Map<OrderBean, List<OrderProductBean>> getProductMap() {
        Map<OrderBean, List<OrderProductBean>> result = new HashMap<>();
        for (OrderProductBean orderProductBean : getAll()) {
            result.computeIfAbsent(orderProductBean.getPayment(), orderBean -> new ArrayList<>()).add(orderProductBean);
        }
        return result;
    }

    private OrderProductBean addOrderProduct(OrderBean paymentBean, ProductBean productBean, int quantity, Double price) {
        OrderProductBean bean = new OrderProductBean();
        bean.setPayment(paymentBean);
        bean.setProduct(productBean);
        bean.setQuantity(quantity);
        bean.setPrice(price);
        return save(bean);
    }

    public OrderProductBean getOrAddProduct(OrderBean order, String productCode, int quantity) {
        ProductBean productBean = productService.getOrCreateProduct(productCode);
        double price = productBean.getPrice() * quantity;
        OrderProductBean orderProductBean = repository().findByProductAndPaymentAndQuantity(productBean, order, quantity);
        if (orderProductBean == null) {
            orderProductBean = addOrderProduct(order, productBean, quantity, price);
        }
        return orderProductBean;
    }
}
