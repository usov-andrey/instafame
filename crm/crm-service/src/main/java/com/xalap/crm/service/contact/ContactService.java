/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact;

import com.xalap.crm.service.contact.event.ContactEventBean;
import com.xalap.crm.service.contact.event.ContactEventService;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.framework.data.CrudService;
import com.xalap.framework.utils.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Усов Андрей
 * @since 22/02/2019
 */
@Service
public class ContactService extends CrudService<ContactBean, ContactRepository, Integer> {

    @Autowired
    private ContactDataService dataService;
    @Autowired
    private LeadService leadService;
    @Autowired
    private ContactEventService contactEventService;

    public ContactService() {
        super();
    }

    private ContactWithDataBean create(ContactBean bean, ContactData data) {
        bean = save(bean);
        ContactDataBean contactDataBean = dataService.saveContactData(bean, data, new Date());
        return new ContactWithDataBean(bean, contactDataBean);
    }

    //-----------------------New

    /**
     * ClientId задан, заданы e-mail и instagram
     * Если все три равны, то существующий
     * Если любых два равны, то изменяем
     * Если равен только email, то изменяем
     * Если равен только clientId, то новый контакт
     * Если равен только instagram, то новый контакт
     */
    public ContactWithDataBean getOrCreateContact(@NotNull String name, String email, String instagram, String clientId) {
        if (StringHelper.isEmpty(clientId)) {
            return getOrCreateContact(name, email, instagram);
        }

        //clientId не пустой
        if (StringHelper.isEmpty(email) && StringHelper.isNotEmpty(instagram)) {
            ContactData instagramData = ContactDataType.instagram.data(instagram);
            return getOrCreateContact(name, instagramData, clientId);
        }
        if (StringHelper.isEmpty(instagram) && StringHelper.isNotEmpty(email)) {
            ContactData emailData = ContactDataType.email.data(email);
            return getOrCreateContact(name, emailData, clientId);
        }
        ContactData emailData = ContactDataType.email.data(email);
        ContactData instagramData = ContactDataType.instagram.data(instagram);
        ContactData clientIdData = ContactDataType.googleClientId.data(clientId);
        List<ContactWithDataBean> beanList = dataService.getContactWithDataListByFilter(emailData);
        Optional<ContactWithDataBean> foundByEmail = beanList.stream().findFirst();
        if (foundByEmail.isPresent()) {
            Optional<ContactWithDataBean> first = beanList.stream()
                    .filter(contactWithDataBean -> contactWithDataBean.contains(instagramData) &&
                            contactWithDataBean.contains(clientIdData)).findFirst();
            if (first.isPresent()) {
                //Все три совпадают
                return first.get();
            }
            first = beanList.stream()
                    .filter(contactWithDataBean -> contactWithDataBean.contains(instagramData)).findFirst();
            if (first.isPresent()) {
                //Cовпадает e-mail + instagram
                ContactWithDataBean bean = first.get();
                return dataService.save(bean, clientIdData);
            }
            first = beanList.stream()
                    .filter(contactWithDataBean -> contactWithDataBean.contains(ContactDataType.googleClientId.data(instagram))).findFirst();
            if (first.isPresent()) {
                //Cовпадает e-mail + clientId
                ContactWithDataBean bean = first.get();
                return dataService.save(bean, instagramData);
            }
            //Совпадает только e-mail
            ContactWithDataBean bean = foundByEmail.get();
            bean = dataService.save(bean, instagramData);
            return dataService.save(bean, clientIdData);
        }
        //По e-mail не нашли, возможно равны instagram + clientId, тогда изменяем
        beanList = dataService.getContactWithDataListByFilter(instagramData);
        Optional<ContactWithDataBean> first = beanList.stream()
                .filter(contactWithDataBean -> contactWithDataBean.contains(clientIdData)).findFirst();
        if (first.isPresent()) {
            //Cовпадает instagram + clientId
            ContactWithDataBean bean = first.get();
            return dataService.save(bean, emailData);
        }
        //Никакие два не совпадают, e-mail не совпадает, создаем новый контакт
        ContactWithDataBean bean = create(new ContactBean(name), emailData);
        bean = dataService.save(bean, instagramData);
        return dataService.save(bean, clientIdData);
    }


    /**
     * Если не пустой clientId и заполнен только какой-то один из параметров, instagram или email
     * Считаем, что name будет равен этому значению из data
     */
    public ContactWithDataBean getOrCreateContact(ContactData data, String clientId) {
        return getOrCreateContact(data.getValue(), data, clientId);
    }

    /**
     * Если не пустой clientId и заполнен только какой-то один из параметров, instagram или email
     */
    public ContactWithDataBean getOrCreateContact(String name, ContactData data, String clientId) {
        return getOrCreateContact(new ContactBean(name), data, clientId);
    }

