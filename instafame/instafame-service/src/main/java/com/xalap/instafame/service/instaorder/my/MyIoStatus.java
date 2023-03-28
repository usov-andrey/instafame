/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.my;

import com.xalap.framework.domain.holder.NameHolder;
import com.xalap.instafame.service.instaorder.IOStatus;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Usov Andrey
 * @since 2020-05-14
 */
public enum MyIoStatus implements NameHolder {

    all("Все статусы", "Все") {
        @Override
        Collection<IOStatus> statuses() {
            return IOStatus.statuses(ioStatus -> ioStatus != IOStatus.Created);
        }
    },
    actived("Активные", "Активен") {
        @Override
        Collection<IOStatus> statuses() {
            return IOStatus.statuses(ioStatus -> ioStatus != IOStatus.Created
                    && ioStatus != IOStatus.Waiting && ioStatus != IOStatus.Completed);
        }
    },
    stoped("Остановленные", "Остановлен") {
        @Override
        Collection<IOStatus> statuses() {
            return Collections.singletonList(IOStatus.Waiting);
        }
    },
    completed("Завершенные", "Завершен") {
        @Override
        Collection<IOStatus> statuses() {
            return Collections.singletonList(IOStatus.Completed);
        }
    };

    private final String category;
    private final String name;

    MyIoStatus(String category, String name) {
        this.category = category;
        this.name = name;
    }

    @Override
    public String getName() {
        return category;
    }

    public String getStatusName() {
        return name;
    }

    abstract Collection<IOStatus> statuses();
}
