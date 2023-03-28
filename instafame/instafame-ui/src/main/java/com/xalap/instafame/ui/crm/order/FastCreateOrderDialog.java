/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.crm.order;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactWithDataBean;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.lead.LeadWithContactData;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.instafame.service.InstaFameService;
import com.xalap.vaadin.custom.crud.BeanFormDialog;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Collections;

/**
 * Быстрое создание заказа
 *
 * @author Usov Andrey
 * @since 2020-04-20
 */
public class FastCreateOrderDialog extends BeanFormDialog<FastCreateOrderBean> {

    private final ServiceRef<InstaFameService> instaFameService;
    private final ServiceRef<LeadService> leadService;
    private final ServiceRef<ContactDataService> contactDataService;
    private final ServiceRef<OrderLineService> orderLineService;

    public FastCreateOrderDialog(ServiceRef<InstaFameService> instaFameService, ServiceRef<LeadService> leadService,
                                 ServiceRef<ContactDataService> contactDataService, ServiceRef<OrderLineService> orderLineService) {
        super(FastCreateOrderBean.class);
        setBean(new FastCreateOrderBean());
        this.instaFameService = instaFameService;
        this.leadService = leadService;
        this.contactDataService = contactDataService;
        this.orderLineService = orderLineService;
    }

    @Override
    protected void save(FastCreateOrderBean bean) {
        OrderBean order = instaFameService.get().createOrder(bean.getInvId(), bean.getFollowersSpeed(), bean.getCustomerPay(),
                null, null,
                pipelineStageBean -> {
                    ContactBean contactBean = bean.getContactBean();
                    LeadBean leadBean = new LeadBean();
                    leadBean.setName(contactBean.getName());
                    leadBean.setEmail(bean.getEmail());
                    leadBean.setInstagram(bean.getInstagram());
                    leadBean.setSource("Заказ " + bean.getInvId());
                    leadBean.setStage(pipelineStageBean);
                    leadBean.setContact(contactBean);
                    leadBean = leadService.get().save(leadBean);
                    ContactWithDataBean contactWithData = contactDataService.get().getContactWithData(contactBean);
                    return new LeadWithContactData(leadBean, contactWithData);
                }, orderBean ->
                        Collections.singletonList(orderLineService.get().getOrCreateOrderLine(orderBean,
                                bean.getOrderText(), 1, bean.getCustomerPay())), "");
        instaFameService.get().payOrder(order, bean.getCustomerPay() - bean.getFee(), bean.getEmail());
    }
}
