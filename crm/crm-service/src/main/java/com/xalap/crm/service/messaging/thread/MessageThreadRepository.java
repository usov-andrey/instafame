/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.thread;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 04/06/2019
 */
@Repository
public interface MessageThreadRepository extends PageableRepository<MessageThreadBean, Integer> {

    @EntityGraph(attributePaths = {"contact"})
    List<MessageThreadBean> findAll();

    @EntityGraph(attributePaths = {"contact"})
    MessageThreadBean findByContact(ContactBean contact);

}
