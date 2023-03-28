/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.ui.payment;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.payment.api.PaymentApi;
import com.xalap.payment.api.PaymentDto;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.data.audit.AbstractAuditingEntity.COLUMN_CREATED_DATE;
import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;

/**
 * Список платежей
 *
 * @author Usov Andrey
 * @since 2020-04-22
 */
@Route(value = PaymentListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Платежи")
public class PaymentListFrame extends RootEntityListFrame<PaymentDto> {

    public static final String VIEW_NAME = "paymentList";

    @Autowired
    public PaymentListFrame(ServiceRef<PaymentApi> service) {
        super(service,
                desc(COLUMN_CREATED_DATE));

        gridPanel.createColumns();

        addCreateButton();
    }

}
