/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.user;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOFollowersPackage;
import com.xalap.instafame.service.instaorder.my.MyIoFilter;
import com.xalap.instafame.service.instaorder.my.MyIoGridBean;
import com.xalap.instafame.service.instaorder.my.MyIoGridService;
import com.xalap.uaa.api.account.AccountApi;
import com.xalap.vaadin.custom.frame.RootListFrame;
import com.xalap.vaadin.custom.grid.column.GridColumnBuilder;
import com.xalap.vaadin.custom.grid.dataprovider.filter.GridFilterSqlDataProvider;
import com.xalap.vaadin.custom.grid.renderer.UrlRendererBuilder;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.util.TextColor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;
import static com.xalap.vaadin.custom.grid.renderer.IconRendererBuilder.icon;
import static com.xalap.vaadin.custom.grid.renderer.IfRendererBuilder.ifBuilder;
import static com.xalap.vaadin.custom.grid.renderer.IfRendererBuilder.ifElseBuilder;
import static com.xalap.vaadin.custom.grid.renderer.LabelRendererBuilder.label;
import static com.xalap.vaadin.custom.grid.renderer.TextRendererBuilder.text;

/**
 * Список моих заказов
 *
 * @author Usov Andrey
 * @since 2020-05-14
 */
@Route(value = MyIoListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Мои заказы")
public class MyIoListFrame extends RootListFrame<MyIoGridBean> {

    public static final String VIEW_NAME = "myOrderList";

    protected GridFilterSqlDataProvider<MyIoGridBean, MyIoFilter> dataProvider;

    @Autowired
    protected MyIoListFrame(MyIoGridService gridService,
                            ContactService contactService, AccountApi accountController) {
        super();
        Supplier<ContactBean> contactBeanSupplier = () -> contactService.getUserContact(
                accountController.getAuthenticatedUserLogin());
        MyIoFilter filter = new MyIoFilter();
        dataProvider = new GridFilterSqlDataProvider<>(
                gridService.noFilter(contactBeanSupplier),
                gridService.byFilter(contactBeanSupplier),
                desc(IOBean.CREATE_TIME)
        );

        setPageableDataProvider(dataProvider, filter,
                (orderFilterBinderFieldsCreator -> {
                    addSearchFilter(orderFilterBinderFieldsCreator);
                    orderFilterBinderFieldsCreator.noLabels()
                            .addFields(MyIoFilter.TYPE, MyIoFilter.STATUS);
                }));

        GridColumnBuilder<MyIoGridBean> columnBuilder = gridPanel.columnBuilder();
        columnBuilder.add("Дата создания", bean ->
                DateHelper.format(bean.getIoBean().getCreateTime(), "dd.MM.yyyy в HH:mm"))
                .setAutoWidth(false).setWidth("180px");

        columnBuilder.add("Инстаграм",
                ifElseBuilder(
                        myIoGridBean -> myIoGridBean.getIoBean().getInstagram() != null,
                        UrlRendererBuilder.url(bean ->
                                        bean.getIoBean().getInstagram() != null ? bean.getIoBean().accountUrl() : "",
                                bean -> {
                                    if (bean.getUser() != null)
                                        return StringHelper.isNotEmpty(bean.getUser().getFullName()) ?
                                                bean.getUser().getFullName() :
                                                bean.getUser().getUserName();
                                    return "";
                                }),
                        text(bean -> "Не задан")
                ));

        String iconSize = "20px";
        columnBuilder.add("Прогресс",
                ifBuilder(
                        MyIoGridBean.Type.followers::isExists,
                        ifElseBuilder(
                                bean -> bean.getIoBean().getFollowersPackage() == IOFollowersPackage.economy,
                                label(icon(VaadinIcon.USERS, TextColor.PRIMARY.getValue(), iconSize), MyIoGridBean.Type.followers::percent),
                                label(icon(VaadinIcon.USER_STAR, TextColor.PRIMARY.getValue(), iconSize), MyIoGridBean.Type.followers::percent)
                        )
                ),
                ifBuilder(
                        MyIoGridBean.Type.likes::isExists,
                        label(icon(VaadinIcon.HEART, TextColor.ERROR.getValue(), iconSize), MyIoGridBean.Type.likes::percent)
                ),
                ifBuilder(
                        MyIoGridBean.Type.comments::isExists,
                        label(icon(VaadinIcon.COMMENT, TextColor.SUCCESS.getValue(), iconSize), MyIoGridBean.Type.comments::percent)
                ),
                ifBuilder(
                        MyIoGridBean.Type.views::isExists,
                        label(icon(VaadinIcon.FILM, TextColor.TERTIARY.getValue(), iconSize), MyIoGridBean.Type.views::percent)
                )
        ).setAutoWidth(false).setWidth("170px");

        columnBuilder.add("Стоимость, \u20BD", bean -> money(bean.getIoBean().getOrder().getClientCost()));
        columnBuilder.add("Статус", bean -> bean.getStatus().getStatusName());


    }

    private String money(Double amount) {
        return (amount != null ? StringHelper.toString(amount) : "0,00");
    }

}
