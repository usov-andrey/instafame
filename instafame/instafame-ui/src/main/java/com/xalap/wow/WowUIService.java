package com.xalap.wow;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.vaadin.starter.custom.MenuCreator;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviItem;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviMenu;
import com.xalap.wow.auction.AuctionListFrame;
import com.xalap.wow.craft.BestCraftListFrame;
import com.xalap.wow.item.ItemListFrame;
import com.xalap.wow.item.auction.AuctionItemListFrame;
import com.xalap.wow.professionspell.SpellListFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author Usov Andrey
 * @since 29/01/2020
 */
@Service
public class WowUIService {

    @Autowired
    private final MenuCreator menuCreator;

    public WowUIService(MenuCreator menuCreator) {
        this.menuCreator = menuCreator;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        menuCreator.add(menu());
    }

    private Consumer<NaviMenu> menu() {
        return menu -> {
            NaviItem wow = menu.addNaviItem(VaadinIcon.CLOUD, "Wow",
                    null);
            menu.addNaviItem(wow, "Аукцион", AuctionListFrame.class);
            menu.addNaviItem(wow, "Что крафтить", BestCraftListFrame.class);
            menu.addNaviItem(wow, "Рецепты", SpellListFrame.class);
            menu.addNaviItem(wow, "Предметы", ItemListFrame.class);
            menu.addNaviItem(wow, "Предметы аукциона", AuctionItemListFrame.class);
        };
    }

}
