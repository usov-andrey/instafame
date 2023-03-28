/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.pipeline.PipelineBean;
import com.xalap.crm.service.pipeline.PipelineService;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageService;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.crm.service.product.ProductService;
import com.xalap.crm.ui.contact.ContactChoosingDialog;
import com.xalap.crm.ui.contact.ContactFrame;
import com.xalap.crm.ui.contact.ContactListFrame;
import com.xalap.crm.ui.finance.CostListFrame;
import com.xalap.crm.ui.finance.ProfitListFrame;
import com.xalap.crm.ui.finance.product.ProductCostListFrame;
import com.xalap.crm.ui.lead.LeadFrame;
import com.xalap.crm.ui.lead.LeadListFrame;
import com.xalap.crm.ui.lead.LeadStageHistoryListFrame;
import com.xalap.crm.ui.logs.GetLogsFrame;
import com.xalap.crm.ui.messaging.newchat.MessageQueueListFrame;
import com.xalap.crm.ui.messaging.newchat.MessageThreadListFrame;
import com.xalap.crm.ui.messaging.template.MessageTemplateListFrame;
import com.xalap.crm.ui.order.OrderChoosingListDialog;
import com.xalap.crm.ui.order.OrderFrame;
import com.xalap.crm.ui.order.OrderListFrame;
import com.xalap.crm.ui.order.product.ProductFrame;
import com.xalap.crm.ui.order.product.ProductListFrame;
import com.xalap.crm.ui.pipeline.PipelineFrame;
import com.xalap.crm.ui.pipeline.PipelineListFrame;
import com.xalap.crm.ui.pipeline.stage.PipelineStageFrame;
import com.xalap.crm.ui.pipeline.stage.PipelineStageListFrame;
import com.xalap.crm.ui.quiz.QuizListFrame;
import com.xalap.crm.ui.scheduler.SchedulerTaskListFrame;
import com.xalap.vaadin.custom.dialog.EntityChoosingListDialog;
import com.xalap.vaadin.custom.field.FieldRegistry;
import com.xalap.vaadin.starter.custom.MenuCreator;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviItem;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviMenu;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;
import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;

/**
 * @author Усов Андрей
 * @since 21/06/2019
 */
@Service
public class CrmUI {

    private final FieldRegistry fieldRegistry;
    private final OrderService orderService;
    private final LeadService leadService;
    private final ContactService contactService;
    private final ContactDataService contactDataService;
    private final PipelineStageService pipelineStageService;
    private final PipelineService pipelineService;
    private final ProductService productService;
    private final MenuCreator menuCreator;

    public CrmUI(FieldRegistry fieldRegistry, OrderService orderService, LeadService leadService, ContactService contactService, ContactDataService contactDataService, PipelineStageService pipelineStageService, PipelineService pipelineService, ProductService productService, MenuCreator menu) {
        this.fieldRegistry = fieldRegistry;
        this.orderService = orderService;
        this.leadService = leadService;
        this.contactService = contactService;
        this.contactDataService = contactDataService;
        this.pipelineStageService = pipelineStageService;
        this.pipelineService = pipelineService;
        this.productService = productService;
        this.menuCreator = menu;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        fieldRegistry.addLookup(OrderFrame.class, () -> new OrderChoosingListDialog(orderService));
        fieldRegistry.addLookup(LeadFrame.class, () -> new EntityChoosingListDialog<>(
                () -> leadService, LeadBean.class, desc(LeadBean.CREATE_TIME)));
        fieldRegistry.addLookup(ContactFrame.class, () -> new ContactChoosingDialog(() -> contactService, () -> contactDataService));
        fieldRegistry.addLookup(PipelineStageFrame.class, () -> new EntityChoosingListDialog<>(
                () -> pipelineStageService, PipelineStageBean.class, desc(COLUMN_ID)));
        fieldRegistry.addLookup(PipelineFrame.class, () -> new EntityChoosingListDialog<>(
                () -> pipelineService, PipelineBean.class, desc(COLUMN_ID)));
        fieldRegistry.addLookup(ProductFrame.class, () -> new EntityChoosingListDialog<>(
                () -> productService, ProductBean.class, desc(ProductBean.NAME_FIELD_NAME))
        );
    }

    public void addMenu() {
        menuCreator.add(menu());
    }

    private Consumer<NaviMenu> menu() {
        return menu -> {

            menu.addNaviItem(VaadinIcon.USERS, "Контакты", ContactListFrame.class);
            menu.addNaviItem(VaadinIcon.QUESTION, "Опросы", QuizListFrame.class);

            NaviItem sales = menu.addNaviItem(VaadinIcon.CART, "Продажи",
                    null);
            menu.addNaviItem(sales, "Заказы", OrderListFrame.class);
            menu.addNaviItem(sales, "Заявки", LeadListFrame.class);
            menu.addNaviItem(sales, "Продукты", ProductListFrame.class);

            NaviItem finance = menu.addNaviItem(VaadinIcon.CASH, "Финансы",
                    null);
            menu.addNaviItem(finance, "Отчет по месяцам", ProfitListFrame.class);
            menu.addNaviItem(finance, "Отчет по продуктам", ProductCostListFrame.class);
            menu.addNaviItem(finance, "Затраты", CostListFrame.class);


            NaviItem conversation = menu.addNaviItem(VaadinIcon.CHAT, "Общение",
                    null);
            menu.addNaviItem(conversation, "Сообщения", MessageThreadListFrame.class);
            menu.addNaviItem(conversation, "Шаблоны сообщений", MessageTemplateListFrame.class);
            menu.addNaviItem(conversation, "Очередь сообщений", MessageQueueListFrame.class);

            NaviItem funnel = menu.addNaviItem(VaadinIcon.FUNNEL, "Воронка продаж",
                    null);
            menu.addNaviItem(funnel, "Список воронок", PipelineListFrame.class);
            menu.addNaviItem(funnel, "Этапы воронок", PipelineStageListFrame.class);
            menu.addNaviItem(funnel, "Переходы заявок", LeadStageHistoryListFrame.class);

            NaviItem admin = menu.addNaviItem(VaadinIcon.AUTOMATION, "Администрирование",
                    null);
            menu.addNaviItem(admin, "Фоновые задания", SchedulerTaskListFrame.class);
            menu.addNaviItem(admin, "Системный журнал", GetLogsFrame.class);
        };
    }


}
