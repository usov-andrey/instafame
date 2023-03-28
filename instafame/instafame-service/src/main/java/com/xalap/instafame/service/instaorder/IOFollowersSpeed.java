/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.framework.utils.DateHelper;

import java.util.Date;

/**
 *
 * @author Усов Андрей
 * @since 06/01/2019
 */
public enum IOFollowersSpeed {

    ByDay100_250("Естественная") {//(100-250 в день)

        @Override
        public int followersPerHour(IOBean bean) {
            return 10;//240 в день
        }

        @Override
        public int min() {
            return 100;
        }
    },
    ByDay250_500("Ускоренная") {//(250-500 в день)

        @Override
        public int followersPerHour(IOBean bean) {
            return 20;//480 в день
        }

        @Override
        public int min() {
            return 250;
        }
    },
    ByDay500_1000("Максимальная") {//(500-1000 в день)

        @Override
        public int followersPerHour(IOBean bean) {
            return 50;//1200 в день
        }

        @Override
        public int min() {
            return 500;
        }
    },
    Custom("Кастомная") {
        @Override
        protected int followersPerHour(IOBean bean) {

            int perHour = bean.getFollowersPerDay() / 24;
            if (perHour == 0) {
                perHour = 1;//Минмально 1 подписчик за час.
            }
            return perHour;
        }

        @Override
        public int min() {
            return 0;
        }
    },
    ByDay50_100("Минимальная") {//(50-100 в день)

        @Override
        protected int followersPerHour(IOBean bean) {
            return 3;//72 в день
        }

        @Override
        public int min() {
            return 50;
        }
    };

    private final String caption;

    IOFollowersSpeed(String caption) {
        this.caption = caption;
    }

    public static IOFollowersSpeed getValue(String value) {
        for (IOFollowersSpeed strategy : values()) {
            if (value.startsWith(strategy.getCaption())) {
                return strategy;
            }
        }
        throw new IllegalStateException("Not found caption by " + value);
    }

    public String getCaption() {
        return caption;
    }

    abstract protected int followersPerHour(IOBean bean);

    abstract public int min();

    public IOStats.Stat progressHours(IOBean bean) {
        int followersPerHour = followersPerHour(bean);
        int max = followersPerHour > 0 ? bean.getFollowersCount() / followersPerHour : 0;
        return new IOStats.Stat(Long.valueOf(DateHelper.hoursBetweenDates(bean.getCreateTime(), new Date())).intValue(),
                max);
    }
}
