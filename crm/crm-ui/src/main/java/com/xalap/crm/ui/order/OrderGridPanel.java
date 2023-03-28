/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.order.OrderStatus;
import com.xalap.crm.ui.contact.ContactMessageListFrame;
import com.xalap.crm.ui.lead.LeadFrame;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;
import com.xalap.vaadin.custom.grid.BeanWithIdGridPanel;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.valueprovider.DoubleValueProvider;
import com.xalap.vaadin.custom.route.RouterLink;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 21/06/2019
 */
public class OrderGridPanel extends BeanWithIdGridPanel<OrderBean, Integer> {

    public OrderGridPanel(ServiceRef<OrderService> service) {
        super(OrderBean.class);
        sortWithId();

        //---------Колонки
        GridColumns<OrderBean> columns = columns();
        columns.addLink("Создание", (ValueProvider<OrderBean, String>) orderBean -> DateHelper.getDateTime(orderBean.getCreateTime()),
                (ValueProvider<OrderBean, Integer>) orderBean -> orderBean.getId(), OrderFrame.class).setWidth("130px");
        columns.footer(
                columns.addComponent("Заказ", orderBean -> {
                    LeadBean lead = orderBean.getLead();
                    CustomVerticalLayout layout = CustomVerticalLayout.of(new Label("Платеж: " + orderBean.getOrderId()),
                            FlexHorizontalLayout.of(new Label("От:"), new RouterLink(lead.getName(), LeadFrame.class, lead.getId())));
                    if (lead.getInstagram() != null) {
                        layout.add(FlexHorizontalLayout.of(new Label("Instagram:"), new InstagramComponent(lead.getInstagram())));
                    }
                    return layout;
                }).setWidth("200px"),
                (beans) -> "Заказов: " + beans.size());

        columns.footer(columns.addColumn(OrderBean.REVENUE),
                value -> StringHelper.toString(CollectionHelper.sumDouble(value, OrderBean::getRevenue)));

        columns.footer(columns.addColumn("Налоги", new DoubleValueProvider<>(orderBean -> orderBean.getTax())).setWidth("70px"),
                (beanList) -> StringHelper.toString(CollectionHelper.sumDouble(beanList, OrderBean::getTax)));


        columns.footer(columns.addColumn(OrderBean.COST_PRICE),
                (beanList) -> StringHelper.toString(CollectionHelper.sumDouble(beanList, OrderBean::getCostPrice)));

        columns.footer(columns.addColumn("Прибыль", new DoubleValueProvider<>(OrderBean::profit)),
                (beanList) -> StringHelper.toString(CollectionHelper.sumDouble(beanList, OrderBean::profit)));

        columns.addColumn("Источник", (ValueProvider<OrderBean, String>) orderBean -> orderBean.getLead().utmCaption());
        columns.addColumn(OrderBean.STATUS).setWidth("70px");
        columns.actions(
                (actions, bean) -> {
                    if (bean.getStatus() == OrderStatus.Completed) {
                        actions.addWithReload("Отправить чек", () -> service.get().asyncCompleteAndSendReceipt(bean));
                    }

                    actions.add("В яндекс", () -> service.get().sendToYandex(bean));
                    actions.add("В Google", () -> service.get().sendToGoogle(bean));
                    actions.add("Написать", () -> navigate(ContactMessageListFrame.class, bean.getLead().getContact().getId()));
                }
        );
    }
}
