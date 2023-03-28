/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

/**
 * @author Усов Андрей
 * @since 07/01/2020
 */
public enum InstaProduct {

    followers {
        @Override
        public boolean byCode(String code) {
            return code.contains("Followers");
        }

        @Override
        public String getCode() {
            return "Followers";
        }

        @Override
        double getCost(IOStats ioStats) {
            return ioStats.getFollowersCharge();
        }
    },
    likes {
        @Override
        double getCost(IOStats ioStats) {
            return ioStats.getLikesCharge();
        }
    },
    views {
        @Override
        double getCost(IOStats ioStats) {
            return ioStats.getViewsCharge();
        }
    },
    comments {
        @Override
        double getCost(IOStats ioStats) {
            return ioStats.getCommentsCharge();
        }
    };

    public String getCode() {
        return name();
    }

    public boolean byCode(String code) {
        return name().equals(code);
    }

    public static InstaProduct getByCode(String code) {
        for (InstaProduct instaProduct : values()) {
            if (instaProduct.byCode(code)) {
                return instaProduct;
            }
        }
        throw new IllegalStateException("Not found insta product by code:" + code);
    }

    abstract double getCost(IOStats ioStats);
}
