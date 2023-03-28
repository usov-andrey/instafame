/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.provider.robokassa;

import com.xalap.framework.utils.RandomUtils;
import com.xalap.framework.utils.StringHelper;
import com.xalap.framework.utils.WebHelper;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.payment.invoice.api.InvoiceId;
import com.xalap.payment.provider.robokassa.properties.RobokassaProperties;
import com.xalap.payment.provider.robokassa.properties.RobokassaPropertiesProvider;
import com.xalap.payment.service.Payment;
import com.xalap.payment.service.provider.PaymentServiceProvider;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Робокасса
 *
 * @author Usov Andrey
 * @since 2020-04-16
 */
@Service
public class RobokassaPaymentServiceProvider extends PaymentServiceProvider {

    private final RobokassaPropertiesProvider propertiesProvider;

    public RobokassaPaymentServiceProvider(SpringTemplateEngine templateEngine,
                                           RobokassaPropertiesProvider propertiesProvider) {
        super(templateEngine, "robokassa");
        this.propertiesProvider = propertiesProvider;
    }

    @Override
    public String generateInvoiceId() {
        //InvId в Robokassa от 1 до Integer.MAX
        int invId = RandomUtils.nextInt(Integer.MAX_VALUE - 1) + 1;
        return Integer.toString(invId);
    }

    @Override
    public Payment createPayment(HttpServletRequest request) {
        RobokassaPayment robokassaPayment = new RobokassaPayment(request);
        InvoiceId invoiceId = invoiceId(robokassaPayment.getInvoiceId());
        Payment payment = new Payment();
        payment.setInvoiceId(invoiceId);
        payment.setCurrencyLabel(robokassaPayment.getCurrencyLabel());
        payment.setCustomerEmail(robokassaPayment.customerEmail());
        payment.setFee(new BigDecimal(robokassaPayment.fee()));
        payment.setCustomerPayAmount(new BigDecimal(robokassaPayment.customerPayAmount()));
        return payment;
    }

    @Override
    public String successPaymentResponse(HttpServletRequest request) {
        return "OK" + new RobokassaPayment(request).getInvoiceId();
    }

    private RobokassaProperties properties() {
        return propertiesProvider.getRobokassaProperties();
    }

    private String signature(String value) {
        if (!properties().getSignatureAlgorithm().equals("md5")) {
            throw new UnsupportedOperationException();
        }
        return StringHelper.md5(value, WebHelper.ENCODING);
    }

    @Override
    protected Context createContext(InvoiceDto invoice) {
        Context context = super.createContext(invoice);
        String signature = signature(properties().getMerchantLogin() + ":" + invoice.getAmount() + ":" +
                invoice.getId() + ":" + getPassword1());
        context.setVariable("signature", signature);
        context.setVariable("merchantLogin", properties().getMerchantLogin());
        return context;
    }

    private String getPassword1() {
        return properties().getPassword1();
    }


}
