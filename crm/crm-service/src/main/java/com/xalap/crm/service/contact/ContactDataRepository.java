/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 14/06/2019
 */
@Repository
public interface ContactDataRepository extends PageableRepository<ContactDataBean, Integer> {

    @EntityGraph(attributePaths = {"contact"})
    List<ContactDataBean> findAll();

    List<ContactDataBean> findByContactAndDataType(ContactBean contact, ContactDataType dataType);

    List<ContactDataBean> findByContactAndDataTypeAndValue(ContactBean contact, ContactDataType dataType, String value);

    List<ContactDataBean> findByDataTypeAndValue(ContactDataType dataType, String value);

    List<ContactDataBean> findByDataType(ContactDataType dataType);

    List<ContactDataBean> findByContact(ContactBean contact);
}
