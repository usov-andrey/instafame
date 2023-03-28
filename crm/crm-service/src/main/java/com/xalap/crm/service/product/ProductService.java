/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.product;

import com.xalap.framework.data.CrudService;
import org.springframework.stereotype.Service;

/**
 * @author Усов Андрей
 * @since 18/01/2019
 */
@Service
public class ProductService extends CrudService<ProductBean, ProductRepository, Integer> {

    public ProductService() {
        super();
    }

    public ProductBean getOrCreateProduct(String code) {
        ProductBean productBean = repository().findByCode(code);
        if (productBean == null) {
            productBean = new ProductBean();
            productBean.setCode(code);
            productBean.setName(code);
            productBean = save(productBean);
        }
        return productBean;
    }

    public ProductBean getOrCreateProduct(String name, Double price) {
        ProductBean bean = repository().findByNameAndPrice(name, price);
        if (bean == null) {
            bean = new ProductBean();
            bean.setName(name);
            bean.setPrice(price);
            bean = save(bean);
        }
        return bean;
    }

}
