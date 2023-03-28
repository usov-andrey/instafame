/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.order.OrderStatus;
import com.xalap.crm.service.order.grid.OrderFilter;
import com.xalap.crm.service.order.grid.OrderGridBean;
import com.xalap.crm.service.order.grid.OrderGridService;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.crm.ui.contact.ContactMessageListFrame;
import com.xalap.crm.ui.lead.LeadFrame;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.component.LookupField;
import com.xalap.vaadin.custom.frame.RootBeanListFrame;
import com.xalap.vaadin.custom.grid.column.GridColumnBuilder;
import com.xalap.vaadin.custom.grid.dataprovider.filter.GridFilterSqlDataProvider;
import com.xalap.vaadin.custom.grid.renderer.UrlRendererBuilder;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static com.xalap.framework.domain.filter.PeriodSearchFilter.FROM;
import static com.xalap.framework.domain.filter.PeriodSearchFilter.TO;
import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;
import static com.xalap.vaadin.custom.grid.renderer.ButtonRendererBuilderGenerator.button;
import static com.xalap.vaadin.custom.grid.renderer.IfRendererBuilder.ifBuilder;
import static com.xalap.vaadin.custom.grid.renderer.LabelRendererBuilder.label;
import static com.xalap.vaadin.custom.grid.renderer.LabelRendererBuilder.labelWithFloat;
import static com.xalap.vaadin.custom.grid.renderer.RouterLinkRendererBuilder.routerLink;
import static com.xalap.vaadin.custom.grid.renderer.TextRendererBuilder.text;

/**
 * Список заказов
 *
 * @author Usov Andrey
 * @since 2020-04-28
 */
