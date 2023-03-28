/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.parser;

import com.xalap.instafame.service.instaorder.IOBean;
import org.springframework.stereotype.Service;

/**
 * Создает IOBean по строке заказа
 *
 * @author Usov Andrey
 * @since 2020-07-18
 */
@Service
public class OrderLineParser {

    private final OldOrderLineParser oldOrderLineParser;
    private final NewOrderLineParser newOrderLineParser;

    public OrderLineParser(OldOrderLineParser oldOrderLineParser, NewOrderLineParser newOrderLineParser) {
        this.oldOrderLineParser = oldOrderLineParser;
        this.newOrderLineParser = newOrderLineParser;
    }

    public void parse(IOBean bean, String orderLineText) {
        //В новом формате всегда указывается скорость, например,
        //1000 Премиум подписчиков, 50-100 в день
        if (orderLineText.contains(",")) {
            newOrderLineParser.parse(bean, orderLineText);
        } else {
            oldOrderLineParser.parse(bean, orderLineText);
        }
    }


}
