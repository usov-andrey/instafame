/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.cost.CostBean;
import com.xalap.crm.service.cost.CostController;
import com.xalap.crm.service.cost.CostService;
import com.xalap.crm.service.cost.yandex.YandexDirectImporter;
import com.xalap.framework.domain.filter.PeriodFilter;
import com.xalap.framework.domain.filter.PeriodSearchFilter;
import com.xalap.vaadin.custom.dialog.FileUploadDialog;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.column.GridColumnBuilder;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.custom.grid.dataprovider.filter.GridFilterSqlDataProvider;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;

/**
 * Список затрат
 *
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Route(value = CostListFrame.VIEW_NAME, layout = MainLayout.class)
public class CostListFrame extends RootEntityListFrame<CostBean> {
    public static final String VIEW_NAME = "costList";

    @Autowired
    public CostListFrame(ServiceRef<CostService> service, CostController costController) {
        super(service, GridDefaultSorting.asc(CostBean.COST_TIME));

        var filter = new PeriodFilter();

        GridFilterSqlDataProvider<CostBean, PeriodFilter> dataProvider = new GridFilterSqlDataProvider<>(
                costController.dataProviderResolver(),
                costController.dataProviderResolverWithFilter(),
                desc(CostBean.COST_TIME));

        setPageableDataProvider(
                dataProvider,
                filter,
                (fieldsCreator -> fieldsCreator
                        .addFields(PeriodSearchFilter.FROM, PeriodSearchFilter.TO)));


        GridColumnBuilder<CostBean> columns = gridPanel.columnBuilder();
        columns.add(CostBean.COST_TIME);
        Grid.Column<CostBean> costBeanColumn = columns.add(CostBean.COST_F);
        columns.add(CostBean.COST_TYPE);

        dataProvider.addSizeResultListener(size -> costBeanColumn.setFooter("Всего: " + costController.getCostSum(filter)));

        addCreateButton();
        gridPanel.buttons().addWithReload("Импорт Yandex Direct", () -> FileUploadDialog.open(buffer -> {
            var importer = new YandexDirectImporter();
            List<CostBean> costBeans = importer.importFromCsv(buffer.getInputStream());
            service.get().importCost(costBeans);
        }));
    }

}