@Route(value = OrderListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Заказы")
public class OrderListFrame extends RootBeanListFrame<OrderBean, OrderGridBean> {

    public static final String VIEW_NAME = "orderList";

    protected GridFilterSqlDataProvider<OrderGridBean, OrderFilter> dataProvider;
    protected OrderFilter filter;

    @Autowired
    protected OrderListFrame(ServiceRef<OrderService> service, OrderGridService gridService) {
        super(orderGridBean -> gridService.fromGrid().apply(orderGridBean), service);

        filter = new OrderFilter();
        dataProvider = new GridFilterSqlDataProvider<>(
                gridService.noFilter(),
                gridService.byPeriodFilter(),
                desc(OrderBean.CREATE_TIME)
        );

        setPageableDataProvider(dataProvider, filter,
                (orderFilterBinderFieldsCreator -> {
                    addSearchFilter(orderFilterBinderFieldsCreator)
                            .addFields(FROM, TO);
                    LookupField<ProductBean, Integer> productField = orderFilterBinderFieldsCreator.addField(OrderFilter.PRODUCT);
                    productField.disableNavigation();
                }));

        //---------Колонки
        GridColumnBuilder<OrderGridBean> columns = gridPanel.columnBuilder();

        columns.add("Дата",
                routerLink(
                        orderBean -> DateHelper.getDateTime(orderBean.getOrderBean().getCreateTime()), orderFrame(),
                        orderGridBean -> orderGridBean.getOrderBean().getId()
                )
        ).setAutoWidth(false).setWidth("100px");

        Grid.Column<OrderGridBean> orderColumn = columns.add("Заказ",
                label("Платеж", orderGridBean -> orderGridBean.getOrderBean().getOrderId()),
                label("Заявка",
                        routerLink(
                                orderGridBean -> orderGridBean.getOrderBean().getLead().getName(),
                                LeadFrame.class,
                                orderGridBean -> orderGridBean.getOrderBean().getLead().getId())),
                label("Instagram",
                        ifBuilder(orderGridBean -> orderGridBean.getOrderBean().getLead().getInstagram() != null,
                                UrlRendererBuilder.url(
                                        orderGridBean -> InstagramComponent.accountUrl(orderGridBean.getOrderBean().getLead().getInstagram()),
                                        orderGridBean -> orderGridBean.getOrderBean().getLead().getInstagram()
                                )
                        )
                ),
                ifBuilder(orderGridBean -> StringHelper.isNotEmpty(orderGridBean.getOrderBean().getPromoCode()),
                        label("Скидка", orderGridBean -> orderGridBean.getOrderBean().getDiscountPercent()
                                + "% " + orderGridBean.getOrderBean().getPromoCode())
                )
        ).setAutoWidth(false).setWidth("220px");

        /*
         * Для каждого заказа нужно писать более подробную информацию о контакте:
         * сколько всего заплачено, сколько всего оплачено заказов, когда последняя оплата была.
         */
        columns.add("Оплачено",
                label("Кол-во", bean -> Integer.toString(bean.getClientPayOrderCount())),
                label("Доход", bean -> money(bean.getClientPayAmount())),
                label("Последний", bean -> bean.getClientLastOrderDate() != null ?
                        DateHelper.getDateTime(bean.getClientLastOrderDate()) : "")
        ).setAutoWidth(false).setWidth("170px");

        columns.add("Позиции заказа",
                text(orderGridBean -> {
                    List<OrderLineBean> orderLineBeans = orderGridBean.getOrderLineBeanList();
                    if (orderLineBeans == null) {
                        return "";
                    }
                    return orderLineBeans.stream().map(OrderLineBean::getLineText).collect(Collectors.toList());
                })
        ).setAutoWidth(false).setWidth("150px");

        Grid.Column<OrderGridBean> profitColumn = columns.add("Прибыль",
                labelWithFloat("Цена", bean -> money(bean.getOrderBean().getClientCost())),
                labelWithFloat("Доход", bean -> money(bean.getOrderBean().getRevenue())),
                labelWithFloat("Комиссия", bean -> money(bean.getOrderBean().getTax())),
                labelWithFloat("Себест.", bean -> money(bean.getOrderBean().getCostPrice())),
                labelWithFloat("Прибыль", bean -> money(bean.getOrderBean().profit()))
        ).setAutoWidth(false).setWidth("170px");

        columns.add("Источник", orderGridBean -> orderGridBean.getOrderBean().getLead().getUtmSource())
                .setAutoWidth(false).setWidth("100px");

        columns.add("Статус", orderGridBean -> orderGridBean.getOrderBean().getStatus().name())
                .setAutoWidth(false).setWidth("70px");

        columns.actions(
                () -> ifBuilder(
                        bean -> bean.getOrderBean().getStatus() == OrderStatus.Completed,
                        button("Отправить чек", bean -> service.get().asyncCompleteAndSendReceipt(bean.getOrderBean()))
                ),
                () -> button("Переписка",
                        bean -> navigate(ContactMessageListFrame.class, bean.getOrderBean().getLead().getContact().getId()))
        );

        //Footer
        dataProvider.addSizeResultListener(size -> {
            orderColumn.setFooter("Заказов: " + size);
            profitColumn.setFooter("Прибыль: " + money(gridService.getProfit(filter)));
        });

        //-----Вкладки
        addTab("Оплаченные", () -> {
            filter.setPayStatus(OrderFilter.PayStatus.payed);
            profitColumn.setVisible(true);
        }).addTab("Неоплаченные", () -> {
            filter.setPayStatus(OrderFilter.PayStatus.notPayed);
            profitColumn.setVisible(false);
        }).addTab("Все", () -> {
            filter.setPayStatus(OrderFilter.PayStatus.all);
            profitColumn.setVisible(true);
        });

        Anchor download = new Anchor(new StreamResource("orders.csv", () -> new OrdersExporter()
                .setOrders(getBeans(gridPanel.getItems())).export()), "");
        download.getElement().setAttribute("download", true);
        download.add(new Button(new Icon(VaadinIcon.DOWNLOAD_ALT)));
        gridPanel.buttons().add(download);
    }

    private String money(Double amount) {
        return amount != null ? StringHelper.toString(amount) : "0,00";
    }

    protected Class<? extends OrderFrame> orderFrame() {
        return OrderFrame.class;
    }

}
