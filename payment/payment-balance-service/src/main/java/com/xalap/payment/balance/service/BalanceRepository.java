/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.balance.service;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Repository
public interface BalanceRepository extends PageableRepository<Balance, Long> {

    Optional<Balance> findByCustomer(String customer);

}
