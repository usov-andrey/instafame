/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.wiq;

import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.provider.json.JsonCheatTaskProvider;
import com.xalap.instafame.service.cheat.provider.nakrutkaby.NakrutkaByCreateOrder;
import com.xalap.instafame.service.cheat.provider.nakrutkaby.NakrutkaByOrderProcessor;
import com.xalap.instafame.service.cheat.provider.nakrutkaby.NakrutkaByOrderStatus;
import com.xalap.instafame.service.cheat.provider.venro.VenroServiceData;
import com.xalap.instafame.service.cheat.provider.venro.VenroServiceDataList;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис https://instagram777.ru/api-documentation.php
 *
 * @author Usov Andrey
 * @since 21.05.2021
 */
@Service
public class WiqCheatTaskProvider extends JsonCheatTaskProvider<NakrutkaByCreateOrder, NakrutkaByOrderStatus,
        VenroServiceData, VenroServiceDataList> {

    public static final String REFILL_BUTTON = "Refill button)";
    private final NakrutkaByOrderProcessor orderProcessor = new NakrutkaByOrderProcessor();

    public WiqCheatTaskProvider() {
        super(NakrutkaByCreateOrder.class, NakrutkaByOrderStatus.class, VenroServiceDataList.class);
    }

    @Override
    protected void processServiceData(List<CheatTaskBean> result, VenroServiceData data) {
        CheatTaskBean bean = new CheatTaskBean();
        String name = data.getName();
        String description = data.getDescription();
        if (name.contains("VK") || name.contains("Youtube") || name.contains("YouTube") || name.contains("TikTok")
                || name.contains("Likee")
                || name.contains("Telegram") || description.contains("Вконтакте") || description.contains("t.me")) {
            return;
        }
        if (name.contains("Подписчики")) {
            bean.setTaskType(InstaOrderTaskType.followers);
        } else if (name.contains("Лайки")) {
            bean.setTaskType(InstaOrderTaskType.likes);
        } else if (name.contains("Комментарии")) {
            bean.setTaskType(InstaOrderTaskType.comments);
        } else if (name.contains("Просмотры видео")) {
            bean.setTaskType(InstaOrderTaskType.views);
        } else {
            return;
        }

        if (description.contains(REFILL_BUTTON)) {
            //(Refill button) 30 дней
            String refillDays = StringHelper.getStringBetween(description, REFILL_BUTTON, "дней").trim();
            bean.setRefillDays(Integer.parseInt(refillDays));
        }

        bean.setProviderName(getName());
        bean.setServiceNumber(Integer.parseInt(data.getID()));
        bean.setName(name);
        bean.setDescription(data.getDescription());
        double cost = Double.parseDouble(data.getCost());
        bean.setCostFor1000(cost);
        bean.setMin(Integer.parseInt(data.getMin()));
        bean.setMax(Integer.parseInt(data.getMax()));
        result.add(bean);
    }

    @Override
    protected void updateOrder(IOTaskBean bean, NakrutkaByOrderStatus nakrutkaByOrderStatus) {
        orderProcessor.updateOrder(bean, nakrutkaByOrderStatus);
    }

    @Override
    protected String getExtOrderId(NakrutkaByCreateOrder nakrutkaByCreateOrder) {
        return nakrutkaByCreateOrder.getOrderId();
    }

    @Override
    public String getName() {
        return "instagram777";
    }

    @Override
    public String getApiKey() {
        return "asdasdsd";
    }

    @Override
    public String getApiUrl() {
        return "https://wiq.ru/api/";
    }

    @Override
    protected String getCreateOrderUrl() {
        return "&action=create&service=%s&quantity=%s&link=%s";
    }
}
