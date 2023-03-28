/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.robokassa;

import com.xalap.crm.service.integration.robokassa.receiptattach.Client;
import com.xalap.crm.service.integration.robokassa.receiptattach.Item;
import com.xalap.crm.service.integration.robokassa.receiptattach.Payment;
import com.xalap.crm.service.integration.robokassa.receiptattach.RobokassaReceiptAttachRequest;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.framework.exception.IORuntimeException;
import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.HttpClientUtils;
import com.xalap.framework.utils.StringHelper;
import com.xalap.framework.utils.WebHelper;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Усов Андрей
 * @since 17/01/2019
 */
@Service
public class RobokassaService {

    private static final Logger log = LoggerFactory.getLogger(RobokassaService.class);
    @Autowired
    private JsonService jsonService;
    private final String shopId = "smpromote";//MerchantLogin
    private final String shopUrl = "https://instafame.ru";
    private final String password1 = "PDH6UK9vGexz3L8D5hEc";
    private final String password2 = "LSiNzE1960K4eZjJBOeq";
    private final boolean isTestMode = false;
    private final String testPassword1 = "NJ890dOUrfoPcOEc5e8T";
    private final String testPassword2 = "NX6G1amZt7UhL7dnzW8y";
    private final String SELL_RECEIPT_ATTACH_URL = "https://ws.roboxchange.com/RoboFiscal/Receipt/Attach";
    private final Map<String, Consumer<RobokassaPayment>> paymentListeners = new HashMap<>();

    public void addListener(String shopId, Consumer<RobokassaPayment> formListener) {
        paymentListeners.put(shopId, formListener);
    }

    /**
     * Пришел сабмит формы, возможно что-то нужно сделать
     */
    public void processPayment(String shopId, HttpServletRequest request) {
        log.debug("Process payment:" + WebHelper.getRequestInfoForLogging(request));
        Consumer<RobokassaPayment> consumer = paymentListeners.get(shopId);
        if (consumer != null) {
            RobokassaPayment payment = new RobokassaPayment(request);
            consumer.accept(payment);
        } else {
            log.debug("Not found formConsumer by shopId:" + shopId);
        }
    }

    public void sendReceiptAfterFinishService(OrderBean orderBean, List<OrderLineBean> orderProductBeanList) {
        if (orderProductBeanList.isEmpty()) {
            log.error("In order: " + orderBean + " no have order lines");
            return;
        }
        RobokassaReceiptAttachRequest receiptAttachRequest = getReceiptAttachRequest(orderBean, orderProductBeanList);
        String jsonString = jsonService.getString(receiptAttachRequest);
        //В каждом запросе необходимо произвести замену «+» на «-» и «/» на «_», прежде чем кодировать в base64.
        jsonString = StringHelper.replace(jsonString, "+", "-");
        jsonString = StringHelper.replace(jsonString, "/", "_");
        String base64 = StringHelper.encodeBase64(jsonString);
        //и стираем на конце все знаки =, если имеются.
        while (base64.endsWith("=")) {
            base64 = base64.substring(0, base64.length() - 1 - 1);
        }
        //Создаем SIGNATURE: для этого берем созданный запрос в base64 и приписываем к нему MerchantPass1 магазина (например, robokassatest):
        String signature = base64 + getPassword1();
        //Теперь из полученной конструкции необходимо создать hash в соответствии с тем, что выбрано у магазина в Технических настройках (md5 и тд):
        String hash = hash(signature);
        //Чтобы собрать финальный запрос, который отправляется уже на URL для выбивания чека, необходимо сначала полученный md5 снова зашифровать в base64:
        String hashBase64 = StringHelper.encodeBase64(hash);
        //и стираем на конце все знаки =, если имеются.
        hashBase64 = clearBase64Equals(hashBase64);
        //Для завершения сборки запроса к URL, на котором выбивается чек, берем изначальное тело запроса в base64, ставим точку и приписываем полученный чуть выше signature от md5 в base64:
        String requestBody = base64 + "." + hashBase64;
        //Отправить этот запрос

        String responseBody = exec(requestBody);
        log.debug("Robokassa response:" + responseBody);
        if (!responseBody.contains("\"ok\"")) {
            if (responseBody.contains("не больше 2 чеков")) {
                log.warn("Чек был выдан ранее для " + orderBean);
            } else {
                throw new IllegalStateException("Error on send request to Robokassa to url:" + SELL_RECEIPT_ATTACH_URL + " with body:" +
                        jsonString + " Response:" + responseBody);
            }
        }
    }

    private String exec(String payload) {
        try {
            return HttpClientUtils.post(SELL_RECEIPT_ATTACH_URL,
                    ContentType.APPLICATION_JSON, payload);
        } catch (IOException e) {
            throw new IORuntimeException("Error on post to url:" + SELL_RECEIPT_ATTACH_URL + " with body:" + payload, e);
        }
    }

    private String clearBase64Equals(String base64) {
        while (base64.endsWith("=")) {
            base64 = base64.substring(0, base64.length() - 1 - 1);
        }
        return base64;
    }

    private RobokassaReceiptAttachRequest getReceiptAttachRequest(OrderBean orderBean, List<OrderLineBean> orderProductBeanList) {
        RobokassaReceiptAttachRequest receiptAttachRequest = new RobokassaReceiptAttachRequest();
        receiptAttachRequest.setMerchantId(shopId);
        receiptAttachRequest.setId(orderBean.getId().toString());
        receiptAttachRequest.setOriginId(orderBean.getOrderId());
        receiptAttachRequest.setOperation("sell");
        receiptAttachRequest.setSno("usn_income");
        receiptAttachRequest.setUrl(shopUrl);
        receiptAttachRequest.setTotal(orderBean.getClientCost().intValue());
        Client client = new Client();
        client.setEmail(StringHelper.isNotEmpty(orderBean.getEmail()) ? orderBean.getEmail() : orderBean.getLead().getEmail());
        receiptAttachRequest.setClient(client);
        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment();
        payment.setType(2);
        payment.setSum(orderBean.getClientCost().intValue());
        payments.add(payment);
        receiptAttachRequest.setPayments(payments);
        List<Item> items = new ArrayList<>();
        receiptAttachRequest.setItems(items);
        for (OrderLineBean orderProductBean : orderProductBeanList) {
            Item item = new Item();
            item.setName(orderProductBean.getLineText());
            item.setQuantity(orderProductBean.getQuantity());
            item.setSum(orderProductBean.getPrice().intValue());
            item.setTax("none");
            item.setPaymentMethod("full_prepayment");
            item.setPaymentObject("service");
            items.add(item);
        }
        return receiptAttachRequest;
    }

    private String hash(String value) {
        return StringHelper.md5(value, WebHelper.ENCODING);
    }


    private String getPassword1() {
        return isTestMode ? testPassword1 : password1;
    }

    private String getPassword2() {
        return isTestMode ? testPassword2 : password2;
    }
}
