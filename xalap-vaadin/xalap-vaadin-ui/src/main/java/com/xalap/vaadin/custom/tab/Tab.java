/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.tab;

import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.custom.data.DataHolder;

import java.io.Serializable;

/**
 * Вкладка хранит информацию о родительском бине
 *
 * @author Усов Андрей
 * @since 07/05/2019
 */
public class Tab<PARENT_BEAN extends Serializable> extends CustomVerticalLayout {

    protected DataHolder<PARENT_BEAN> parentBeanHolder = new DataHolder<PARENT_BEAN>();

    protected PARENT_BEAN getParentBean() {
        return parentBeanHolder.getValue();
    }

    public void setParentBean(PARENT_BEAN parentBean) {
        parentBeanHolder.setValue(parentBean);
    }

}
