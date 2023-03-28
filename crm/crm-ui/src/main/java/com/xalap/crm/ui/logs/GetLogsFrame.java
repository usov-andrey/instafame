/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.logs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.xalap.vaadin.custom.component.IntegerField;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.views.ViewFrame;

import java.util.Arrays;

/**
 * @author Усов Андрей
 * @since 26/06/2019
 */
@Route(value = GetLogsFrame.VIEW_NAME, layout = MainLayout.class)
public class GetLogsFrame extends ViewFrame {

    public static final String VIEW_NAME = "getLogs";

    public GetLogsFrame() {

        VerticalLayout layout = new VerticalLayout();
        ComboBox<String> appComboBox = new ComboBox<>("Приложение");
        Anchor download = new Anchor("", "");
        IntegerField hours = new IntegerField("Период(часы)");


        hours.addValueChangeListener(event -> changeAnchor(appComboBox, download, hours));
        appComboBox.addValueChangeListener(event -> changeAnchor(appComboBox, download, hours));

        appComboBox.setItems(Arrays.asList("instafame", "crm", "app"));
        appComboBox.setValue("instafame");
        layout.add(appComboBox);

        hours.setValue(1);
        layout.add(hours);

        download.getElement().setAttribute("download", true);
        download.add(new Button("Получить логи"));
        layout.add(download);

        setViewContent(layout);

    }

    private void changeAnchor(ComboBox<String> appComboBox, Anchor download, IntegerField hours) {
        download.setHref(DownloadLogsController.DOWNLOAD + appComboBox.getValue() + "?hours=" + hours.getValue());
    }


}
