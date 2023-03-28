/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.ui.invoice;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.xalap.payment.invoice.api.InvoiceApi;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.payment.invoice.api.InvoiceId;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.data.audit.AbstractAuditingEntity.COLUMN_CREATED_DATE;

/**
 * Список счетов
 *
 * @author Usov Andrey
 * @since 2020-04-22
 */
@Route(value = InvoiceListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Счета")
public class InvoiceListFrame extends RootEntityListFrame<InvoiceDto> {

    public static final String VIEW_NAME = "invoices";

    @Autowired
    public InvoiceListFrame(ServiceRef<InvoiceApi> service) {
        super(service, GridDefaultSorting.desc(COLUMN_CREATED_DATE));
        GridColumns<InvoiceDto> columns = gridPanel.columns();
        columns.addComponent("Номер", invoice -> {
            InvoiceId invoiceId = invoice.getInvoiceId();
            if (invoiceId == null) {
                return new Label("Не задан");
            }
            return new RouterLink(invoiceId.name(), InvoiceFrame.class, invoice.getId());
        });
        columns
                .addDecimal("Сумма", InvoiceDto::getAmount);
        columns
                .add("Описание", InvoiceDto::getDescription)
                .add("Клиент", InvoiceDto::getCustomer)
                .add("Email", InvoiceDto::getCustomerEmail);

        addCreateButton();
    }
}
