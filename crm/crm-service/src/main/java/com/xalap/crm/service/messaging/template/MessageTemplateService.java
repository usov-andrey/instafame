/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.template;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactDataHolder;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactWithDataBean;
import com.xalap.framework.data.CrudService;
import com.xalap.framework.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 07/06/2019
 */
@Service
public class MessageTemplateService extends CrudService<MessageTemplateBean, MessageTemplateRepository, Integer> {

    @Autowired
    private ContactDataService contactDataService;

    public MessageTemplateService() {
        super();
    }

    public String process(MessageTemplateBean bean, ContactWithDataBean contactBean) {
        return process(bean, contactBean, new HashMap<>());
    }

    public String process(String templateCode, ContactBean contactBean) {
        return process(templateCode, contactBean, new HashMap<>());
    }

    /**
     * В шаблон с кодом templateCode подставляем ключевые слова для аккаунта
     */
    public String process(String templateCode, ContactBean contactBean, Map<String, String> keywordValues) {
        return process(templateCode, contactDataService.getContactWithData(contactBean), keywordValues);
    }

    public String process(String templateCode, ContactDataHolder contactDataHolder) {
        return process(templateCode, contactDataHolder, new HashMap<>());
    }

    public String process(String templateCode, ContactDataHolder contactDataHolder, Map<String, String> keywordValues) {
        Optional<MessageTemplateBean> templateBeanOptional = getTemplateByCode(templateCode);
        if (templateBeanOptional.isEmpty()) {
            throw new IllegalStateException("Not found template by code:" + templateCode);
        }
        return process(templateBeanOptional.get(), contactDataHolder, keywordValues);
    }

    private String process(MessageTemplateBean bean, ContactDataHolder contactDataHolder, Map<String, String> keywordValues) {
        String text = bean.getText();
        for (MessageTemplateContactKeyword contactKeyword : MessageTemplateContactKeyword.values()) {
            text = process(text, contactKeyword.name(), contactKeyword.getValue(contactDataHolder));
        }
        for (String keywordName : keywordValues.keySet()) {
            text = process(text, keywordName, keywordValues.get(keywordName));
        }
        return text;
    }

    private String process(String text, String keywordName, String keywordValue) {
        return StringHelper.replace(text, "{{" + keywordName + "}}", keywordValue);
    }

    public Optional<MessageTemplateBean> getTemplateByCode(String templateCode) {
        MessageTemplateBean templateBean = repository().findByCode(templateCode);
        return templateBean != null ? java.util.Optional.of(templateBean) : java.util.Optional.empty();
    }

    public Map<String, String> keyword(String keywordName, String keywordValue) {
        Map<String, String> values = new HashMap<>();
        values.put(keywordName, keywordValue);
        return values;
    }
}
