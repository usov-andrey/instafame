/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.smmpanel;

import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.exception.NoBalanceException;
import com.xalap.instafame.service.cheat.provider.json.JsonCheatTaskProvider;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;

import java.util.List;

/**
 * @author Usov Andrey
 * @since 06/02/2020
 */
public abstract class SmmPanelProvider extends JsonCheatTaskProvider<SmmPanelAddOrder, SmmPanelOrder,
        SmmPanelServiceData, SmmPanelServiceDataList> {

    public SmmPanelProvider() {
        super(SmmPanelAddOrder.class, SmmPanelOrder.class, SmmPanelServiceDataList.class);
    }

    @Override
    protected double toRubRate() {
        return 80;//Реальный курс + 8 процентов
    }

    @Override
    protected void processServiceData(List<CheatTaskBean> result, SmmPanelServiceData data) {
        CheatTaskBean bean = new CheatTaskBean();
        String name = data.getName();
        if (name.contains("Followers") && data.getCategory().contains("Instagram")) {
            bean.setTaskType(InstaOrderTaskType.followers);
        } else {
            return;
        }

        bean.setProviderName(getName());
        bean.setServiceNumber(data.getService());
        String category = data.getCategory();//Instagram Followers [Guaranteed]
        //берем только то, что в скобках
        category = StringHelper.getStringBetween(category, "[", "]");
        bean.setName("[" + category + "]" + name);
        //bean.setDescription(data.get);
        double cost = Double.parseDouble(data.getRate());
        cost = cost * toRubRate();//примерный курс рубля
        //double priceFor1000 = cost * 1000;
        bean.setCostFor1000(cost);
        bean.setMin(Integer.parseInt(data.getMin()));
        bean.setMax(Integer.parseInt(data.getMax()));
        result.add(bean);
    }

    @Override
    protected String getExtOrderId(SmmPanelAddOrder addOrder) {
        if (StringHelper.isNotEmpty(addOrder.getError())) {
            if (addOrder.getError().contains("balance")) {
                throw new NoBalanceException(getName());
            }
            throw new IllegalStateException("Error response by SmmPanelAddOrder:" + addOrder.getError());
        }
        return Integer.toString(addOrder.getOrder());
    }

    @Override
    protected void updateOrder(IOTaskBean bean, SmmPanelOrder order) {
        if (order.getCharge() != null) {
            bean.setCharge(Double.parseDouble(order.getCharge()) * toRubRate());
        }
        bean.setRemains(Integer.parseInt(order.getRemains()));
        if (bean.getRemains() > bean.getQuantity()) {
            bean.setRemains(bean.getQuantity());
        }
        bean.setStartCount(StringHelper.isNotEmpty(order.getStartCount()) ? Integer.parseInt(order.getStartCount()) : 0);
        bean.setStatus(InstaOrderTaskStatus.InProgress);
        Double charge = bean.getCharge();
        if (order.getStatus().equals("Completed")) {
            bean.setStatus(InstaOrderTaskStatus.Completed);
        } else if (order.getStatus().equals("Partial")) {
            //Нужно расчитать стоимость сколько реально заплатили
            double cost = charge * (bean.getQuantity() - bean.getRemains()) / bean.getQuantity();
            if (cost < 0) {
                cost = 0;
            }
            //Округляем до 2 чисел после запятой
            double rounded = Math.round(cost * 100.0) / 100.0;
            bean.setCharge(rounded);
            bean.setStatus(InstaOrderTaskStatus.Interrupted);
        } else if (order.getStatus().equals("Canceled")) {
            bean.setStatus(InstaOrderTaskStatus.Canceled);
        }
    }
}
