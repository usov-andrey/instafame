/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.recovery;

import com.vaadin.flow.router.Route;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.instaorder.*;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.MemoryIOTaskProvider;
import com.xalap.instafame.ui.instaorder.IOFrame;
import com.xalap.instafame.ui.instaorder.IOGridBean;
import com.xalap.vaadin.custom.frame.RootListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.grid.valueprovider.DateTimeValueProvider;
import com.xalap.vaadin.custom.grid.valueprovider.DoubleValueProvider;
import com.xalap.vaadin.starter.ui.MainLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 06/08/2019
 */
@Route(value = IORecoveryListFrame.VIEW_NAME, layout = MainLayout.class)
public class IORecoveryListFrame extends RootListFrame<IOGridBean> {

    public static final String VIEW_NAME = "ioRecoveryList";

    public IORecoveryListFrame(IOService service, IOTaskService ioTaskService) {
        super(Class -> new GridPanel<>(IOGridBean.class));
        setMemoryDataProvider(() -> {
            List<IOGridBean> result = new ArrayList<>();
            List<IOBean> all = service.getNotCreated();
            List<IOBean> beanList = all.stream().filter(
                    orderBean ->
                            orderBean.getOrder() != null && (
                                    orderBean.getFollowersPackage() == IOFollowersPackage.premium ||
                                            orderBean.getFollowersPackage() == IOFollowersPackage.vip)
            )
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            MemoryIOTaskProvider taskProvider = ioTaskService.memoryTaskProvider(beanList);
            for (IOBean ioBean : beanList) {
                IOGridBean gridBean = new IOGridBean(ioBean);
                gridBean.setStats(new IOStats(ioBean, taskProvider.getTasks(ioBean)));
                result.add(gridBean);
            }
            return result;
        });

        //-----Вкладки
        addTab("Завершенные", bean -> bean.getInstaOrderBean().getStatus() == IOStatus.Completed).addTab("Все");

        //-----Filters
        gridPanel.filters()
                .addDate("C", (bean, date) -> bean.getInstaOrderBean().getCreateTime().after(date))
                .addDate("По", (bean, date) -> bean.getInstaOrderBean().getCreateTime().before(date));

        //-------------Columns
        GridColumns<IOGridBean> columns = gridPanel.columns();
        columns.addLink("Время",
                new DateTimeValueProvider<>(instaOrderGridBean -> instaOrderGridBean.getInstaOrderBean().getCreateTime()),
                instaOrderGridBean -> instaOrderGridBean.getInstaOrderBean().getId(), IOFrame.class).setWidth("100px");

        columns.footer(
                columns.addColumn("Расход на подписчиков", new DoubleValueProvider<>(bean -> bean.getStats().getFollowersCharge())),
                (beanList) -> StringHelper.toString(CollectionHelper.sumDouble(beanList, ioGridBean -> ioGridBean.getStats().getFollowersCharge())));
        columns.footer(
                columns.addColumn("Расход на восстановление", new DoubleValueProvider<>(bean ->
                        bean.getStats().getRecoveryFollowersCharge())),
                (beanList) -> StringHelper.toString(CollectionHelper.sumDouble(beanList,
                        bean -> bean.getStats().getRecoveryFollowersCharge())));

        columns.footer(
                columns.addColumn("Процент", new DoubleValueProvider<>(bean ->
                        bean.getStats().getRecoveryFollowersChargePercent())),
                (beanList) -> StringHelper.toString(
                        CollectionHelper.sumDouble(beanList,
                                bean -> bean.getStats().getRecoveryFollowersCharge()) * 100 /
                                CollectionHelper.sumDouble(beanList, ioGridBean -> ioGridBean.getStats().getFollowersCharge())));

        columns.footer(
                columns.addColumn("Прибыль", new DoubleValueProvider<>(IOGridBean::profit)),
                (beanList) -> StringHelper.toString(CollectionHelper.sumDouble(beanList, IOGridBean::profit)));

    }
}
