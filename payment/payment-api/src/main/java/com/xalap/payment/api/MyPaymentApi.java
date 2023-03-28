/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.api;

import com.xalap.framework.domain.filter.SearchFilter;
import com.xalap.framework.domain.page.dataprovider.PageableDataProviderResolver;
import com.xalap.framework.domain.page.dataprovider.filter.FilterDataProviderResolver;

/**
 * Api для отображения списка моих платежей
 *
 * @author Usov Andrey
 * @since 17.05.2021
 */
public interface MyPaymentApi {
    FilterDataProviderResolver<PaymentDto, SearchFilter> byCurrentCustomerWithFilter();

    PageableDataProviderResolver<PaymentDto> byCurrentCustomer();
}
