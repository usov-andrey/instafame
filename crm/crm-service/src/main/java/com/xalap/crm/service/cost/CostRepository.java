/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.cost;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Затраты
 *
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Repository
public interface CostRepository extends PageableRepository<CostBean, Integer> {

    String QUERY = "select c from CostBean c";
    //Убираем fetch
    String COUNT_QUERY = "select count(c) from CostBean c";

    String PROFIT_QUERY = "select sum(coalesce(c.cost, 0)) from CostBean c";
    String FILTER = " where" +
            " (true = :b2 or c.costTime > :from)" +
            " and (true = :b3 or c.costTime < :to)";

    @Query(QUERY + FILTER)
    List<CostBean> find(Pageable pageable, boolean b2, LocalDate from, boolean b3, LocalDate to);

    @Query(COUNT_QUERY + FILTER)
    long count(boolean b2, LocalDate from, boolean b3, LocalDate to);

    @Query(PROFIT_QUERY + FILTER)
    BigDecimal getCostSum(boolean b2, LocalDate from, boolean b3, LocalDate to);

    Optional<CostBean> findByCostTypeAndCostTime(CostType costType, LocalDate costTime);
}
