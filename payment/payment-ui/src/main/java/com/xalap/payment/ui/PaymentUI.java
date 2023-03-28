/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.payment.balance.api.BalanceApi;
import com.xalap.payment.ui.balance.MyReplenishBalancePaymentListFrame;
import com.xalap.payment.ui.invoice.InvoiceListFrame;
import com.xalap.payment.ui.payment.PaymentListFrame;
import com.xalap.uaa.api.account.AccountApi;
import com.xalap.vaadin.custom.component.ButtonWithLog;
import com.xalap.vaadin.custom.component.fluent.Div;
import com.xalap.vaadin.custom.component.fluent.Span;
import com.xalap.vaadin.starter.custom.MenuCreator;
import com.xalap.vaadin.starter.custom.UICustomizer;
import com.xalap.vaadin.starter.ui.util.IconSize;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import org.springframework.stereotype.Service;

import static com.vaadin.flow.component.button.ButtonVariant.*;

/**
 * @author Usov Andrey
 * @since 2020-04-16
 */
@Service
public class PaymentUI {

    private final UICustomizer uiCustomizer;
    private final BalanceApi balanceApi;
    private final AccountApi accountController;
    private final MenuCreator menu;
    private Runnable payButtonRunnable = () -> UI.getCurrent().navigate(MyReplenishBalancePaymentListFrame.class);

    public PaymentUI(UICustomizer uiCustomizer, BalanceApi balanceApi, AccountApi accountController,
                     MenuCreator menu) {
        this.uiCustomizer = uiCustomizer;
        this.balanceApi = balanceApi;
        this.accountController = accountController;
        this.menu = menu;
    }

    public void setPayButtonRunnable(Runnable payButtonRunnable) {
        this.payButtonRunnable = payButtonRunnable;
    }

    public void addMenu() {
        menu.add(menu -> {
            menu.addNaviItem(VaadinIcon.MONEY_DEPOSIT,
                    "Мои платежи", MyReplenishBalancePaymentListFrame.class);
            menu.addNaviItem(VaadinIcon.MONEY_EXCHANGE,
                    "Все платежи", PaymentListFrame.class);
            menu.addNaviItem(VaadinIcon.INVOICE,
                    "Счета", InvoiceListFrame.class);
        });
    }

    public void addBalanceActionItem() {
        uiCustomizer.addAppBarActionItemConsumer(appBar -> {
            double value = getCurrentUserBalance();
            appBar.addActionItem(new ButtonWithLog(value + "\u20BD", VaadinIcon.WALLET.create(),
                    () -> payButtonRunnable.run())
            );
        });
    }

    private double getCurrentUserBalance() {
        String login = accountController.getAuthenticatedUserLogin();
        return balanceApi.getBalance(login).doubleValue();
    }

    public Component createBalancePanel() {
        double value = getCurrentUserBalance();
        Span valueLabel = new Span(value + "\u20BD");
        Div layout = new Div(
                UIUtils.createIcon(IconSize.S, TextColor.PRIMARY, VaadinIcon.WALLET),
                valueLabel,
                new ButtonWithLog("Пополнить", () -> payButtonRunnable.run())
                        .variants(LUMO_PRIMARY, LUMO_SUCCESS, LUMO_SMALL));
        layout.addClassNames("navi-item__link");
        return layout;
    }
}
