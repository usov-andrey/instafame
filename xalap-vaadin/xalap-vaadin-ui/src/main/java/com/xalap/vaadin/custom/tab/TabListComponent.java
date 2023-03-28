/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.tab;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;
import com.xalap.vaadin.custom.crud.Drawable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Usov Andrey
 * @since 30/01/2020
 */
public class TabListComponent<T extends Component> extends CustomVerticalLayout implements Drawable {

    private final Tabs tabs = new Tabs();
    private final Map<com.vaadin.flow.component.tabs.Tab, T> tabBodyMap = new LinkedHashMap<>();
    private final VerticalLayout body = new VerticalLayout();
    private Component currentTab;

    public TabListComponent() {
        tabs.addSelectedChangeListener(event -> {
            if (currentTab != null) {////currentTab == null при добавлении первой вкладки
                currentTab.setVisible(false);
            }
            currentTab = tabBodyMap.get(tabs.getSelectedTab());
            currentTab.setVisible(true);
        });


        addTabs();
        add(body);
        body.setSizeFull();
    }

    protected void addTabs() {
        FlexHorizontalLayout layout = new FlexHorizontalLayout();
        //Без этой строчки на мобильном отображается некорректно
        layout.getStyle().remove("display");
        layout.setWidthFull();
        layout.add(tabs);
        add(layout);
    }

    /**
     * Если уже есть вкладка с таким caption, то она заменяется
     */
    public void createTab(String caption, T body) {
        Optional<Tab> tabWithCaption = getTabWithCaption(caption);
        if (tabWithCaption.isPresent()) {
            tabBodyMap.replace(tabWithCaption.get(), body);
        } else {
            var tab = new com.vaadin.flow.component.tabs.Tab(caption);
            tabBodyMap.put(tab, body);

        }
    }

    private Optional<Tab> getTabWithCaption(String caption) {
        //Вкладок не может быть очень много, поэтому не заботимся об эффективности
        for (Tab tab : tabBodyMap.keySet()) {
            if (tab.getLabel().equals(caption)) {
                return Optional.of(tab);
            }
        }
        return Optional.empty();
    }

    public void draw() {
        for (Map.Entry<Tab, T> tabTEntry : getTabEntries()) {
            var tabBody = tabTEntry.getValue();
            tabBody.setVisible(false);
            this.body.add(tabBody);
            var tab = tabTEntry.getKey();
            tabs.add(tab);
        }
    }

    protected Set<Map.Entry<Tab, T>> getTabEntries() {
        return tabBodyMap.entrySet();
    }


}
