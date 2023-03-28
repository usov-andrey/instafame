/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.order.grid;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderRepository;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.order.OrderStatus;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.framework.data.page.RepositoryTypeConverter;
import com.xalap.framework.domain.page.dataprovider.PageableDataProvider;
import com.xalap.framework.domain.page.dataprovider.PageableDataProviderResolver;
import com.xalap.framework.domain.page.dataprovider.filter.FilterDataProviderResolver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.xalap.framework.domain.page.dataprovider.PageableDataProvider.page;

/**
 * @author Usov Andrey
 * @since 2020-04-30
 */
@Service
public class OrderGridService implements RepositoryTypeConverter {

    private final OrderRepository repository;
    private final OrderService orderService;
    private final OrderLineService orderLineService;

    public OrderGridService(OrderRepository repository, OrderService orderService, OrderLineService orderLineService) {
        this.repository = repository;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
    }

    public Function<OrderGridBean, OrderBean> fromGrid() {
        return OrderGridBean::getOrderBean;
    }

    public PageableDataProviderResolver<OrderGridBean> noFilter() {
        return () -> PageableDataProvider.delegate(OrderGridBean::new, PageableDataProvider.all(orderService));
    }

    public FilterDataProviderResolver<OrderGridBean, OrderFilter> byPeriodFilter() {
        /*

        Реализуем такую логику
        return bean.getLead().getInstagram() != null && bean.getLead().getInstagram().contains(s) ||
                bean.getOrderId().contains(s) || bean.getLead().getName().contains(s) ||
                bean.getLead().getContact().getName().contains(s);

                .addDate("C", (orderBean, date) -> orderBean.getCreateTime().after(date))
                .addDate("По", (orderBean, date) -> orderBean.getCreateTime().before(date));

            Оплаченные:
            bean.getStatus() != OrderStatus.Created && bean.getStatus() != OrderStatus.Canceled
         */
        return filter -> page(pageable -> {
                    List<OrderGridBean> beans = new ArrayList<>();
                    List<OrderBean> orderBeans = OrderGridService.this.repository.find(pageable(pageable),
                            OrderGridService.this.empty(filter.getSearch()), filter.getSearch(),
                            OrderGridService.this.empty(filter.getFrom()), OrderGridService.this.startDate(filter.getFrom()),
                            OrderGridService.this.empty(filter.getTo()), OrderGridService.this.endDate(filter.getTo()),
                            filter.getPayStatus().noFilter(), filter.getPayStatus().statuses(),
                            OrderGridService.this.empty(filter.getProduct()), filter.getProduct());
                    if (!orderBeans.isEmpty()) {
                        //Вычисляем OrderLineBeanList
                        Map<OrderBean, List<OrderLineBean>> orderLineMap = orderLineService.getOrderLineMap(orderBeans);
                        //Вычисляем List<OrderBean>
                        Map<ContactBean, List<OrderBean>> ordersByContact = orderService.getOrders(orderBeans);
                        //Заполняем
                        for (OrderBean orderBean : orderBeans) {
                            OrderGridBean gridBean = new OrderGridBean(orderBean);
                            gridBean.setOrderLineBeanList(orderLineMap.get(orderBean));
                            List<OrderBean> contactOrders = ordersByContact.get(orderBean.getLead().getContact());
                            gridBean.calcNumbersByOrders(contactOrders);
                            beans.add(gridBean);
                        }
                    }
                    return beans;
                },
                () -> get(repository::count, filter)
        );
    }

    public Double getProfit(OrderFilter filter) {
        return get(repository::getProfit, filter);
    }

    private <R> R get(OrderGridInterface<R> gridInterface, OrderFilter filter) {
        return gridInterface.get(empty(filter.getSearch()), filter.getSearch(),
                empty(filter.getFrom()), startDate(filter.getFrom()),
                empty(filter.getTo()), endDate(filter.getTo()),
                filter.getPayStatus().noFilter(),
                filter.getPayStatus().statuses(),
                empty(filter.getProduct()), filter.getProduct());
    }

    @FunctionalInterface
    private interface OrderGridInterface<R> {
        R get(boolean b1, String search, boolean b2, Date from, boolean b3, Date to,
              boolean b4, List<OrderStatus> statuses, boolean b5, ProductBean product);
    }
}
