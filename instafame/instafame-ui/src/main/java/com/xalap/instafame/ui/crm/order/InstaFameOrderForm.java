/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.crm.order;

import com.vaadin.flow.component.UI;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.instafame.service.InstaFameService;
import com.xalap.instafame.service.instaorder.CreateIOBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOFollowersSpeed;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.instafame.ui.instaorder.IOFrame;
import com.xalap.vaadin.custom.component.entity.EntityForm;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.List;

/**
 * @author Usov Andrey
 * @since 2020-05-07
 */
public class InstaFameOrderForm extends EntityForm<OrderBean> {

    public InstaFameOrderForm(ServiceRef<OrderService> orderService, ServiceRef<InstaFameService> instaFameService,
                              ServiceRef<IOService> ioService) {
        super(OrderBean.class, orderService);
        formActions().setProvider((actions, bean) -> {
            actions
                    .add("В яндекс", () -> orderService.get().sendToYandex(bean))
                    .add("В Google", () -> orderService.get().sendToGoogle(bean))
                    .add("Email:ConfirmInstagram", () -> instaFameService.get().sendEmailAboutConfirmInstagram(bean.getLead()))
                    .add("Email:StartOrder", () -> instaFameService.get().sendEmailAboutStartOrder(bean.getLead()));
            List<IOBean> byOrder = ioService.get().getByOrder(bean);
            if (byOrder.isEmpty()) {
                actions.add("Создать заказ", () -> ioService.get().getOrCreateOrders(new CreateIOBean(bean, IOFollowersSpeed.ByDay100_250, "")));
            } else {
                for (IOBean ioBean : byOrder) {
                    actions.add("К заказу", () -> UI.getCurrent().navigate(IOFrame.class, ioBean.getId()));
                }
            }
        });
    }
}
