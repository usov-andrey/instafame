/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service;

import com.xalap.crm.service.contact.ContactDataBean;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactDataType;
import com.xalap.crm.service.scheduler.SchedulerTask;
import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 22/06/2019
 */
public class UpdateInstagramUsersTask extends SchedulerTask {

    @Autowired
    private ContactDataService contactDataService;
    @Autowired
    private InstagramUserService userService;

    @Override
    protected void doRun() {
        for (ContactDataBean dataBean : contactDataService.repository().findByDataType(ContactDataType.instagram)) {
            try {
                Optional<InstagramUserBean> userBean = userService.updateUserWithLastMedia(dataBean.getValue());
                if (userBean.isEmpty() && dataBean.getExpireDate() == null) {
                    dataBean.setExpireDate(new Date());
                    contactDataService.save(dataBean);
                } else if (userBean.isPresent() && dataBean.getExpireDate() != null) {
                    dataBean.setExpireDate(null);
                    contactDataService.save(dataBean);
                }
            } catch (ServiceTemporaryException e) {
                e.log(log, "Error on get user:" + dataBean.getValue());
            }
        }
    }
}
