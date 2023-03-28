/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.venro;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.exception.ServiceWrongResponseException;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.exception.*;
import com.xalap.instafame.service.cheat.provider.CheatTaskProvider;
import com.xalap.instafame.service.instaorder.task.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 12/01/2019
 */
@Service
public class VenroProvider extends CheatTaskProvider {

    private static final String API_KEY = "dwerwerwe";
    private static final String API_URL = "https://venro.ru/api/orders?key=" + API_KEY;
    private static final String CREATE_ORDER = API_URL + "&action=add&type=%s&count=%s&url=%s";
    private static final String CREATE_ORDER_WITH_SPEED = CREATE_ORDER + "&speed=%s";
    private static final String ORDER_STATUS = API_URL + "&action=check&id=%s";
    private static final String CANCEL = API_URL + "&action=cancel&id=%s";
    private static final String PRICES = API_URL + "&action=services";

    @Override
    public String getApiKey() {
        return API_KEY;
    }

    @Override
    public String getApiUrl() {
        return "https://venro.ru/api/orders";
    }

    private VenroOrder createOrder(int quantity, String link, int serviceNumber, VenroSpeed speed) throws ServiceTemporaryException {
        return createOrder(speed != null && !speed.equals(VenroSpeed.sMax) ?
                String.format(CREATE_ORDER_WITH_SPEED, serviceNumber, quantity, link, speed.getSpeed()) :
                String.format(CREATE_ORDER, serviceNumber, quantity, link));
    }

    private VenroOrder createOrder(String url) throws ServiceTemporaryException {
        try {
            return exec(url);
        } catch (VenroUrlException e) {
            throw new MediaDeletedException(url, e);
        }
    }

    private VenroOrder orderStatus(String orderId) throws ServiceTemporaryException {
        try {
            String url = String.format(ORDER_STATUS, orderId);
            return exec(url);
        } catch (VenroUrlException e) {
            throw new ServiceTemporaryException("Error on orderStatus:" + orderId, e);
        }
    }

    private VenroOrder exec(String url) throws ServiceTemporaryException {
        try {
            VenroOrder response = exec(url, VenroOrder.class);
            if (StringHelper.isNotEmpty(response.getError())) {
                String error = response.getError();
                switch (error) {
                    case "already":
                        throw new AlreadyOrderException(); //Уже есть заказ на этом сайте

                    case "url":
                        //Ошибка означает, что публикация была удалена
                        throw new VenroUrlException("Url error on url:" + url);
                    case "balance":
                        throw new NoBalanceException("Venro");
                    case "service is disabled":
                        throw new ServiceDisabledException("Error on exec url:" + url + "\nresponse:" + response);
                    case "account is private":
                        throw new AccountIsPrivateException("Error on exec url:" + url + "\nresponse:" + response);
                }
                throw new IllegalStateException("Error on exec url:" + url + "\nresponse:" + response);
            }
            return response;
        } catch (ServiceWrongResponseException e) {
            if (e.getResponse().trim().isEmpty()) {
                throw new ServiceTemporaryException(e);
            } else throw e;
        }
    }

    @Override
    public String getName() {
        return "Venro";
    }


    @Override
    public List<CheatTaskBean> getActualTasks() throws ServiceTemporaryException {
        //getJson(seleniumService, url);
        VenroServiceDataList response = getActualTasksResponse();
        List<CheatTaskBean> result = new ArrayList<>();
        for (VenroServiceData data : response) {
            processServiceData(result, data);
        }
        return result;
    }

    private void processServiceData(List<CheatTaskBean> result, VenroServiceData data) {
        CheatTaskBean bean = new CheatTaskBean();
        String name = data.getName();
        if (name.startsWith("Подписчики")) {
            bean.setTaskType(InstaOrderTaskType.followers);
        } else if (name.startsWith("Лайки") && !name.startsWith("Лайки на комментарий")) {
            bean.setTaskType(InstaOrderTaskType.likes);
        } else if (name.startsWith("Комментарии")) {
            bean.setTaskType(InstaOrderTaskType.comments);
        } else if (name.startsWith("Просмотры видео")) {
            bean.setTaskType(InstaOrderTaskType.views);
        } else {
            return;
        }
        if (name.contains("⏱")) {
            bean.setSpeedSupport(true);
        }

        bean.setProviderName(getName());
        bean.setServiceNumber(Integer.parseInt(data.getID()));
        bean.setName(name);
        bean.setDescription(data.getDescription());
        double cost = Double.parseDouble(data.getCost());
        //double priceFor1000 = cost * 1000;
        bean.setCostFor1000(cost);
        bean.setMin(Integer.parseInt(data.getMin()));
        bean.setMax(Integer.parseInt(data.getMax()));
        result.add(bean);
    }

    private VenroServiceDataList getActualTasksResponse() throws ServiceTemporaryException {
        try {
            return exec(PRICES, VenroServiceDataList.class);
        } catch (ServiceWrongResponseException e) {
            throw new ServiceTemporaryException("Error on get Actual Tasks", e);
        }
    }

    @Override
    public void update(IOTaskBean bean) throws ServiceTemporaryException {
        VenroOrder order = orderStatus(bean.getExtOrderId());
        updateOrder(bean, order);
    }

