/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.parser;

import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.instaorder.*;
import org.springframework.stereotype.Service;

/**
 * Новая реализация парсера строки заказа.
 * Примеры:
 * 1000 Премиум подписчиков, 50-100 в день
 * 1000 VIP подписчиков, 500-1000 в день и 2000 лайков
 * 1000 Эконом подписчиков, 100-250 в день
 * 5300 лайков, на 1 публ.
 * 830 комментариев, на 100 публ.
 *
 * 1000 VIP подписчиков, 250-500 в день, Россия, Популярный
 * 1000 VIP подписчиков, 250-500 в день, Все СНГ, 18+ лет
 * 1000 VIP подписчиков, 250-500 в день, Россия, Москва
 *
 * @author Usov Andrey
 * @since 2020-07-18
 */
@Service
public class NewOrderLineParser {

    private static final String VIP_SEPARATOR = " и ";

    void parse(IOBean bean, String value) {
        try {
            internalParse(bean, value);
        } catch (Exception e) {
            throw new IllegalStateException("Error on parse order line value:" + value + " for bean:" + bean, e);
        }
    }

    private void internalParse(IOBean bean, String value) {
        //Определяем, это VIP заказ или нет по наличию символа +
        boolean plusLikes = value.contains(VIP_SEPARATOR);
        String addValue = "";
        if (plusLikes) {
            addValue = StringHelper.getStringAfter(value, VIP_SEPARATOR);
            value = StringHelper.getStringBefore(value, VIP_SEPARATOR);
        }

        bean.setFollowersPackage(IOFollowersPackage.none);
        for (Type type : Type.values()) {
            type.parse(bean, value);
        }
        if (plusLikes) {
            addLikesActivity(bean, addValue);
        }
    }

    /**
     * Добавляем к подписчикам лайки
     */
    private void addLikesActivity(IOBean bean, String value) {
        Type.likes.parse(bean, value);
    }

    enum Type {
        followers("подписчиков") {
            @Override
            protected void parseCount(IOBean bean, String countValue) {
                //Определяем тип подписчиков
                //Value:1000 Премиум
                String type = StringHelper.getStringAfter(countValue, " ");
                IOFollowersPackage followersPackage = IOFollowersPackage.getValue(type);
                bean.setFollowersPackage(followersPackage);
                countValue = StringHelper.getStringBefore(countValue, " ");
                super.parseCount(bean, countValue);
            }

            @Override
            protected void setCount(IOBean bean, int value) {
                bean.setFollowersCount(value);
            }

            @Override
            protected void parseSpeed(IOBean bean, String speedValue) {
                //50-100 в день
                //Получаем первое значение и его сверяем
                String value = StringHelper.getStringBefore(speedValue, "-").trim();
                int intValue = Integer.parseInt(value);
                for (IOFollowersSpeed followersSpeed : IOFollowersSpeed.values()) {
                    if (followersSpeed.min() == intValue) {
                        bean.setOrderStrategy(followersSpeed);
                    }
                }
            }
        },
        likes("лайков") {
            @Override
            protected void setCount(IOBean bean, int value) {
                bean.setLikesCount(value);
            }
        },
        comments("комментариев") {
            @Override
            protected void setCount(IOBean bean, int value) {
                bean.setCommentsCount(value);
            }
        };

        private final String keyword;

        Type(String keyword) {
            this.keyword = keyword;
        }

        public void parse(IOBean bean, String value) {
            if (value.contains(keyword)) {
                //1000 Премиум подписчиков, 50-100 в день
                //Делим на две части по ключевому слову
                String countValue = StringHelper.getStringBefore(value, keyword).trim();
                parseCount(bean, countValue);
                String additional = StringHelper.getStringAfter(value, keyword).trim();
                if (StringHelper.isNotEmpty(additional)) {
                    //Например:, 250-500 в день, Россия, Москва
                    //Разбиваем по ,
                    String[] split = StringHelper.getStringAfter(value, keyword).split(",");
                    //split[0] - это пустой символ до первой запятой,
                    //split[1] - это скорость/количество публикаций
                    parseSpeed(bean, split[1].trim());
                    if (split.length > 2) {
                        //split[2] - если есть, то это страна
                        parseCountry(bean, split[2].trim());
                        if (split.length > 3) {
                            //split[3] - если есть, то это критерий(Город или Возраст или Тип аккаунтов)
                            parseAdditional(bean, split[3].trim());
                        }

                    }
                }
            }
        }

        private void parseAdditional(IOBean bean, String value) {
            //(Город или Возраст или Тип аккаунтов)
            //Вначале перебираем город
            for (IOCity ioCity : IOCity.values()) {
                if (value.equals(ioCity.getValue())) {
                    bean.setCity(ioCity);
                    return;
                }
            }
            //потом пол
            for (IOSex ioSex : IOSex.values()) {
                if (value.equals(ioSex.getValue())) {
                    bean.setSex(ioSex);
                    return;
                }
            }
            //потом возрастные группы
            if (value.equals("18+ лет")) {
                bean.setMinAge(18);
            }
            if (value.equals("До 18 лет")) {
                bean.setMaxAge(17);
            }
            //потом тип аккаунтов
            for (IOAccountType ioAccountType : IOAccountType.values()) {
                if (value.equals(ioAccountType.getValue())) {
                    bean.setAccountType(ioAccountType);
                    return;
                }
            }
            //Остается возраст(например, 10-15 лет)
            var minAgeSt = StringHelper.getStringBefore(value, "-");
            if (StringHelper.isNotEmpty(minAgeSt)) {
                var minAge = Integer.parseInt(minAgeSt);
                bean.setMinAge(minAge);
            }

            var maxAgeSt = StringHelper.getStringBetween(value, "-", " ");
            if (StringHelper.isNotEmpty(maxAgeSt)) {
                var maxAge = Integer.parseInt(maxAgeSt);
                bean.setMaxAge(maxAge);
            }
        }

        private void parseCountry(IOBean bean, String country) {
            for (IORegion value : IORegion.values()) {
                if (value.getValue().equals(country)) {
                    bean.setRegion(value);
                }
            }
        }

        protected void parseSpeed(IOBean bean, String speedValue) {
            //Может быть и без скорости вообще: 2000 лайков
            if (!StringHelper.isEmpty(speedValue)) {
                //50-100 в день или на 100 публ.
                //Здесь парсим второй вариант, для подписчиков перекрываем
                String value = StringHelper.getStringBetween(speedValue, "на", "публ.").trim();
                bean.setLastMediaCount(Integer.parseInt(value));
            }
        }

        protected void parseCount(IOBean bean, String countValue) {
            setCount(bean, Integer.parseInt(countValue));
        }

        abstract protected void setCount(IOBean bean, int value);

    }
}
