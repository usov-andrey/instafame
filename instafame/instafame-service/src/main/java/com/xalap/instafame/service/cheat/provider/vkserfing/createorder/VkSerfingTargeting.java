/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.vkserfing.createorder;

import com.xalap.instafame.service.instaorder.*;

/**
 * @author Usov Andrey
 * @since 2020-10-07
 */
public class VkSerfingTargeting {

    private static final int DEFAULT_MIN_POST = 1;//Минимальное количество публикаций
    private static final int DEFAULT_MIN_FOLLOWERS = 1;//Минимальное количество подписчиков
    private static final int DEFAULT_MAX_FOLLOWS = 5000;//Максимальное количество подписок

    private final Sex sex;
    private final Integer minAge;//age_from
    private final Integer maxAge;//age_to
    private Integer minFollowers;//friends_from
    private Integer maxFollowers;//friends_to
    private Integer minFollows;//subscribes_from
    private Integer maxFollows;//subscribes_to
    private Integer minPosts;//records_from
    private Integer maxPosts;//records_to
    private RegType regType;//reg_type Тип времени с момента регистрации исполнителя
    private Integer regNum;//reg_num - Количество месяцев/лет с момента регистрации исполнителя
    private final Country country;//country_id
    private final City city;//city_id

    public VkSerfingTargeting(IOBean bean) {
        sex = Sex.get(bean.getSex());
        minAge = bean.getMinAge();
        maxAge = bean.getMaxAge();
        country = Country.get(bean.getRegion());
        city = City.get(bean.getCity());

        if (bean.getAccountType() != null) {
            if (IOAccountType.popular == bean.getAccountType()) {
                //Популярный (более 1000 подписчиков)
                minFollowers = 1000;
            } else if (IOAccountType.active == bean.getAccountType()) {
                //Активный (более 100 публикаций)
                minPosts = 100;
            } else if (IOAccountType.MIN_FOLLOWS == bean.getAccountType()) {
                //Минимально подписок (не больше 50 подписок)
                maxFollows = 50;
            } else if (IOAccountType.ALL == bean.getAccountType()) {
                minPosts = DEFAULT_MIN_POST;
                minFollowers = DEFAULT_MIN_FOLLOWERS;
                maxFollows = DEFAULT_MAX_FOLLOWS;
            } else {
                throw new IllegalStateException("Не найден обработчик для " + bean.getAccountType());
            }

        }
    }


    public String processUrl(String url) {
        return url + new Builder().processUrl().toString();
    }

    enum RegType {
        month, year
        //month - месяц
        //    year - год
    }

    enum Sex {
        male(2, IOSex.MALE),
        female(1, IOSex.FEMALE );

        private final int value;
        private final IOSex sex;

        Sex(int value, IOSex sex) {
            this.value = value;
            this.sex = sex;
        }

        public static Sex get(IOSex sex) {
            for (Sex value : values()) {
                if (value.sex.equals(sex)) {
                    return value;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }
    }


    enum Country {
        russia(1, IORegion.russia),
        /**
         * Украина
         */
        ukraine(2, IORegion.ukraine),
        /**
         * Беларусь
         */
        belarus(3, IORegion.belarus),
        /**
         * Казахстан
         */
        kazakhstan(4, IORegion.kazakhstan);

        private final int value;
        private final IORegion region;

        Country(int value, IORegion region) {
            this.value = value;
            this.region = region;
        }

        public static Country get(IORegion region) {
            for (Country value : values()) {
                if (value.region.equals(region)) {
                    return value;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }
    }

    enum City {
        moscow(1, IOCity.MOSCOW),
        stPeterburg(2, IOCity.ST_PETERSBURG);

        private final int value;
        private final IOCity city;

        City(int value, IOCity city) {
            this.value = value;
            this.city = city;
        }

        public static City get(IOCity city) {
            for (City value : values()) {
                if (value.city.equals(city)) {
                    return value;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }
    }

    private class Builder {

        private final StringBuilder sb = new StringBuilder();

        public StringBuilder processUrl() {
            if (sex != null) {
                add("sex", sex.value);
            }
            add("age_from", minAge);
            add("age_to", maxAge);
            add("friends_from", minFollowers);
            add("friends_to", maxFollowers);
            add("subscribes_from", minFollows);
            add("subscribes_to", maxFollows);
            add("records_from", minPosts);
            add("records_to", maxPosts);
            if (add("reg_num", regNum)) {
                add("reg_type", regType);
            }
            if (country != null) {
                add("country_id", country.value);
            }
            if (city != null) {
                add("city_id", city.value);
            }
            return sb;
        }

        private boolean add(String name, Object value) {
            if (value != null) {
                sb.append("&").append(name).append("=").append(value);
                return true;
            }
            return false;
        }
    }
}
