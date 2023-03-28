/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.vkserfing;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.integration.lock.LockService;
import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.provider.CheatTaskProvider;
import com.xalap.instafame.service.cheat.provider.vkserfing.createorder.*;
import com.xalap.instafame.service.cheat.provider.vkserfing.editorder.VkSerfingEditOrderData;
import com.xalap.instafame.service.cheat.provider.vkserfing.editorder.VkSerfingEditOrderResponse;
import com.xalap.instafame.service.cheat.provider.vkserfing.getorder.VkSerfingGetOrderData;
import com.xalap.instafame.service.cheat.provider.vkserfing.getorder.VkSerfingGetOrderResponse;
import com.xalap.instafame.service.cheat.provider.vkserfing.getorder.VkSerfingOrderStatus;
import com.xalap.instafame.service.cheat.provider.vkserfing.model.VkSerfingResponse;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.*;
import com.xalap.instagram.service.user.InstagramUserBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Работа с сайтом https://vkserfing.ru/
 *
 * @author Usov Andrey
 * @since 2020-07-21
 */
@Service
public class VkSerfingCheatProvider extends CheatTaskProvider {

    public static final String CREATE_ORDER_PARAMS = "&link=%s&type=%s&amount_users_limit=%s&status=on";
    public static final String CREATE_ORDER_COMMENTS_PARAMS = "&comments_type=%s";
    private static final String URL = "https://vkserfing.ru/api/%s.%s?token=%s";
    private static final String GET_ORDER_PARAMS = "&id=%s";
    private static final String EDIT_ORDER_PARAMS = "&id=%s&status=%s";

    private final LockService lockService;
    private final JsonService jsonService;

    public VkSerfingCheatProvider(LockService lockService, JsonService jsonService) {
        this.lockService = lockService;
        this.jsonService = jsonService;
    }

    @Override
    public String getName() {
        return "VkSerfing";
    }

