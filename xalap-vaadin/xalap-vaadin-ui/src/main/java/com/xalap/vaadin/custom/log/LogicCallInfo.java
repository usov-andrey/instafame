/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Информация для вывода в лог о текущем стектрейсе вызовов
 *
 * @author Usov Andrey
 * @since 2020-04-25
 */
public class LogicCallInfo implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(LogicCallInfo.class);

    private final String logInfo;

    public LogicCallInfo() {
        //Нам нужно залогировать место, где добавляется эта кнопка, для этого используем стек трейс вызова
        /*
        at com.xalap.vaadin.custom.component.Button.forLog(Button.java:47)
	at com.xalap.vaadin.custom.component.Button.<init>(Button.java:23)
	at com.xalap.vaadin.custom.component.MenuButton.<init>(MenuButton.java:16)
	at com.xalap.vaadin.custom.grid.GridButtons.menu(GridButtons.java:60)
	at com.xalap.instafame.ui.crm.InstaFameOrderListFrame.<init>(InstaFameOrderListFrame.java:42)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
	at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:204)
         */
        //Для простоты хардкодим сюда com.xalap.vaadin считая, что бизнес логии в этом пакете не будет, а нам нужна именно она
        Throwable t = new Throwable();
        final String packageName = "com.xalap.vaadin";
        int pos = 1;//Начинаем с 1, так как первая строчка будет forLog
        while (t.getStackTrace()[pos].getClassName().startsWith(packageName)) {
            pos++;
        }
        StackTraceElement element = t.getStackTrace()[pos];
        logInfo = element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber();
    }

    public void log(String caption) {
        log.debug("{}: {}", caption, logInfo);
    }

}
