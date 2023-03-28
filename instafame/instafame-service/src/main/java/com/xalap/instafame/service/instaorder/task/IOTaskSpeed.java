/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

/**
 * @author Усов Андрей
 * @since 22/04/2019
 */
public enum IOTaskSpeed {

    s5(5) {
        @Override
        public IOTaskSpeed next() {
            return s10;
        }
    }, s10(10) {
        @Override
        public IOTaskSpeed next() {
            return s20;
        }
    }, s20(20) {
        @Override
        public IOTaskSpeed next() {
            return s50;
        }
    }, s50(50) {
        @Override
        public IOTaskSpeed next() {
            return s100;
        }
    }, s100(100) {
        @Override
        public IOTaskSpeed next() {
            return s200;
        }
    }, s200(200) {
        @Override
        public IOTaskSpeed next() {
            return this;
        }
    };

    private final Integer speed;

    IOTaskSpeed(Integer speed) {
        this.speed = speed;
    }

    /**
     * Находим ту скорость с которой мы сможем точно получить countForDay подпичсиков за сутки
     */
    public static IOTaskSpeed followersPerDay(int countForDay) {
        int countForHour = countForDay / 24;
        for (IOTaskSpeed ioTaskSpeed : values()) {
            if (ioTaskSpeed.speed >= countForHour) {
                return ioTaskSpeed;
            }
        }

        return s200;
    }

    public Integer getSpeed() {
        return speed;
    }

    abstract public IOTaskSpeed next();

}
