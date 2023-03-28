/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.google;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.framework.utils.WebHelper;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.xalap.framework.utils.HttpClientUtils.post;

/**
 * @author Усов Андрей
 * @since 08/05/2019
 */
@Service
public class GoogleMeasureProtocolService {

    private static final String URL = "https://www.google-analytics.com/collect";

    @Value("${google.analytics.tracking-id:}")
    private String trackingId;
    @Value("${google.analytics.site-url:}")
    private String siteUrl;
    @Value("${google.analytics.order-description:}")
    private String orderDescription;

    /**
     * v=1              // Version.
     * &tid=UA-XXXXX-Y  // Tracking ID / Property ID.
     * &cid=555         // Anonymous Client ID.
     * <p>
     * &t=event         // Event hit type
     * &ec=video        // Event Category. Required.
     * &ea=play         // Event Action. Required.
     * &el=holiday      // Event label.
     * &ev=300          // Event value.
     */
    public void goal(LeadBean leadBean, String category, String action) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("v=1&t=pageview&tid=%s&t=event&ec=%s&ea=%s", trackingId, category, action));
        addClientUserId(sb, leadBean);
        String payload = sb.toString();
        exec(payload);
    }

    private void exec(String payload) {
        try {
            post(URL, ContentType.TEXT_PLAIN, payload);
        } catch (IOException e) {
            throw new IllegalStateException("Error on post data to google analytics:" + payload, e);
        }
    }

    public void transaction(OrderBean bean, List<OrderLineBean> orderLineBeanList) {
        exec(transactionPayload(bean, orderLineBeanList));
    }

    //"v=1&t=pageview&tid=UA-103123029-3&cid=555&dh=instafame.ru&dp=%2F&dt=Main&ti=T12345&tr=37.39&tt=2.85&pa=purchase&pr1id=P12345&pr1nm=Android%20Warhol%20T-Shirt"
    private String transactionPayload(OrderBean bean, List<OrderLineBean> orderLineBeanList) {
        StringBuilder sb = new StringBuilder();
        String transactionId = DateHelper.format(bean.getCreateTime(), "yyyy-MM-dd") + "." + bean.getOrderId();
        sb.append(String.format("v=1&t=pageview&tid=%s&dh=" + siteUrl + "&dp=%s&dt=%s&ti=%s&tr=%s&tt=%s&pa=purchase",
                transactionId, "%2F", WebHelper.encodeURL(orderDescription), transactionId, string(bean.getRevenue()), string(bean.getClientCost() - bean.getRevenue())));
        addClientUserId(sb, bean.getLead());
        int index = 1;
        for (OrderLineBean orderLineBean : orderLineBeanList) {
            String prefix = "&pr" + index;
            sb.append(prefix).append("id=").append("O").append(orderLineBean.getId());
            sb.append(prefix).append("nm=").append(WebHelper.encodeURL(orderLineBean.getLineText()));
            sb.append(prefix).append("pr=").append(string(orderLineBean.getPrice()));
            sb.append(prefix).append("qt=").append(orderLineBean.getQuantity());
            index++;
        }
        return sb.toString();
    }

    private void addClientUserId(StringBuilder sb, LeadBean leadBean) {
        String clientId = leadBean.getClientId();
        if (clientId != null) {
            sb.append("&cid=").append(clientId);
        }
        String contactId = "U" + leadBean.getContact().getId();
        sb.append("&uid=").append(contactId);
    }

    private String string(Double value) {
        return StringHelper.toString(value).replace(",", ".");
    }

}
