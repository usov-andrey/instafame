/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.venro;

/**
 * @author Усов Андрей
 * @since 13/01/2019
 */
public enum VenroSpeed {

    s2(2),
    s5(5), s10(10), s20(20), s50(50), s100(100), s150(150), s200(200), sMax(null);

    private final Integer speed;

    VenroSpeed(Integer speed) {
        this.speed = speed;
    }

    public static VenroSpeed find(Integer value) {
        if (value == null) {
            return sMax;
        }
        for (VenroSpeed venroSpeed : values()) {
            if (venroSpeed.speed != null && value < venroSpeed.speed) {
                return venroSpeed;
            }
        }
        return sMax;

    }

    public Integer getSpeed() {
        return speed;
    }
}
