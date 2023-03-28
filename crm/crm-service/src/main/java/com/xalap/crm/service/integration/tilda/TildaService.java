/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.tilda;

import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.crm.service.contact.ContactWithDataBean;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.lead.LeadWithContactData;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.framework.json.JsonService;
import com.xalap.framework.web.request.HttpRequestProcessor;
import com.xalap.framework.web.request.SerializableHttpRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Service
@AllArgsConstructor
public class TildaService {

    private static final Logger log = LoggerFactory.getLogger(TildaService.class);

    private final Map<String, TildaRequestHandler> requestHandlersByFormId;
    private final List<TildaRequestHandler> requestHandlers;

    private final JsonService converterService;

    private final LeadService leadService;

    private final ContactService contactService;

    private final ContactDataService contactDataService;

    private final HttpRequestProcessor httpRequestProcessor;

    private final OrderLineService orderLineService;

    @PostConstruct
    public void initRequests() {
        httpRequestProcessor.register(TildaSubmitFormRequest.class,
                request -> {
                    String transactionId = request.getTransactionId();
                    if (transactionId == null) {
                        //Генерируем id запроса на основе содержимого параметров этого запроса
                        int keyValuesHash = getRequestParameterValuesHashCode(request);
                        transactionId = Integer.toString(keyValuesHash);
                    }
                    handleRequest(request.getFormId(), transactionId, request);
                });
    }

    /**
     * @return Хэшкод строки конкатенации значений всех параметров запроса
     */
    private int getRequestParameterValuesHashCode(TildaSubmitFormRequest request) {
        List<String> sortedKeys = request.getParametersMap().keySet().stream().sorted().collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (String sortedKey : sortedKeys) {
            sb.append(request.getParameter(sortedKey));
        }
        return sb.toString().hashCode();
    }


    public void addRequestHandler(TildaRequestHandler formListener) {
        requestHandlers.add(formListener);
    }

    public void addRequestHandler(String formId, TildaRequestHandler formListener) {
        requestHandlersByFormId.put(formId, formListener);
    }

    /**
     * Пришел сабмит формы, возможно что-то нужно сделать
     */
    public void handleRequest(String formId, String transactionId, SerializableHttpRequest request) {
        TildaForm tildaForm = new TildaForm(request, converterService);
        for (TildaRequestHandler handler : requestHandlers) {
            handler.handle(transactionId, tildaForm);
        }

        TildaRequestHandler handler = requestHandlersByFormId.get(formId);
        if (handler != null) {
            handler.handle(transactionId, tildaForm);
        } else {
            log.error("Not found formConsumer by formId:{}", formId);
        }
    }

    /**
     * Создаем заявку
     */
    public LeadWithContactData getOrCreateLead(String requestId, TildaForm form, String source,
                                               PipelineStageBean stageBean, LeadBean leadBean,
                                               Function<LeadBean, ContactWithDataBean> createContactFunction) {
        Optional<LeadBean> leadBeanOptional = leadService.repository().findByRequestId(requestId);
        if (leadBeanOptional.isPresent()) {
            LeadBean exLeadBean = leadBeanOptional.get();
            ContactWithDataBean contactWithData = contactDataService.getContactWithData(exLeadBean.getContact());
            return new LeadWithContactData(leadBeanOptional.get(), contactWithData);
        } else {
            leadBean.setCreateTime(new Date());
            leadBean.setSource(source);
            leadBean.setClientId(form.getClientId());
            leadBean.setCookies(form.getCookies());
            leadBean.setReferrer(form.getReferrer());
            leadBean.setUtmSource(form.get("utm_source"));
            leadBean.setUtmMedium(form.get("utm_medium"));
            leadBean.setUtmTerm(form.get("utm_term"));
            leadBean.setUtmCampaign(form.get("utm_campaign"));
            leadBean.setUtmContent(form.get("utm_content"));
            leadBean.setStage(stageBean);
            ContactWithDataBean contactWithDataBean = createContactFunction.apply(leadBean);
            leadBean.setContact(contactWithDataBean.getContact());
            log.debug("Create lead:{}", leadBean);
            leadBean = leadService.save(leadBean);
            return new LeadWithContactData(leadBean, contactWithDataBean);
        }
    }

    /**
     * Создаем заявку
     */
    public LeadWithContactData getOrCreateLead(String requestId, TildaForm form, String source, PipelineStageBean stageBean,
                                               LeadBean leadBean) {
        return getOrCreateLead(requestId, form, source, stageBean, leadBean, this::getOrCreateContact);
    }

    public ContactWithDataBean getOrCreateContact(LeadBean leadBean) {
        return contactService.getOrCreateContact(leadBean.getName(), leadBean.getEmail(),
                leadBean.getInstagram(), leadBean.getClientId());
    }

    public List<OrderLineBean> getOrCreateOrderLines(TildaForm tildaForm, OrderBean orderBean) {
        List<OrderLineBean> result = new ArrayList<>();
        Map<String, Double> products = tildaForm.products();
        for (Map.Entry<String, Double> stringDoubleEntry : products.entrySet()) {
            String productName = stringDoubleEntry.getKey();
            Double price = stringDoubleEntry.getValue();
            if (price > 0) {
                int quantity = 1;
                OrderLineBean bean = orderLineService.getOrCreateOrderLine(orderBean, productName, quantity, price);
                result.add(bean);
            }
        }
        return result;
    }


}
