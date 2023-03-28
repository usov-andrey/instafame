/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.custom;

import com.xalap.vaadin.starter.ui.components.navigation.bar.AppBar;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviDrawer;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviMenu;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Brand Provider
 *
 * @author Usov Andrey
 * @since 2020-03-11
 */
@Service
public class UICustomizer {

    private final List<Consumer<AppBar>> appBarConsumers = new ArrayList<>();
    private String logoImgPath = UIUtils.IMG_PATH + "logos/18.png";
    private String title = "vaadin-starter";
    private Function<String, AppBar> appBarCreator = UICustomizer::defaultAppBar;
    private Supplier<NaviDrawer> naviDrawerCreator = NaviDrawer::new;
    private Supplier<String> avatarImageSupplier = () -> UIUtils.IMG_PATH + "avatar.png";
    private Consumer<NaviMenu> menuConsumer = menu -> {
    };

    private static AppBar defaultAppBar(String titleBar) {
        return new AppBar(titleBar);
    }

    public String getLogoImgPath() {
        return logoImgPath;
    }

    public void setLogoImgPath(String logoImgPath) {
        this.logoImgPath = logoImgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AppBar createAppBar(String title) {
        return appBarCreator.apply(title);
    }

    public NaviDrawer createNaviDrawer() {
        return naviDrawerCreator.get();
    }

    public void setAppBarCreator(Function<String, AppBar> appBarCreator) {
        this.appBarCreator = appBarCreator;
    }

    public void setAvatarImageProvider(Supplier<String> avatarImageSupplier) {
        this.avatarImageSupplier = avatarImageSupplier;
    }

    public String getAvatarImage() {
        return avatarImageSupplier.get();
    }

    public void setMenuConsumer(Consumer<NaviMenu> menuConsumer) {
        this.menuConsumer = menuConsumer;
    }

    public void addActionItems(AppBar appBar) {
        appBarConsumers.forEach(appBarConsumer -> appBarConsumer.accept(appBar));
    }

    public void addAppBarActionItemConsumer(Consumer<AppBar> consumer) {
        appBarConsumers.add(consumer);
    }

    public void setNaviDrawerCreator(Supplier<NaviDrawer> naviDrawerCreator) {
        this.naviDrawerCreator = naviDrawerCreator;
    }

    public void addMenu(NaviMenu menu) {
        menuConsumer.accept(menu);
    }
}
