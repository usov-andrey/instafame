/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.ui.balance;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.function.SerializableConsumer;
import com.xalap.payment.api.PaymentApi;
import com.xalap.payment.api.PaymentHtml;
import com.xalap.payment.balance.api.BalanceApi;
import com.xalap.payment.invoice.api.InvoiceApi;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.payment.invoice.api.InvoiceId;
import com.xalap.uaa.api.account.AccountApi;
import com.xalap.vaadin.custom.component.ButtonWithLog;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Компонент пополнения баланса
 *
 * @author Usov Andrey
 * @since 2020-04-22
 */
public class ReplenishBalanceComponent implements Serializable {

    private final BigDecimalField amountField;
    private final ButtonWithLog payButton;
    private final SerializableConsumer<BigDecimal> payConsumer;

    public ReplenishBalanceComponent(HasComponents layout, BalanceApi balanceApi, InvoiceApi invoiceApi,
                                     PaymentApi paymentApi, AccountApi accountApi) {

        List<String> providers = paymentApi.getPaymentServiceProviderNames();
        ComboBox<String> providerComboBox = new ComboBox<>("Платежная система");
        providerComboBox.setItems(providers);
        providerComboBox.setValue(providers.get(0));
        if (providers.size() > 1) {
            //Отображаем выбор платежной системы, только если их несколько
            layout.add(providerComboBox);
        }

        amountField = new BigDecimalField("Введите сумму", new BigDecimal(12), "");
        amountField.setSuffixComponent(new Label("\u20BD"));
        payConsumer = amountValue -> {
            InvoiceId invoiceId = paymentApi.generateNewInvoiceId(providerComboBox.getValue());
            InvoiceDto invoice = invoiceApi.createAndSaveInvoice(accountApi.getAuthenticatedUserLogin(), amountValue,
                    balanceApi.getInvoiceType(), invoiceId);
            PaymentHtml paymentHtml = paymentApi.getPaymentHtml(providerComboBox.getValue(), invoice);
            layout.add(new Html(paymentHtml.getFormHtml()));
            UI.getCurrent().getPage().executeJs(paymentHtml.getJavaScript());
        };
        payButton = new ButtonWithLog("Пополнить", () -> {
            BigDecimal amountValue = amountField.getValue();
            if (amountValue == null || providerComboBox.getValue() == null) {
                Notification.show("Заполните поле");
                return;
            }
            payConsumer.accept(amountValue);
        });

        layout.add(amountField, payButton);
    }

    public void pay(BigDecimal amount) {
        payConsumer.accept(amount);
    }

    public BigDecimalField getAmountField() {
        return amountField;
    }

    public ButtonWithLog getPayButton() {
        return payButton;
    }
}
