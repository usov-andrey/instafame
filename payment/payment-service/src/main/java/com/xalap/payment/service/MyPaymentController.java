/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.service;

import com.xalap.framework.data.dto.DtoMapper;
import com.xalap.framework.data.dto.InterfaceDtoMapper;
import com.xalap.framework.data.page.RepositoryTypeConverter;
import com.xalap.framework.domain.filter.SearchFilter;
import com.xalap.framework.domain.page.dataprovider.PageableDataProviderResolver;
import com.xalap.framework.domain.page.dataprovider.filter.FilterDataProviderResolver;
import com.xalap.framework.domain.page.request.PageRequest;
import com.xalap.payment.api.MyPaymentApi;
import com.xalap.payment.api.PaymentDto;
import com.xalap.uaa.api.account.AccountApi;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.xalap.framework.domain.page.dataprovider.PageableDataProvider.page;

/**
 * Внешнее api для списка моих платежей
 *
 * @author Usov Andrey
 * @since 2020-05-27
 */
@Controller
@RequestMapping(MyPaymentController.NAME)
public class MyPaymentController implements RepositoryTypeConverter, MyPaymentApi {

    public static final String NAME = "myPayments";

    private final PaymentRepository repository;
    private final AccountApi accountController;
    private final DtoMapper<PaymentDto, Payment> mapper = new InterfaceDtoMapper<>();

    public MyPaymentController(PaymentRepository repository, AccountApi accountController) {
        this.repository = repository;
        this.accountController = accountController;
    }

    private String currentCustomer() {
        return accountController.getAuthenticatedUserLogin();
    }

    private PaymentRepository getRepository() {
        return repository;
    }

    /**
     * @return Список моих платежей
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public
    @ResponseBody
    List<PaymentDto> get(PageRequest pageable) {
        return mapper.toDto(getRepository().findByCustomer(pageable(pageable), currentCustomer()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "count")
    public
    @ResponseBody
    long count() {
        return getRepository().countByCustomer(currentCustomer());
    }

    /**
     * @return Список платежей сделанных текущим пользователем с поиском и заданным фильтром
     */
    @Override
    public FilterDataProviderResolver<PaymentDto, SearchFilter> byCurrentCustomerWithFilter() {
        return filter -> page(
                this::get,
                this::count
        );
    }

    /**
     * @return Список платежей сделанных текущим пользователем с поиском
     */
    @Override
    public PageableDataProviderResolver<PaymentDto> byCurrentCustomer() {
        return () -> page(
                this::get,
                this::count
        );
    }

}
