/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.vaadin.custom.component.entity.EntityForm;
import com.xalap.vaadin.custom.crud.binderfieldscreator.BinderFormFieldsCreator;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Usov Andrey
 * @since 2020-05-07
 */
class IOForm extends EntityForm<IOBean> {

    IOForm(ServiceRef<IOService> service) {
        super(new BinderFormFieldsCreator<>(IOBean.class).noDefaultFields(), service);
        fieldsCreator().addFields(IOBean.CREATE_TIME, IOBean.STATUS, IOBean.STATUS_CHANGE_TIME,
                IOBean.SPEED, IOBean.FOLLOWERS_PER_DAY, IOBean.LAST_MEDIA_COUNT, IOBean.MEDIA_DIRECTION,
                IOBean.PACKAGE, IOBean.FOLLOWERS, IOBean.LIKES, IOBean.COMMENTS, IOBean.ORDER,
                IOBean.RESTORE_MAX, IOBean.RESTORE_LEVEL, IOBean.RESTORE_MIN_FOLLOWERS_FIELD, IOBean.FOLLOWERS_TASK,
                IOBean.REGION, IOBean.CITY, IOBean.SEX, IOBean.ACCOUNT_TYPE, IOBean.MIN_AGE, IOBean.MAX_AGE,
                IOBean.USER, IOBean.INSTAGRAM);
    }
}
