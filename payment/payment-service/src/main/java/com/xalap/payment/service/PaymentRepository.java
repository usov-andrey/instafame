/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.service;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий платежей
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Repository
public interface PaymentRepository extends PageableRepository<Payment, Long> {

    Page<Payment> findByInvoiceIdPspInvoiceIdContaining(Pageable pageable, String pspInvoiceId);

    long countByInvoiceIdPspInvoiceIdContaining(String pspInvoiceId);

    List<Payment> findByCustomer(Pageable pageable, String customer);

    long countByCustomer(String customer);
}
