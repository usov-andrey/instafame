/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance.product;

import com.xalap.crm.service.product.ProductBean;
import com.xalap.crm.ui.finance.SalesBean;
import com.xalap.crm.ui.finance.period.StartPeriod;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Усов Андрей
 * @since 07/01/2020
 */
public class ProductCostGridBean implements Serializable {

    private final StartPeriod period;
    private Map<ProductBean, SalesBean> salesBeanMap = new HashMap<>();

    public ProductCostGridBean(StartPeriod period) {
        this.period = period;
    }

    public StartPeriod getPeriod() {
        return period;
    }

    public Map<ProductBean, SalesBean> getSalesBeanMap() {
        return salesBeanMap;
    }

    public void setSalesBeanMap(Map<ProductBean, SalesBean> salesBeanMap) {
        this.salesBeanMap = salesBeanMap;
    }
}
