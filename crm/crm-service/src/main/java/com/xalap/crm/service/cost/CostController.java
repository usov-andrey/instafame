/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.cost;

import com.xalap.framework.data.page.RepositoryTypeConverter;
import com.xalap.framework.domain.filter.PeriodFilter;
import com.xalap.framework.domain.page.dataprovider.PageableDataProviderResolver;
import com.xalap.framework.domain.page.dataprovider.filter.FilterDataProviderResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

import static com.xalap.framework.domain.page.dataprovider.PageableDataProvider.all;
import static com.xalap.framework.domain.page.dataprovider.PageableDataProvider.page;

/**
 * Внешнее api затрат
 *
 * @author Usov Andrey
 * @since 2020-08-26
 */
@Controller
@RequestMapping(CostController.NAME)
public class CostController implements RepositoryTypeConverter {

    public static final String NAME = "costs";

    private final CostRepository repository;
    private final CostService service;

    public CostController(CostRepository repository, CostService service) {
        this.repository = repository;
        this.service = service;
    }

    /**
     * @return Список платежей сделанных текущим пользователем с поиском
     */
    public FilterDataProviderResolver<CostBean, PeriodFilter> dataProviderResolverWithFilter() {
        return filter -> page(
                pageable -> repository.find(pageable(pageable),
                        empty(filter.getFrom()), filter.getFrom(),
                        empty(filter.getTo()), filter.getTo()
                ),
                () -> repository.count(
                        empty(filter.getFrom()), filter.getFrom(),
                        empty(filter.getTo()), filter.getTo()
                )
        );
    }

    /**
     * @return Список затрат сделанных текущим пользователем с поиском
     */
    public PageableDataProviderResolver<CostBean> dataProviderResolver() {
        return () -> all(service);
    }

    public BigDecimal getCostSum(PeriodFilter filter) {
        return repository.getCostSum(
                empty(filter.getFrom()), filter.getFrom(),
                empty(filter.getTo()), filter.getTo()
        );
    }

}
