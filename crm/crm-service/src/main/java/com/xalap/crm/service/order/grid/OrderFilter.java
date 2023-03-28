/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order.grid;

import com.xalap.crm.service.order.OrderStatus;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.filter.PeriodSearchFilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Usov Andrey
 * @since 2020-04-30
 */
public class OrderFilter extends PeriodSearchFilter {

    public static final String PRODUCT = "product";

    private Set<String> utmSources = new HashSet<>();
    private PayStatus payStatus = PayStatus.payed;
    @FieldName(PRODUCT)
    @Caption("Продукт")
    private ProductBean product;

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public Set<String> getUtmSources() {
        return utmSources;
    }

    public void setUtmSources(Set<String> utmSources) {
        this.utmSources = utmSources;
    }

    public ProductBean getProduct() {
        return product;
    }

    public OrderFilter setProduct(ProductBean product) {
        this.product = product;
        return this;
    }

    @Override
    public boolean empty() {
        return super.empty() && payStatus == PayStatus.all && utmSources.isEmpty() && product == null;
    }

    public enum PayStatus {
        payed {
            @Override
            public List<OrderStatus> statuses() {
                return OrderStatus.payed();
            }
        }, notPayed {
            @Override
            public List<OrderStatus> statuses() {
                return OrderStatus.notPayed();
            }
        }, all {
            @Override
            public boolean noFilter() {
                return true;
            }
        };

        public List<OrderStatus> statuses() {
            return new ArrayList<>();
        }

        public boolean noFilter() {
            return false;
        }
    }
}
