/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.user.history;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.utils.DateHelper;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.UserStatsBean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 22/04/2019
 */
@Service
public class UserHistoryService extends CrudService<UserHistoryBean, UserHistoryRepository, Integer> {

    public UserHistoryService() {
        super();
    }

    /**
     * Находим ближайщую статистику о пользователя в указанное время
     *
     * @return null, если за указанный день статистика не сохранена
     */
    public UserStatsBean getDayUserHistory(InstagramUserBean userBean, Date time) {
        if (DateHelper.daysBetweenDates(time, userBean.getCreateTime()) < 1) {
            return userBean.getUserStats();
        } else if (userBean.getUpdateTime() != null && DateHelper.daysBetweenDates(time, userBean.getUpdateTime()) < 1) {
            return userBean.getUserStats();
        }
        List<UserHistoryBean> byUser = repository().findByUser(userBean);
        Collections.sort(byUser, new Comparator<UserHistoryBean>() {
            @Override
            public int compare(UserHistoryBean o1, UserHistoryBean o2) {
                return o1.getCreateTime().compareTo(o2.getCreateTime());
            }
        });
        long minutesDifference = Long.MAX_VALUE;
        UserStatsBean result = null;
        for (UserHistoryBean userHistoryBean : byUser) {
            long current = DateHelper.minutesBetweenDates(userHistoryBean.getCreateTime(), time);
            if (current < minutesDifference) {
                current = minutesDifference;
                if (current < 60 * 24) {//Если разница меньше суток, то записываем в результат
                    result = userHistoryBean.getUserStats();
                }
            } else {
                break;
            }
        }

        return result;
    }
}