    /**
     * Если не пустой clientId
     * задан один из email или instagram
     * <p>
     * email=
     * clientId=Cущ
     * clientId- Измен
     * client    Измен
     * email-
     * clientId=Другой
     * clientId-Другой
     * clientId Другой
     */
    private ContactWithDataBean getOrCreateContact(ContactBean newBean, ContactData data, String clientId) {
        final ContactData clientIdData = ContactDataType.googleClientId.data(clientId);
        Optional<ContactWithDataBean> beanOptional = getContact(data, clientIdData);
        if (beanOptional.isPresent()) {
            return beanOptional.get();
        }
        //Если не нашли по основному аттрибуту, то создаем новый
        ContactWithDataBean contactWithDataBean = create(newBean, data);
        return dataService.save(contactWithDataBean, clientIdData);
    }

    /**
     * Пустой clientId и задан instagram и email
     * <p>
     * email=
     * insta= Сущ
     * insta- Сущ
     * insta Сущ
     * email-
     * insta=Cущ
     * insta- Другой
     * insta Другой
     * email
     * insta=Сущ
     * insta-Другой
     * insta Другой
     * <p>
     * Иначе Новый
     * <p>
     * Если словами, сначала находим, что равны оба, потом когда e-mail, поток когда instagram.
     * если ничего не нашли, то создаем новый.
     */
    public ContactWithDataBean getOrCreateContact(String name, String email, String instagram) {
        if (StringHelper.isEmpty(instagram)) {
            if (StringHelper.isEmpty(email)) {
                throw new IllegalStateException("Email and Instagram is empty, can't create contact for " + name);
            }
            //email задан
            ContactData emailData = ContactDataType.email.data(email);
            return getOrCreateContact(new ContactBean(name), emailData);
        }

        final ContactData instagramData = ContactDataType.instagram.data(instagram);
        //Инстаграм задан
        if (StringHelper.isNotEmpty(email)) {
            ContactData emailData = ContactDataType.email.data(email);
            Optional<ContactWithDataBean> beanOptional = getContact(emailData, instagramData);
            if (beanOptional.isPresent()) {
                return beanOptional.get();
            }
            //По e-mail не нашли, может быть найдем по инстаграм, если не найдем, то создадим
            ContactWithDataBean contactWithDataBean = getOrCreateContact(new ContactBean(name), instagramData);
            return dataService.save(contactWithDataBean, emailData);
        } else {
            return getOrCreateContact(new ContactBean(name), instagramData);
        }
    }

    private Optional<ContactWithDataBean> getContact(ContactData main, ContactData second) {
        List<ContactWithDataBean> beanList = dataService.getContactWithDataListByFilter(main);
        Optional<ContactWithDataBean> foundByMain = beanList.stream().findFirst();
        if (foundByMain.isPresent()) {
            Optional<ContactWithDataBean> first = beanList.stream()
                    .filter(contactWithDataBean -> contactWithDataBean.contains(second)).findFirst();
            if (first.isPresent()) {
                //Оба равны
                return first;
            }
            //Нашли по e-mail, но инстаграм не равен, заполняем
            return Optional.of(dataService.save(foundByMain.get(), second));
        }
        return Optional.empty();
    }

    /**
     * Испольузется когда задан e-mail или instagram и пустой clientId
     */
    public ContactWithDataBean getOrCreateContact(ContactData data) {
        return getOrCreateContact(new ContactBean(data.getValue()), data);
    }

    /**
     * Ищем контакт по аттрибуту data, если не находим, то создаем
     */
    private ContactWithDataBean getOrCreateContact(ContactBean newBean, ContactData data) {
        List<ContactWithDataBean> beanList = dataService.getContactWithDataListByFilter(data);
        if (beanList.isEmpty()) {
            return create(newBean, data);
        }
        return beanList.get(0);
    }

    @Transactional
    public void joinContacts(ContactBean deletingContact, ContactBean targetContact) {
        if (deletingContact.equals(targetContact)) {
            return;
        }
        for (ContactDataBean dataBean : dataService.getContactWithData(deletingContact).getContactDataList()) {
            dataService.saveContactData(targetContact, dataBean);
        }
        for (ContactEventBean bean : contactEventService.repository().findByContact(deletingContact)) {
            bean.setContact(targetContact);
            contactEventService.save(bean);
        }
        for (LeadBean leadBean : leadService.repository().findByContact(deletingContact)) {
            leadBean.setContact(targetContact);
            leadService.save(leadBean);
        }
        delete(deletingContact);
    }

    @Cacheable("userContact")
    public ContactBean getUserContact(String userLogin) {
        //Считаем, что в userLogin находится e-mail
        Set<ContactBean> contacts = dataService.getContacts(new ContactData(userLogin, ContactDataType.email));
        if (contacts.size() > 1) {
            throw new IllegalStateException("For user " + userLogin + " found many contacts: " + contacts);
        }
        return contacts.iterator().next();
    }
}
