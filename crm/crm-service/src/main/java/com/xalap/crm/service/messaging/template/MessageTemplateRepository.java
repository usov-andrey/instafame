/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.template;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Усов Андрей
 * @since 28/05/2019
 */
@Repository
public interface MessageTemplateRepository extends PageableRepository<MessageTemplateBean, Integer> {

    MessageTemplateBean findByCode(String code);

}
