/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.cost;

/**
 * Тип затрат
 *
 * @author Usov Andrey
 * @since 2020-08-26
 */
public enum CostType {

    others,
    yandexDirect {
        @Override
        public boolean isMarketing() {
            return true;
        }
    },
    googleAds {
        @Override
        public boolean isMarketing() {
            return true;
        }
    },
    saas;

    public boolean isMarketing() {
        return false;
    }
}
