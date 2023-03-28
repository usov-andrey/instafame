package com.xalap.wow.item;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.RouterLink;
import com.xalap.vaadin.custom.component.fluent.Anchor;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;

/**
 * @author Усов Андрей
 * @since 19/11/2019
 */
public class ItemComponent extends CustomVerticalLayout {

    public ItemComponent(ItemBean itemBean) {
        String text = itemBean.getName() + " " + itemBean.getBindType() + " " + itemBean.getQuality();
        RouterLink routerLink = new RouterLink(text,
                ItemFrame.class, itemBean.getId());
        add(routerLink);
        add(new Label(" "));
        add(new Anchor("https://ru.wowhead.com/item=" + itemBean.getItemId(), "WowHead").blank());
        add(new Anchor("https://www.tradeskillmaster.com/items/" + itemBean.getItemId(), "TSM").blank());
        add(new Anchor("https://www.wowdb.com/items/" + itemBean.getItemId(), "WowDB").blank());
    }

}
