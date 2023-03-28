/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.invoice.service;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.payment.invoice.api.InvoiceId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Repository
public interface InvoiceRepository extends PageableRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceId(InvoiceId invoiceId);

}
