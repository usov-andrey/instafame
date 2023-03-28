/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.user.follower;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.instagram.service.user.InstagramUserBean;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 25/12/2018
 */
@Entity(name = UserFollowerBean.NAME)
public class UserFollowerBean implements IdHolder<Integer> {

    public static final String NAME = "I$UserFollower";
    public static final String USER_NAME = "userName";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + UserFollowerBean.NAME)
    @SequenceGenerator(name = "sequence-generator" + UserFollowerBean.NAME,
            sequenceName = "SEQ_" + UserFollowerBean.NAME)
    private Integer id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InstagramUserBean user;
    @FieldName(USER_NAME)
    private String followerUserName;
    private Date followDate;//Когда подписчик подписался
    private Date unFollowDate;//Когда подписчик отписался
    private Date notSeenFollowFirstTime;//Время, когда первый раз мы увидели, что его нет в подписчиках

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public InstagramUserBean getUser() {
        return user;
    }

    public UserFollowerBean setUser(InstagramUserBean user) {
        this.user = user;
        return this;
    }

    public String getFollowerUserName() {
        return followerUserName;
    }

    public UserFollowerBean setFollowerUserName(String followerUserName) {
        this.followerUserName = followerUserName;
        return this;
    }

    public Date getFollowDate() {
        return followDate;
    }

    public UserFollowerBean setFollowDate(Date followDate) {
        this.followDate = followDate;
        return this;
    }

    public Date getUnFollowDate() {
        return unFollowDate;
    }

    public UserFollowerBean setUnFollowDate(Date unFollowDate) {
        this.unFollowDate = unFollowDate;
        return this;
    }

    public Date getNotSeenFollowFirstTime() {
        return notSeenFollowFirstTime;
    }

    public void setNotSeenFollowFirstTime(Date notSeenFollowFirstTime) {
        this.notSeenFollowFirstTime = notSeenFollowFirstTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFollowerBean that = (UserFollowerBean) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserFollowerBean{" +
                "id=" + id +
                ", user=" + user +
                ", followerUserName='" + followerUserName + '\'' +
                ", followDate=" + followDate +
                ", unFollowDate=" + unFollowDate +
                ", notSeenFollowFirstTime=" + notSeenFollowFirstTime +
                '}';
    }
}
