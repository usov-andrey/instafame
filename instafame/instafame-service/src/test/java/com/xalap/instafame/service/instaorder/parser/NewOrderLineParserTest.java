/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.parser;

import com.xalap.instafame.service.instaorder.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты для NewOrderLineParser
 *
 * @author Usov Andrey
 * @since 2020-07-18
 */
public class NewOrderLineParserTest {

    private IOBean bean;
    private NewOrderLineParser parser;

    @BeforeEach
    public void initUseCase() {
        bean = new IOBean();
        parser = new NewOrderLineParser();
    }

    @Test
    public void testPremiumFollowers() {
        //1000 Премиум подписчиков (50-100 в день)
        parser.parse(bean, "1000 Премиум подписчиков, 50-100 в день");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.premium);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay50_100);
    }

    @Test
    public void testVIPFollowers() {
        //1000 VIP подписчиков (500-1000 в день) + 2000 лайков
        parser.parse(bean, "1000 VIP подписчиков, 500-1000 в день и 2000 лайков");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay500_1000);
        assertThat(bean.getLikesCount()).isEqualTo(2000);

    }

    @Test
    public void testEconomyFollowers() {
        //1000 Эконом подписчиков (100-250 в день)
        parser.parse(bean, "1000 Эконом подписчиков, 100-250 в день");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.economy);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay100_250);
    }

    @Test
    public void testLikes() {
        //5300 лайков (на 1 публ.)
        parser.parse(bean, "5300 лайков, на 1 публ.");
        assertThat(bean.getLikesCount()).isEqualTo(5300);
        assertThat(bean.getLastMediaCount()).isEqualTo(1);
    }

    @Test
    public void testComments() {
        //830 комментариев (на 100 публ.)
        parser.parse(bean, "830 комментариев, на 100 публ.");
        assertThat(bean.getCommentsCount()).isEqualTo(830);
        assertThat(bean.getLastMediaCount()).isEqualTo(100);
    }

    @Test
    public void testVIPRussia() {
        //1000 VIP подписчиков, 250-500 в день, Россия
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Россия");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.russia);
    }

    @Test
    public void testVIPRussiaMoscow() {
        //1000 VIP подписчиков, 250-500 в день, Россия, Москва
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Россия, Москва");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.russia);
        assertThat(bean.getCity()).isEqualTo(IOCity.MOSCOW);
    }

    @Test
    public void testVIPRussiaMinFollowers() {
        //1000 VIP подписчиков, 250-500 в день, Россия, Мин. подписок
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Россия, Минимально подписок");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.russia);
        assertThat(bean.getAccountType()).isEqualTo(IOAccountType.MIN_FOLLOWS);
    }

    @Test
    public void testVIPRussiaPopular() {
        //1000 VIP подписчиков, 250-500 в день, Россия, Мин. подписок
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Россия, Популярный");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.russia);
        assertThat(bean.getAccountType()).isEqualTo(IOAccountType.popular);
    }

    @Test
    public void testVIPRussiaAges() {
        //1000 VIP подписчиков, 250-500 в день, Россия, 10-15 лет
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Россия, 10-15 лет");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.russia);
        assertThat(bean.getMinAge()).isEqualTo(10);
        assertThat(bean.getMaxAge()).isEqualTo(15);
    }

    @Test
    public void testVIPAfter18() {
        //1000 VIP подписчиков, 250-500 в день, Все СНГ, 18+ лет
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Все СНГ, 18+ лет");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.allRussian);
        assertThat(bean.getMinAge()).isEqualTo(18);
    }

    @Test
    public void testVIPBefore18() {
        //1000 VIP подписчиков, 250-500 в день, Все СНГ, 18+ лет
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Все СНГ, До 18 лет");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.allRussian);
        assertThat(bean.getMaxAge()).isEqualTo(17);
    }

    @Test
    public void testVIPMale() {
        //1000 VIP подписчиков, 250-500 в день, Все СНГ, 18+ лет
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Все СНГ, Мужчины");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.allRussian);
        assertThat(bean.getSex()).isEqualTo(IOSex.MALE);
    }

    @Test
    public void testVIPFemale() {
        //1000 VIP подписчиков, 250-500 в день, Все СНГ, 18+ лет
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Все СНГ, Женщины");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.allRussian);
        assertThat(bean.getSex()).isEqualTo(IOSex.FEMALE);
    }


    @Test
    public void testRussiaAll() {
        parser.parse(bean, "1000 VIP подписчиков, 250-500 в день, Россия, Все");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay250_500);
        assertThat(bean.getRegion()).isEqualTo(IORegion.russia);
    }

    @Test
    public void testVip() {
        parser.parse(bean, "2000 VIP подписчиков, 100-250 в день, Казахстан, Санкт-Петербург и 3500 лайков");
        assertThat(bean.getFollowersCount()).isEqualTo(2000);
        assertThat(bean.getLikesCount()).isEqualTo(3500);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.vip);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay100_250);
        assertThat(bean.getRegion()).isEqualTo(IORegion.kazakhstan);
        assertThat(bean.getCity()).isEqualTo(IOCity.ST_PETERSBURG);
    }

    @Test
    public void testLife() {
        parser.parse(bean, "1000 Живых подписчиков, 100-250 в день");
        assertThat(bean.getFollowersCount()).isEqualTo(1000);
        assertThat(bean.getFollowersPackage()).isEqualTo(IOFollowersPackage.live);
        assertThat(bean.getOrderStrategy()).isEqualTo(IOFollowersSpeed.ByDay100_250);
    }
}
