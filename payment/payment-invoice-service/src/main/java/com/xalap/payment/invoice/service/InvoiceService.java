/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.invoice.service;

import com.xalap.framework.data.CrudService;
import com.xalap.payment.invoice.api.InvoiceId;
import com.xalap.payment.invoice.api.InvoiceType;
import com.xalap.uaa.api.account.AccountApi;
import com.xalap.uaa.api.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Сервис по работе со счетами
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Service
@AllArgsConstructor
public class InvoiceService extends CrudService<Invoice, InvoiceRepository, Long> {

    private final AccountApi accountApi;
/*
    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        //Поле created_by должно быть not null, заполняем
        for (Invoice invoice : getRepository().findAll()) {
            if (invoice.getCreatedBy() == null) {
                invoice.setCreatedBy("None");
                invoice.setCreatedDate(Instant.now());
                save(invoice);
            }
        }
    }*/

    Optional<Invoice> getInvoice(InvoiceId invoiceId) {
        return getRepository().findByInvoiceId(invoiceId);
    }

    Invoice createAndSaveInvoice(String userLogin, BigDecimal amountValue, InvoiceType invoiceType, InvoiceId invoiceId) {
        Invoice invoice = new Invoice();
        invoice.setAmount(amountValue);
        invoice.setInvoiceId(invoiceId);
        fillInvoiceType(invoiceType, invoice);
        fillCustomer(userLogin, invoice);
        return save(invoice);
    }

    private void fillInvoiceType(InvoiceType invoiceType, Invoice invoice) {
        invoice.setHandler(invoiceType.getHandler());
        invoice.setDescription(invoiceType.getDescription());
    }

    private void fillCustomer(String userLogin, Invoice invoice) {
        UserDto user = accountApi.getUser(userLogin);
        invoice.setCustomer(user.getLogin());
        invoice.setCustomerEmail(user.getEmail());
    }
}
