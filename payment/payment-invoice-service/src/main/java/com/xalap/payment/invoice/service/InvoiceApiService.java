/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.invoice.service;

import com.xalap.framework.data.dto.DtoCrudService;
import com.xalap.payment.invoice.api.InvoiceApi;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.payment.invoice.api.InvoiceId;
import com.xalap.payment.invoice.api.InvoiceType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Реализация InvoiceApi
 *
 * @author Usov Andrey
 * @since 17.05.2021
 */
@Service
@AllArgsConstructor
public class InvoiceApiService extends DtoCrudService<InvoiceDto, Invoice, InvoiceService, Long> implements InvoiceApi {

    @Override
    public Optional<InvoiceDto> getInvoice(InvoiceId invoiceId) {
        return toDto(service.getInvoice(invoiceId));
    }

    @Override
    public InvoiceDto createAndSaveInvoice(String userLogin, BigDecimal amountValue, InvoiceType invoiceType,
                                           InvoiceId invoiceId) {
        return toDto(service.createAndSaveInvoice(userLogin, amountValue, invoiceType, invoiceId));
    }
}
