/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.ui.balance;

import com.vaadin.flow.router.Route;
import com.xalap.payment.api.MyPaymentApi;
import com.xalap.payment.api.PaymentDto;
import com.xalap.vaadin.custom.frame.RootListFrame;
import com.xalap.vaadin.custom.grid.dataprovider.filter.GridSearchSqlDataProvider;
import com.xalap.vaadin.starter.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.uaa.api.audit.AuditEventDto.COLUMN_AUDIT_EVENT_DATE;
import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;

/**
 * Список платежей для пополнения баланса
 *
 * @author Usov Andrey
 * @since 2020-04-22
 */
@Route(value = MyReplenishBalancePaymentListFrame.VIEW_NAME, layout = MainLayout.class)
public class MyReplenishBalancePaymentListFrame extends RootListFrame<PaymentDto> {

    public static final String VIEW_NAME = "balancePayments";

    @Autowired
    public MyReplenishBalancePaymentListFrame(MyPaymentApi myPaymentController) {
        setDataProviderWithSearch(new GridSearchSqlDataProvider<>(
                myPaymentController.byCurrentCustomer(),
                myPaymentController.byCurrentCustomerWithFilter(),
                desc(COLUMN_AUDIT_EVENT_DATE)));
        gridPanel.createColumns();
    }

}

