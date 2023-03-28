/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.Counter;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.IOStatus;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.provider.MemoryIOTaskProvider;
import com.xalap.instafame.ui.instaorder.tasklist.IOTaskForManualListFrame;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import com.xalap.instagram.ui.user.InstagramUserFrame;
import com.xalap.vaadin.custom.frame.RootBeanListFrame;
import com.xalap.vaadin.custom.grid.GridButtons;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.valueprovider.DateTimeValueProvider;
import com.xalap.vaadin.custom.route.RouterLink;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.xalap.uaa.api.Role.ROLE_ADMIN;

/**
 * @author Усов Андрей
 * @since 11/06/2019
 */
@Route(value = IOListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("ИнстаЗаказы")
@Secured(ROLE_ADMIN)
public class IOListFrame extends RootBeanListFrame<IOBean, IOGridBean> {

    public static final String VIEW_NAME = "instaOrderList";

    @Autowired
    public IOListFrame(ServiceRef<IOService> service, ServiceRef<InstagramUserService> userService, ServiceRef<IOTaskService> ioTaskService) {
        super(IOGridBean::getInstaOrderBean, service);
        setMemoryDataProvider(() -> {
            List<IOBean> all = service.get().getAll();
            return all.stream().map(IOGridBean::new).collect(Collectors.toList());
        });

        setForm(new IOForm(service));

        setGridDataListener(value -> {
            Counter.CounterMap<String> sameInstagramCounter = new Counter.CounterMap<>();
            Map<IOBean, IOGridBean> beanMap = new HashMap<>();
            for (IOGridBean instaOrderGridBean : value) {
                beanMap.put(instaOrderGridBean.getInstaOrderBean(), instaOrderGridBean);
                //Если несколько заказов на один инстаграм, то нужно будет подсвечивать
                sameInstagramCounter.inc(instaOrderGridBean.getInstaOrderBean().getInstagram());
            }
            for (IOGridBean instaOrderGridBean : value) {
                instaOrderGridBean.setSameInstagramOrderCount(
                        sameInstagramCounter.getMap().get(instaOrderGridBean.getInstaOrderBean().getInstagram()).getCount());
            }


            TaskCounter createdCount = new TaskCounter();
            int inProgressCount = 0;

            MemoryIOTaskProvider taskProvider = ioTaskService.get().memoryTaskProvider(beanMap.keySet());
            int manualCheckCount = taskProvider.getTasks(InstaOrderTaskStatus.ManualCheck).size();

            for (Map.Entry<IOBean, IOGridBean> entry : beanMap.entrySet()) {
                IOBean instaOrderBean = entry.getKey();
                IOStats instaOrderStats = new IOStats(instaOrderBean, taskProvider.getTasks(instaOrderBean));
                beanMap.get(instaOrderBean).setStats(instaOrderStats);

                inProgressCount += instaOrderStats.getInProgressCount();
                //Считаем кнопку Запустить
                List<IOTaskBean> tasksForRun = ioTaskService.get().getTasksForRun(instaOrderBean, taskProvider);
                createdCount.add(tasksForRun.size(), CollectionHelper.countDouble(tasksForRun, IOTaskBean::getCharge));
            }
            //Изменяем меню в гриде
            GridButtons buttons = gridPanel.buttons().clear();
            addCreateButton();

            buttons.add("Обновить прогресс:" + inProgressCount, withReloadData(service.get()::updateAllTasksProgress))
                    .add("Автозадания", withReloadData(service.get()::addAutoTasks))
                    .add("Запустить:" + createdCount.caption(), withReloadData(service.get()::runAllOrders))
                    .add("Ручная проверка:" + manualCheckCount,
                            () -> getUI().ifPresent(ui -> ui.navigate(IOTaskForManualListFrame.class))
                    );

        });

        gridPanel.filters().addText("Поиск", (bean, s) -> StringHelper.isEmpty(s) || (bean.getUser() != null && bean.getUser().getUserName().contains(s)));
        //-------------Tabs
        addTab("Оплаченные в работе",
                gridBean -> gridBean.getInstaOrderBean().getStatus() != IOStatus.Completed
                        && gridBean.getInstaOrderBean().getStatus() != IOStatus.Created)
                .addTab("Все");
        //-------------Columns
        GridColumns<IOGridBean> columns = gridPanel.columns();
        columns.addLink("Время создания",
                new DateTimeValueProvider<>(instaOrderGridBean -> instaOrderGridBean.getInstaOrderBean().getCreateTime()),
                instaOrderGridBean -> instaOrderGridBean.getInstaOrderBean().getId(), IOFrame.class).setWidth("100px");


        columns.addColumn("Статус", gridBean -> {
            IOBean bean = gridBean.getInstaOrderBean();
            //Если отрицательная прибыль, то сообщаем об этом
            OrderBean order = bean.getOrder();
            if (order != null && order.getRevenue() != null) {
                double revenue = order.getRevenue() - gridBean.getStats().getCharge();
                if (revenue <= 0) {
                    return "Revenue:" + StringHelper.toString(revenue);
                }
            }
            //Если вышла публикация, а в заказе еще нужно лайки или комментарии проставлять, то сообщаем об этом
            if (bean.getUser() != null && gridBean.getStats().needMoreLikesOrComments(bean)
                    && bean.getUser().getLastMediaCreateTime() != null &&
                    (gridBean.getStats().getLastMediaTaskCreateTime() == null ||
                            bean.getUser().getLastMediaCreateTime().after(gridBean.getStats().getLastMediaTaskCreateTime()))) {
                return "New Media";
            }

            return bean.statusCaption();
        });
        columns.addColumn("Пакет", gridBean -> {
            IOBean orderBean = gridBean.getInstaOrderBean();
            String orderPackage = orderBean.getFollowersPackage().letter();

            return orderPackage + " " + gridBean.getStats().getRealFollowers().caption();
        });

        columns.addComponent("Подписчики", bean -> {
            Label statComponent = createStatComponent(bean, bean.getStats().getFollowers(), 100);
            if (bean.getStats().isDelayWithFollowers()) {
                UIUtils.setTextColor(TextColor.TERTIARY, statComponent);
            }
            return statComponent;
        });
        columns.addComponent("Лайки", bean -> createStatComponent(bean, bean.getStats().getLikes(), 100));
        columns.addComponent("Комментарии", bean -> createStatComponent(bean, bean.getStats().getComments(), 80));
        columns.addComponent("Пользователь", ioGridBean -> {
            InstagramUserBean user = ioGridBean.getUser();
            Component userComponent = user != null ?
                    new RouterLink(user.getUserName(), InstagramUserFrame.class, user.getId()) :
                    new Label(ioGridBean.getInstaOrderBean().getInstagram());
            //Если несколько заказов на один инстаграм, то подсвечиваем
            if (ioGridBean.getSameInstagramOrderCount() > 1) {
                UIUtils.setTextColor(TextColor.ERROR, userComponent);
            }
            return userComponent;

        });
    }

    private Label createStatComponent(IOGridBean bean, IOStats.Stat stat, int max) {
        Label label = new Label(stat.caption());
        if (stat.getMax() > 0 && stat.plannedPercent() < max && bean.getInstaOrderBean().progressMoreStat(stat)) {
            UIUtils.setTextColor(TextColor.ERROR, label);
        }
        return label;
    }

    private class TaskCounter {

        private int count = 0;
        private double charge = 0d;

        public void add(int count, double charge) {
            this.count += count;
            this.charge += charge;
        }

        public int getCount() {
            return count;
        }

        public double getCharge() {
            return charge;
        }

        public String caption() {
            return count + (count > 0 ? " цена=" + StringHelper.toString(charge) : "");
        }
    }
}
