/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.ui.invoice;

import com.vaadin.flow.router.Route;
import com.xalap.payment.invoice.api.InvoiceApi;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Экран счета
 *
 * @author Usov Andrey
 * @since 2020-04-22
 */
@Route(value = InvoiceListFrame.VIEW_NAME, layout = MainLayout.class)
public class InvoiceFrame extends RootEntityFrame<InvoiceDto, Long> {

    @Autowired
    public InvoiceFrame(ServiceRef<InvoiceApi> service) {
        super(service, InvoiceListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
        );
    }

    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
