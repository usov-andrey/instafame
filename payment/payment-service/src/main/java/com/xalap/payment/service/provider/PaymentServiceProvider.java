/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.service.provider;

import com.xalap.payment.api.PaymentHtml;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.payment.invoice.api.InvoiceId;
import com.xalap.payment.service.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.http.HttpServletRequest;

/**
 * Поставщик платежных услуг
 *
 * @author Usov Andrey
 * @since 2020-04-16
 */
public abstract class PaymentServiceProvider {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceProvider.class);

    private final SpringTemplateEngine templateEngine;
    private final String templateName;

    public PaymentServiceProvider(SpringTemplateEngine templateEngine, String templateName) {
        this.templateEngine = templateEngine;
        this.templateName = templateName;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public PaymentHtml getPaymentHtml(InvoiceDto invoice) {
        Context context = createContext(invoice);
        PaymentHtml paymentHtml = new PaymentHtml(templateEngine.process(templateName, context));
        log.debug("For invoice {} created form html: {}", invoice, paymentHtml);
        return paymentHtml;
    }

    protected Context createContext(InvoiceDto invoice) {
        Context context = new Context();
        context.setVariable("invoiceId", invoice.getId());
        context.setVariable("amount", invoice.getAmount());
        context.setVariable("description", invoice.getDescription());
        context.setVariable("email", invoice.getCustomerEmail());
        return context;
    }

    public InvoiceId invoiceId(String pspInvoiceId) {
        InvoiceId invoiceId = new InvoiceId();
        invoiceId.setPaymentServiceProvider(getName());
        invoiceId.setPspInvoiceId(pspInvoiceId);
        return invoiceId;
    }

    public InvoiceId newInvoiceId() {
        return invoiceId(generateInvoiceId());
    }

    abstract protected String generateInvoiceId();

    public abstract Payment createPayment(HttpServletRequest request);

    public abstract String successPaymentResponse(HttpServletRequest request);
}
