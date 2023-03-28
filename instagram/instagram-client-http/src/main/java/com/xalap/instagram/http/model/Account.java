/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import com.xalap.instagram.api.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 21.04.17
 */
public class Account {

    private User user;
    private FollowedBy followedBy;
    private Follows follows;
    //Статистика изменений
    private List<StatsData> stats;
    private Data<String> followName;//Из какой стратегии follow этот аккаунт был добавлен
    private Date lastReadTime;

    public Account() {
        this.followedBy = new FollowedBy();
        this.follows = new Follows();
        this.stats = new ArrayList<>();
    }

    public Account(Account account) {
        user = account.user;
        if (account.followedBy != null) {
            followedBy = account.followedBy;
        }
        if (account.follows != null) {
            follows = account.follows;
        }
        followName = account.followName;
        if (account.stats != null) {
            stats = account.stats;
            if (user != null) {
                stats.add(new StatsData(new Stats(user)));
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FollowedBy getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(FollowedBy followedBy) {
        this.followedBy = followedBy;
    }

    public Follows getFollows() {
        return follows;
    }

    public void setFollows(Follows follows) {
        this.follows = follows;
    }

    public List<StatsData> getStats() {
        return stats;
    }

    public void setStats(List<StatsData> stats) {
        this.stats = stats;
    }

    public void updateUser(User user) {
        setUser(user);
        setLastReadTime(new Date());
    }

    @Override
    public String toString() {
        return "Account{" +
                "user=" + user +
                ", followedBy=" + followedBy +
                ", follows=" + follows +
                ", stats=" + stats +
                '}';
    }

    public Data<String> getFollowName() {
        return followName;
    }

    public void setFollowName(Data<String> followName) {
        this.followName = followName;
    }

    public Date getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(Date lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public static class FollowedBy extends Follow<FollowedByData, UnFollowedByData> {
        @Override
        protected FollowedByData createFollow(UserActivity userActivity) {
            return new FollowedByData(userActivity);
        }

        @Override
        protected UnFollowedByData createUnFollow(UserActivity userActivity) {
            return new UnFollowedByData(userActivity);
        }
    }

    public static class Follows extends Follow<FollowsData, UnFollowsData> {
        @Override
        protected FollowsData createFollow(UserActivity userActivity) {
            return new FollowsData(userActivity);
        }

        @Override
        protected UnFollowsData createUnFollow(UserActivity userActivity) {
            return new UnFollowsData(userActivity);
        }
    }
}