    private <T> T execSynchronized(String url, Class<T> tClass) throws ServiceTemporaryException {
        try {
            return lockService.withLock("VkSerfing", () -> exec(url, tClass));
        } catch (ServiceTemporaryException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private VkSerfingCreateOrderData createOrder(String link, VkSerfingOrderType orderType, int count,
                                                 VkSerfingTargeting targeting) throws ServiceTemporaryException {
        return createOrder(orderType.createOrderParams(link, count), targeting);
    }

    private VkSerfingCreateOrderData createOrder(String orderParams,
                                                 VkSerfingTargeting targeting) throws ServiceTemporaryException {
        String url = String.format(URL, "campaign", "add", getApiKey()) + orderParams;
        url = targeting.processUrl(url);
        VkSerfingCreateOrderResponse response = execSynchronized(url, VkSerfingCreateOrderResponse.class);
        return getResponseData(url, response);
    }

    private VkSerfingGetOrderData getOrder(String orderId) throws ServiceTemporaryException {
        String url = String.format(URL, "campaign", "getById", getApiKey()) +
                String.format(GET_ORDER_PARAMS, orderId);
        VkSerfingGetOrderResponse response = execSynchronized(url, VkSerfingGetOrderResponse.class);
        return getResponseData(url, response);
    }

    private VkSerfingEditOrderData abortOrder(String orderId) throws ServiceTemporaryException {
        String url = String.format(URL, "campaign", "edit", getApiKey()) +
                String.format(EDIT_ORDER_PARAMS, orderId, VkSerfingOrderStatus.delete);
        VkSerfingEditOrderResponse response = execSynchronized(url, VkSerfingEditOrderResponse.class);
        return getResponseData(url, response);
    }

    private <T> T getResponseData(String url, VkSerfingResponse<T> response) {
        if ("success".equals(response.getStatus())) {
            return response.getData();
        } else {
            throw new IllegalStateException("Error on create order VkSerfing:" + url + " Response:" + response);
        }
    }

    @Override
    public List<CheatTaskBean> getActualTasks() {
        return Arrays.asList(
                cheatTaskBean(1, InstaOrderTaskType.followers, "Подписчики", 500),
                cheatTaskBean(2, InstaOrderTaskType.likes, "Лайки", 200),
                cheatTaskBean(3, InstaOrderTaskType.comments, "Комментарии", 600),
                cheatTaskBean(4, InstaOrderTaskType.views, "Просмотры видео", 100)
        );
    }

    private CheatTaskBean cheatTaskBean(int index, InstaOrderTaskType taskType, String name,
                                        double priceFor1000) {
        CheatTaskBean bean = new CheatTaskBean();
        bean.setTaskType(taskType);
        bean.setName(name);
        bean.setProviderName(getName());
        bean.setServiceNumber(index);
        bean.setCostFor1000(priceFor1000);
        bean.setMin(5);
        bean.setMax(10000);
        return bean;
    }

    @Override
    public void abort(IOTaskBean taskBean) throws ServiceTemporaryException {
        VkSerfingEditOrderData vkSerfingEditOrderData = abortOrder(taskBean.getExtOrderId());
        if (!"success".equals(vkSerfingEditOrderData.getEdit())) {
            throw new IllegalStateException("Error on abort order VkSerfing:" + taskBean);
        }
    }

    @Override
    public void update(IOTaskBean bean) throws ServiceTemporaryException {
        VkSerfingGetOrderData order = getOrder(bean.getExtOrderId());
        updateOrder(bean, order);
    }

    private void updateOrder(IOTaskBean bean, VkSerfingGetOrderData order) {
        if (order.getMoney().getSpent() != null) {
            bean.setCharge(Double.parseDouble(order.getMoney().getSpent()));
        }

        bean.setRemains(order.getUsers().getLimitTotal() - order.getUsers().getCurrent());

        //У VkSerfing возмжны переборы по заказу
        //Возможно это они сейчас убрали и это не нужно
        //Типо в left они выводят сколько дополнительно они сейчас выдают от себя бонусом
        if (bean.getRemains() < 0) {
            int addCount = -bean.getRemains();
            bean.setQuantity(bean.getQuantity() + addCount);
            bean.setRemains(0);
        }

        if (VkSerfingOrderStatus.on.name().equals(order.getStatus()) ||
                VkSerfingOrderStatus.in_queue.name().equalsIgnoreCase(order.getStatus())) {
            bean.setStatus(InstaOrderTaskStatus.InProgress);
        } else if (VkSerfingOrderStatus.finished.name().equals(order.getStatus())) {
            bean.setStatus(InstaOrderTaskStatus.Completed);
        } else {
            bean.setStatus(InstaOrderTaskStatus.Interrupted);
        }
    }

    private VkSerfingTargeting targeting(IOBean bean) {
        return new VkSerfingTargeting(bean);
    }

    @Override
    public void runFollowers(IOFollowersTaskBean taskBean) throws ServiceTemporaryException {
        VkSerfingCreateOrderData order = createOrder(taskBean.getOrder().accountUrl(),
                VkSerfingOrderType.instagram_follower, taskBean.getQuantity(), targeting(taskBean.getOrder()));
        InstagramUserBean user = taskBean.getOrder().getUser();
        if (user != null) {
            taskBean.setStartCount(user.getUserStats().getFollowsCount());
        }
        updateAfterCreate(taskBean, order);

    }

    private void updateAfterCreate(IOTaskBean taskBean, VkSerfingCreateOrderData order) throws ServiceTemporaryException {
        taskBean.setExtOrderId(Integer.toString(order.getId()));
        update(taskBean);
    }

    @Override
    public void runLikes(IOLikesTaskBean taskBean) throws ServiceTemporaryException {
        VkSerfingCreateOrderData order = createOrder(taskBean.url(),
                VkSerfingOrderType.instagram_like, taskBean.getQuantity(), targeting(taskBean.getOrder()));
        updateAfterCreate(taskBean, order);
    }

    @Override
    public void runComments(IOCommentsTaskBean taskBean) throws ServiceTemporaryException {
        String params = VkSerfingOrderType.instagram_comment.createOrderParams(taskBean.url(), taskBean.getQuantity());
        String comments = taskBean.getComments();
        boolean notEmptyComments = StringHelper.isNotEmpty(comments);
        params += String.format(VkSerfingCheatProvider.CREATE_ORDER_COMMENTS_PARAMS,
                notEmptyComments ? VkSerfingCommentsType.custom : VkSerfingCommentsType.positive);

        if (notEmptyComments) {
            //кастомные комментарии
            params += "&comments=" + jsonService.getString(comments(comments));
        }
        VkSerfingCreateOrderData order = createOrder(params, targeting(taskBean.getOrder()));
        updateAfterCreate(taskBean, order);
    }

    private List<VkSerfingComment> comments(String comments) {
        String[] split = comments.split(StringHelper.END_LINE);
        return Arrays.stream(split).map(VkSerfingComment::new).collect(Collectors.toList());
    }

    @Override
    public void runViews(IOViewsTaskBean taskBean) throws ServiceTemporaryException {
        VkSerfingCreateOrderData order = createOrder(taskBean.url(),
                VkSerfingOrderType.instagram_view_video, taskBean.getQuantity(), targeting(taskBean.getOrder()));
        updateAfterCreate(taskBean, order);
    }

    @Override
    public String getApiKey() {
        return "werwerwer";
    }

    @Override
    public String getApiUrl() {
        return "https://vkserfing.ru/";
    }

    @Override
    public void refill(IOTaskBean bean) throws ServiceTemporaryException {
        throw new UnsupportedOperationException();
    }
}
