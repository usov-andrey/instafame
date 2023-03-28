/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.starter.appbar;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.shared.Registration;
import com.xalap.vaadin.starter.custom.UICustomizer;
import com.xalap.vaadin.starter.ui.components.FlexBoxLayout;
import com.xalap.vaadin.starter.ui.components.navigation.bar.AppBar;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.ArrayList;
import java.util.List;

/**
 * Чиним ошибку, что обработчики вкладок не удаляются
 * и добавляем разную кастомизацию
 * @author Усов Андрей
 * @since 28/06/2019
 */
public class CustomAppBar extends AppBar {

    private final List<Registration> tabRegistrations = new ArrayList<>();
    private final ServiceRef<UICustomizer> uiCustomizer;

    public CustomAppBar(String title, ServiceRef<UICustomizer> uiCustomizer) {
        super(title);
        avatar.setSrc(uiCustomizer.get().getAvatarImage());
        this.uiCustomizer = uiCustomizer;
    }

    @Override
    protected FlexBoxLayout createContainer() {
        return new FlexBoxLayout(menuIcon, contextIcon, this.title, search,//Убираем строку поиска сверху
                actionItems);
    }

    @Override
    public Registration addTabSelectionListener(
            ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
        Registration registration = super.addTabSelectionListener(listener);
        tabRegistrations.add(registration);
        return registration;
    }

    @Override
    public void removeAllTabs() {
        //Вначале убираем обработчики, чтобы событие не выскакивало при удалении вкладки, а потом только удаляем все вкладки
        tabRegistrations.forEach(Registration::remove);
        tabRegistrations.clear();
        super.removeAllTabs();
    }

    @Override
    protected void initAvatar() {
        avatar = new Image();
        avatar.setClassName(CLASS_NAME + "__avatar");
    }

    @Override
    public void reset() {
        super.reset();
        setTitle("Home");
/*
        uiCustomizer.get().addActionItems(this);

        Button notificationButton = UIUtils.createButton(VaadinIcon.BELL_O, ButtonVariant.LUMO_LARGE,
                ButtonVariant.LUMO_TERTIARY_INLINE);
        addActionItem(notificationButton);
        notificationButton.addClickListener((event2 -> Notification.show("Нет новых уведомлений")));

        FlexHorizontalLayout account = new FlexHorizontalLayout();
        Button dropdown = UIUtils.createButton(VaadinIcon.ANGLE_DOWN, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        account.add(avatar, dropdown);
        ContextMenu contextMenu = new ContextMenu(account);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("Log Out",
                e -> UI.getCurrent().getPage().setLocation(uiCustomizer.get().getLogoutUrl()));
        actionItems.setAlignItems(FlexComponent.Alignment.CENTER);
        addActionItem(account);*/
    }
}
