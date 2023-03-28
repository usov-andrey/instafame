/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.balance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.xalap.payment.api.PaymentApi;
import com.xalap.payment.balance.api.BalanceApi;
import com.xalap.payment.invoice.api.InvoiceApi;
import com.xalap.payment.ui.balance.ReplenishBalanceComponent;
import com.xalap.uaa.api.account.AccountApi;
import com.xalap.vaadin.custom.component.ButtonWithLog;
import com.xalap.vaadin.custom.component.fluent.Div;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;
import com.xalap.vaadin.custom.component.layout.HeaderLabelLayout;
import com.xalap.vaadin.custom.frame.NavigationFrame;
import com.xalap.vaadin.custom.widget.FaqWidget;
import com.xalap.vaadin.custom.widget.WidgetBox;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.util.FontSize;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static com.xalap.vaadin.custom.component.fluent.Div.div;
import static com.xalap.vaadin.custom.component.fluent.Span.span;
import static com.xalap.vaadin.custom.component.layout.CardLayout.cards;


/**
 * Пополнить баланс
 *
 * Дизайн брался с https://vkserfing.ru/cashin
 *
 * @author Usov Andrey
 * @since 2020-04-22
 */
@CssImport("./styles/views/balance.css")
@Route(value = ReplenishBalanceFrame.VIEW_NAME, layout = MainLayout.class)
public class ReplenishBalanceFrame extends NavigationFrame {

    public static final String VIEW_NAME = "replenishBalance";

    private final ReplenishBalanceComponent replenishBalance;

    @Autowired
    public ReplenishBalanceFrame(BalanceApi balanceApi, PaymentApi paymentApi, InvoiceApi invoiceApi,
                                 AccountApi accountApi) {

        VerticalLayout body = new CustomVerticalLayout();
        setViewContent(body);

        FlexHorizontalLayout layout = new FlexHorizontalLayout().spacingM();
        replenishBalance = new ReplenishBalanceComponent(layout, balanceApi, invoiceApi, paymentApi, accountApi);
        replenishBalance.getAmountField().setLabel("");
        replenishBalance.getPayButton().variants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);

        Label label = UIUtils.createH4Label("Введите сумму пополнения");
        label.addClassNames(LumoStyles.Margin.Bottom.M, LumoStyles.Margin.Top.L);

        ButtonWithLog promoLink = new ButtonWithLog("У меня есть промокод");
        promoLink.variants(ButtonVariant.LUMO_TERTIARY_INLINE);
        promoLink.setIcon(VaadinIcon.GIFT.create());
        promoLink.addClassName(LumoStyles.Margin.Top.L);

        Div present = div("box2i",
                div("box2i_inside",
                        label,
                        layout,
                        promoLink
                )
        );

        body.add(new WidgetBox(present));

        //-----------Бонусы при пополнении----------
        body.add(
                new WidgetBox(HeaderLabelLayout.h4Layout("Бонусы при пополнении:",
                        cards(
                                bonus(2000, 10),
                                bonus(4000, 20),
                                bonus(8000, 30),
                                bonus(16000, 40))
                        )
                )
        );

        body.add(new FaqWidget()
                .add("Отправил деньги, но баланс кабинета не пополнился",
                        "Вам необходимо обратиться в поддержку, предоставив чек об оплате, свой логин и примерное время пополнения. Зачислим на баланс в ручном режиме.")
                .add("Забыл логин/пароль, что делать?", "Вы можете воспользоваться формой восстановления логина или пароля, в крайнем случае можем рассмотреть в поддержке индивидуально.")
        );
    }

    private Component bonus(int payAmount, int discountPercent) {
        int discountAmount = payAmount * discountPercent / 100;
        return div("pay-bonus",
                div("pay-bonus__inner",
                        div(
                                span("pay-bonus__percentage", "+" + discountPercent + "%"),
                                div("pay-bonus__sum",
                                        span("pay-bonus__sum-label", "от"),
                                        span("pay-bonus__sum-value", "" + payAmount),
                                        span("pay-bonus__sum-cur", "\u20BD")
                                ),
                                div("pay-bonus__gift", "+ " + discountAmount + " \u20BD в подарок")
                        )
                ),
                //div("pay-bonus__footer",
                CustomVerticalLayout.of(
                        UIUtils.createLabel(FontSize.S, "Все способы оплаты"),
                        new ButtonWithLog("Пополнить", () -> replenishBalance.pay(new BigDecimal(payAmount)))
                                .variants(ButtonVariant.LUMO_PRIMARY)
                ).center().padding().spacing()
                //)
        );
    }

    @Override
    protected String getTitle() {
        return "Пополнение баланса";
    }
}
