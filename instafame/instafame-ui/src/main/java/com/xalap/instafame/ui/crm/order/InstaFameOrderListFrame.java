/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.crm.order;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.order.grid.OrderFilter;
import com.xalap.crm.service.order.grid.OrderGridService;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.crm.ui.order.OrderFrame;
import com.xalap.crm.ui.order.OrderListFrame;
import com.xalap.framework.utils.DateHelper;
import com.xalap.instafame.service.InstaFameService;
import com.xalap.instafame.service.instaorder.CreateIOBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOFollowersSpeed;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.instafame.ui.component.ClipboardDialog;
import com.xalap.instafame.ui.instaorder.IOFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xalap.vaadin.custom.grid.renderer.ButtonRendererBuilderGenerator.button;
import static com.xalap.vaadin.custom.grid.renderer.IfRendererBuilder.ifBuilder;
import static com.xalap.vaadin.custom.grid.renderer.IfRendererBuilder.ifElseBuilder;

/**
 * @author Usov Andrey
 * @since 2020-05-06
 */
@Route(value = InstaFameOrderListFrame.VIEW_NAME, layout = MainLayout.class)
public class InstaFameOrderListFrame extends OrderListFrame {

    public static final String VIEW_NAME = "ifOrderList";

    private Map<OrderBean, IOBean> orderMap;

    @Autowired
    public InstaFameOrderListFrame(ServiceRef<OrderService> service, OrderGridService orderGridService,
                                   ServiceRef<IOService> ioService, ServiceRef<InstaFameService> instaFameService,
                                   ServiceRef<LeadService> leadService,
                                   ServiceRef<ContactDataService> contactDataService, ServiceRef<OrderLineService> orderLineService) {
        super(service, orderGridService);

        //----Buttons
        gridPanel.buttons().menu()
                .addMenu("Обновить затраты", () -> {
                    ioService.get().updateOrdersCost();
                    reloadData();
                }).addMenu("Создать заказ", () -> {
            FastCreateOrderDialog dialog = new FastCreateOrderDialog(instaFameService, leadService, contactDataService, orderLineService);
            dialog.setChangeHandler(this::reloadData);
            dialog.openDialog();
        }).addMenu("Отправить в Яндекс метрику", () -> {
            OrderService orderService = service.get();
            Date date = DateHelper.getDateTime("15.02.2022 00:00");
            List<OrderBean> orders = orderService.getOrdersAfter(date);
            orderService.sendToYandex(orders);
        });

        dataProvider.addPageResultListener(orderBeans -> {
            orderMap = new HashMap<>();
            if (!orderBeans.isEmpty()) {
                List<IOBean> instaOrders = ioService.get().getByOrders(getBeans(orderBeans));
                for (IOBean instaOrder : instaOrders) {
                    orderMap.put(instaOrder.getOrder(), instaOrder);
                }
            }
        });

        gridPanel.columnBuilder().actionsInsert(
                () -> ifElseBuilder(
                        bean -> orderMap.containsKey(bean.getOrderBean()),
                        button("К заказу", bean -> navigate(IOFrame.class, orderMap.get(bean.getOrderBean()).getId())),
                        button("Создать заказ", bean -> ioService.get().getOrCreateOrders(new CreateIOBean(bean.getOrderBean(), IOFollowersSpeed.ByDay100_250, "")))
                ),
                () -> ifBuilder(
                        bean -> filter.getPayStatus() == OrderFilter.PayStatus.notPayed,
                        button("Напоминание", bean -> ClipboardDialog.open(
                                instaFameService.get().getForgotOrderText(bean.getOrderBean())))
                )
        );

        setForm(new InstaFameOrderForm(service, instaFameService, ioService));

    }

    @Override
    protected Class<? extends OrderFrame> orderFrame() {
        return InstaFameOrderFrame.class;
    }
}
