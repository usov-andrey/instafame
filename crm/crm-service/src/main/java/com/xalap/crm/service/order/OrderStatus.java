/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
public enum OrderStatus {

    Created {
        @Override
        public boolean paid() {
            return false;
        }
    },
    Paid,
    Canceled {
        @Override
        public boolean paid() {
            return false;
        }
    },
    Confirmed,
    Running,
    Completed {
        @Override
        public boolean completed() {
            return true;
        }
    },
    CompletedAndReceipt {
        @Override
        public boolean completed() {
            return true;
        }
    };//Завершен и отправлен чек о выполнении

    public boolean paid() {
        return true;
    }

    public boolean completed() {
        return false;
    }

    public static List<OrderStatus> notPayed() {
        return statuses(orderStatus -> !orderStatus.paid());
    }

    public static List<OrderStatus> payed() {
        return statuses(OrderStatus::paid);
    }

    private static List<OrderStatus> statuses(Predicate<OrderStatus> predicate) {
        List<OrderStatus> result = new ArrayList<>();
        for (OrderStatus value : OrderStatus.values()) {
            if (predicate.test(value)) {
                result.add(value);
            }
        }
        return result;
    }

}
