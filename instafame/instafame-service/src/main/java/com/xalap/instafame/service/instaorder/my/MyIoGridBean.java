/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.my;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.IOStatus;
import com.xalap.instagram.service.user.InstagramUserBean;

import java.io.Serializable;


/**
 * @author Usov Andrey
 * @since 2020-05-14
 */
public class MyIoGridBean implements Serializable {

    private final IOBean ioBean;
    private IOStats ioStats;

    public MyIoGridBean(IOBean ioBean) {
        this.ioBean = ioBean;
    }

    public IOBean getIoBean() {
        return ioBean;
    }

    public MyIoStatus getStatus() {
        if (ioBean.getInstagram() == null) {
            return MyIoStatus.stoped;
        }
        IOStatus status = ioBean.getStatus();
        if (status == IOStatus.Waiting) return MyIoStatus.stoped;
        return status == IOStatus.Completed ? MyIoStatus.completed :
                MyIoStatus.actived;
    }

    public InstagramUserBean getUser() {
        return ioBean.getUser();
    }

    public String getProgress() {
        StringBuilder text = new StringBuilder();
        for (Type value : Type.values()) {
            value.append(text, ioStats);
        }
        return text.toString();
    }

    public IOStats getIoStats() {
        return ioStats;
    }

    public void setIoStats(IOStats ioStats) {
        this.ioStats = ioStats;
    }

    public enum Type {
        followers("Подписчики") {
            @Override
            protected IOStats.Stat getStat(IOStats ioStats) {
                return ioStats.getFollowers();
            }
        },
        likes("Лайки") {
            @Override
            protected IOStats.Stat getStat(IOStats ioStats) {
                return ioStats.getLikes();
            }
        },
        comments("Комментарии") {
            @Override
            protected IOStats.Stat getStat(IOStats ioStats) {
                return ioStats.getComments();
            }
        },
        views("Просмотры") {
            @Override
            protected IOStats.Stat getStat(IOStats ioStats) {
                return ioStats.getViews();
            }
        };

        private final String caption;

        Type(String caption) {
            this.caption = caption;
        }

        public void append(StringBuilder text, IOStats ioStats) {
            IOStats.Stat stat = getStat(ioStats);
            if (stat.getMax() > 0) {
                int percent = stat.currentPercent();
                if (percent > 100) {
                    percent = 100;
                }
                text.append(caption).append(":").append(percent).append("% из ").append(stat.getMax()).append(" ");
            }
        }

        public String percent(MyIoGridBean bean) {
            IOStats.Stat stat = getStat(bean.ioStats);
            return stat.getCurrent() + " из " + stat.getMax();
        }

        public boolean isExists(MyIoGridBean bean) {
            IOStats.Stat stat = getStat(bean.ioStats);
            return stat.getMax() > 0;
        }

        abstract protected IOStats.Stat getStat(IOStats ioStats);
    }
}
