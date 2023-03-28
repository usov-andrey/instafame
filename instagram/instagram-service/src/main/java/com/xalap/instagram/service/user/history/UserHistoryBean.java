/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.user.history;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.instagram.api.user.User;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.UserStatsBean;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Entity(name = UserHistoryBean.NAME)
public class UserHistoryBean implements IdHolder<Integer> {

    public static final String NAME = "I$UserHistory";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    private Date createTime;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InstagramUserBean user;
    private UserStatsBean userStats;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public InstagramUserBean getUser() {
        return user;
    }

    public void setUser(InstagramUserBean user) {
        this.user = user;
    }

    public UserStatsBean getUserStats() {
        return userStats;
    }

    public void setUserStats(UserStatsBean userStats) {
        this.userStats = userStats;
    }

    public void update(User user) {
        if (userStats == null) {
            userStats = new UserStatsBean();
        }
        userStats.update(user);
    }

    @Override
    public String toString() {
        return "UserHistoryBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", user=" + user +
                ", userStats=" + userStats +
                '}';
    }
}
