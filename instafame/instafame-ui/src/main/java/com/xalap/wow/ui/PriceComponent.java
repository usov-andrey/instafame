package com.xalap.wow.ui;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;
import com.xalap.wow.auction.PriceHelper;

/**
 * @author Усов Андрей
 * @since 10/08/2019
 */
public class PriceComponent extends FlexHorizontalLayout {

    public PriceComponent(Double price) {
        this(price.longValue());
    }

    public PriceComponent(Long price) {
        if (price == null) {
            return;
        }
        long gold = PriceHelper.inGold(price);
        long silver = (price - (gold * 10000)) / 100;
        long copper = (price - (gold * 10000) - (silver * 100));
        if (gold > 0) {
            add(new Label(Long.toString(gold)), img("https://wow.zamimg.com/images/icons/money-gold.gif", "gold"));
        }
        if (silver > 0) {
            add(new Label(Long.toString(silver)), img("https://wow.zamimg.com/images/icons/money-silver.gif", "silver"));
        }
        if (copper > 0) {
            add(new Label(Long.toString(copper)), img("https://wow.zamimg.com/images/icons/money-copper.gif", "copper"));
        }
        if (price == 0) {
            add(new Label("0"));
        }
    }

    private Image img(String src, String alt) {
        Image image = new Image(src, alt);
        image.setWidth("10px");
        image.setHeight("10px");
        return image;
    }


}
