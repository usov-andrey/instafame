/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.direct;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.framework.exception.ServiceTemporaryException;

/**
 * api для отправки/получений сообщений в инстаграм
 *
 * @author Usov Andrey
 * @since 2020-10-01
 */
public interface InstagramDirectService {

    void init();

    MessageBean sendMessage(Integer contactId, String instagram, String text);

    void updateThreadMessages(String instagram) throws ServiceTemporaryException;

    void asyncUpdateAndAccept();

    void sendTemplate(ContactBean contactBean, String instagram, String text);
}