    private void updateOrder(IOTaskBean bean, VenroOrder order) {
        if ("error".equals(order.getCheck())) {
            //Значит нельзя получить прогресс об этом заказе
            bean.setStatus(InstaOrderTaskStatus.Completed);
            return;
        }
        if (order.getCharge() != null) {
            bean.setCharge(Double.parseDouble(order.getCharge()));
        }
        bean.setRemains(Integer.parseInt(order.getRemains()));
        if (bean.getRemains() > bean.getQuantity()) {
            bean.setRemains(bean.getQuantity());
        }
        bean.setStartCount(StringHelper.isNotEmpty(order.getStart()) ? Integer.parseInt(order.getStart()) : 0);
        bean.setExtOrderId(order.getId());
        bean.setStatus(InstaOrderTaskStatus.InProgress);
        if (order.getStatus().equals("Completed")) {
            bean.setStatus(InstaOrderTaskStatus.Completed);
        } else if (order.getStatus().equals("Partial")) {
            //Нужно расчитать стоимость сколько реально заплатили
            double cost = bean.getCharge() * (bean.getQuantity() - bean.getRemains()) / bean.getQuantity();
            if (cost < 0) {
                cost = 0;
            }
            //Округляем до 2 чисел после запятой
            double rounded = Math.round(cost * 100.0) / 100.0;
            bean.setCharge(rounded);
            bean.setStatus(InstaOrderTaskStatus.Interrupted);
            //{"id":"75024886","url":"https:\/\/www.instagram.com\/p\/B8TNtRsB7ZM\/","start":"0","count":"500","remains":"500","status":"Canceled","charge":"2.25","refund":"2.25"}
        } else if (order.getStatus().equals("Canceled")) {
            if (order.getRefund() != null) {
                //Теперь Venro стал присылать информацию о деньгах
                Double charge = Double.parseDouble(order.getCharge());
                Double refund = Double.parseDouble(order.getRefund());
                bean.setCharge(charge - refund);
                bean.setStatus(InstaOrderTaskStatus.Interrupted);
            } else {
                //Venro не присылает реальных денег, считаем примерно
                if (bean.getOrder().getUser() != null) {
                    int followers = bean.getOrder().getUser().getUserStats().getFollowedByCount() - bean.getStartCount();
                    if (followers < 0) {
                        followers = 0;
                    }
                    double cost = bean.getCharge() * followers / bean.getQuantity();
                    double rounded = Math.round(cost * 100.0) / 100.0;
                    bean.setRemains(bean.getQuantity() - followers);
                    if (bean.getRemains() > bean.getQuantity()) {
                        bean.setRemains(bean.getQuantity());
                    }
                    bean.setCharge(rounded);
                }
            }
            bean.setStatus(InstaOrderTaskStatus.Interrupted);
        }
    }

    @Override
    public void abort(IOTaskBean taskBean) throws ServiceTemporaryException {
        String url = String.format(CANCEL, taskBean.getExtOrderId());
        VenroOrder response = exec(url, VenroOrder.class);
        if (response.getCancel() == null || !response.getCancel().equals("ok")) {
            throw new ServiceAbortErrorException("Error on cancel url:" + url + "\n response:" + response);
        }
    }

    @Override
    public void runFollowers(IOFollowersTaskBean taskBean) throws ServiceTemporaryException {
        CheatTaskBean cheatTask = taskBean.getCheatTask();
        VenroOrder order = createOrder(taskBean.getQuantity(), taskBean.getOrder().accountUrl(), cheatTask.getServiceNumber(),
                cheatTask.isSpeedSupport() ? VenroSpeed.find(taskBean.getCountSpeed()) : null);
        updateOrder(taskBean, order);
    }

    @Override
    public void runLikes(IOLikesTaskBean taskBean) throws ServiceTemporaryException {
        runWithMedia(taskBean);
    }

    @Override
    public void runComments(IOCommentsTaskBean taskBean) throws ServiceTemporaryException {
        CheatTaskBean cheatTask = taskBean.getCheatTask();
        VenroOrder order = createOrder(taskBean.getQuantity(), taskBean.url(), cheatTask.getServiceNumber(),
                cheatTask.isSpeedSupport() ? VenroSpeed.find(taskBean.getCountSpeed()) : null);
        updateOrder(taskBean, order);
    }

    @Override
    public void runViews(IOViewsTaskBean taskBean) throws ServiceTemporaryException {
        runWithMedia(taskBean);
    }

    private void runWithMedia(AbstractMediaTaskBean taskBean) throws ServiceTemporaryException {
        CheatTaskBean cheatTask = taskBean.getCheatTask();
        VenroOrder order = createOrder(taskBean.getQuantity(), taskBean.url(), cheatTask.getServiceNumber(),
                cheatTask.isSpeedSupport() ? VenroSpeed.find(taskBean.getCountSpeed()) : null);
        updateOrder(taskBean, order);
    }

    @Override
    public void refill(IOTaskBean bean) throws ServiceTemporaryException {
        throw new UnsupportedOperationException();
    }

}
