/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.json;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.provider.CheatTaskProvider;
import com.xalap.instafame.service.instaorder.task.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Универсальный
 *
 * @author Usov Andrey
 * @since 21.05.2021
 */
public abstract class JsonCheatTaskProvider<AddOrder, Order, ServiceData, ServiceDataList extends List<ServiceData>>
        extends CheatTaskProvider {

    private static final String CREATE_ORDER = "&action=add&service=%s&quantity=%s&link=%s";
    private static final String ORDER_STATUS = "&action=status&order=%s";
    private static final String CANCEL_ORDER = "&action=cancel&order=%s";
    private static final String PRICES = "&action=services";
    private static final double DEFAULT_RUB_RATE = 1;//По-умолчанию считаем, что расчет идет в рублях
    public static final String ORDER_REFILL = "refill";

    private final Class<AddOrder> addOrderClass;
    private final Class<Order> statusOrderClass;
    private final Class<ServiceDataList> serviceDataListClass;

    public JsonCheatTaskProvider(Class<AddOrder> addOrderClass, Class<Order> statusOrderClass, Class<ServiceDataList> serviceDataListClass) {
        this.addOrderClass = addOrderClass;
        this.statusOrderClass = statusOrderClass;
        this.serviceDataListClass = serviceDataListClass;
    }

    protected abstract void processServiceData(List<CheatTaskBean> result, ServiceData data);

    /**
     * Обновить информацию о заказе из order в bean
     */
    protected abstract void updateOrder(IOTaskBean bean, Order order);

    protected abstract String getExtOrderId(AddOrder addOrder);

    private String getApiUrlWithKey() {
        return getApiUrl() + "?key=" + getApiKey();
    }

    @Override
    public List<CheatTaskBean> getActualTasks() throws ServiceTemporaryException {
        ServiceDataList response = exec(getApiUrlWithKey() + getPricesUrl(), serviceDataListClass);
        List<CheatTaskBean> result = new ArrayList<>();
        for (ServiceData data : response) {
            processServiceData(result, data);
        }
        return result;
    }

    protected String getPricesUrl() {
        return PRICES;
    }

    protected double toRubRate() {
        return DEFAULT_RUB_RATE;
    }

    @Override
    public void abort(IOTaskBean taskBean) throws ServiceTemporaryException {
        String sourceUrl = getApiUrlWithKey() + CANCEL_ORDER;
        String url = String.format(sourceUrl, taskBean.getExtOrderId());
        exec(url, statusOrderClass);
    }

    @Override
    public void update(IOTaskBean bean) throws ServiceTemporaryException {
        Order order = orderStatus(bean.getExtOrderId());
        try {
            updateOrder(bean, order);
        } catch (Exception e) {
            throw new IllegalStateException("Error on update ioTaskBean:" + bean + " by order:" + order);
        }
    }

    @Override
    public void refill(IOTaskBean bean) throws ServiceTemporaryException {
        String sourceUrl = getApiUrlWithKey() + getOrderRefill();
        String url = String.format(sourceUrl, bean.getExtOrderId());
        exec(url, statusOrderClass);
    }

    private Order orderStatus(String orderId) throws ServiceTemporaryException {
        String sourceUrl = getApiUrlWithKey() + getOrderStatusUrl();
        String url = String.format(sourceUrl, orderId);
        return exec(url, statusOrderClass);
    }

    private String getOrderStatusUrl() {
        return ORDER_STATUS;
    }

    private String getOrderRefill() {
        return ORDER_REFILL;
    }

    @Override
    public void runFollowers(IOFollowersTaskBean taskBean) throws ServiceTemporaryException {
        createOrder(taskBean, taskBean.getOrder().getInstagram());
    }

    private void createOrder(IOTaskBean taskBean, String url) throws ServiceTemporaryException {
        CheatTaskBean cheatTask = taskBean.getCheatTask();
        AddOrder addOrder = createOrder(taskBean.getQuantity(), url,
                cheatTask.getServiceNumber());
        String extOrderId = getExtOrderId(addOrder);
        if (StringHelper.isEmpty(extOrderId)) {
            throw new IllegalStateException("Error on create order:" + addOrder + " for taskBean:" + taskBean);
        }
        taskBean.setExtOrderId(extOrderId);
        update(taskBean);
    }

    private AddOrder createOrder(int quantity, String link, int serviceNumber) throws ServiceTemporaryException {
        String sourceUrl = getApiUrlWithKey() + getCreateOrderUrl();
        return exec(String.format(sourceUrl, serviceNumber, quantity, link),
                addOrderClass);
    }

    protected String getCreateOrderUrl() {
        return CREATE_ORDER;
    }

    @Override
    public void runLikes(IOLikesTaskBean taskBean) throws ServiceTemporaryException {
        createOrder(taskBean, taskBean.url());
    }

    @Override
    public void runComments(IOCommentsTaskBean taskBean) throws ServiceTemporaryException {
        createOrder(taskBean, taskBean.url());
    }

    @Override
    public void runViews(IOViewsTaskBean taskBean) throws ServiceTemporaryException {
        createOrder(taskBean, taskBean.url());
    }
}
