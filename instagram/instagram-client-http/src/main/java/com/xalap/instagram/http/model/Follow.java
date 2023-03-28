/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 23.04.17
 */
public abstract class Follow<F extends Data<UserActivity>, U extends Data<UserActivity>> {

    private ConcurrentHashMap<String, Date> userMap;//Когда какой пользователь стал follow
    private ConcurrentLinkedQueue<F> followData;
    private ConcurrentLinkedQueue<U> unFollowData;
    private String lastObjectId;//Идентификатор последнего прочитанного обьекта - используется для возобновления чтения большого количества подписчиков
    private Date lastUpdateFinishTime;

    public Follow() {
        userMap = new ConcurrentHashMap<>();
        followData = new ConcurrentLinkedQueue<>();
        unFollowData = new ConcurrentLinkedQueue<>();
    }

    public boolean contain(String userName) {
        return userMap.containsKey(userName);
    }

    public ConcurrentHashMap<String, Date> getUserMap() {
        return userMap;
    }

    public void setUserMap(ConcurrentHashMap<String, Date> userMap) {
        this.userMap = userMap;
    }

    public void addFollow(String userName) {
        Date lastFollowDate = lastFollowDate(userName);
        if (lastFollowDate == null) {
            followData.add(createFollow(new UserActivity(userName)));
        }
        userMap.put(userName, new Date());
    }

    protected Date lastFollowDate(String userName) {
        //Ищем самую последнюю подписку пользователя
        Date lastAddFollow = null;
        for (F f : followData) {
            if (f.getObject().getUserName().equals(userName)) {
                lastAddFollow = f.getTime();
            }
        }
        if (lastAddFollow == null) {
            return null;
        }
        for (U u : unFollowData) {
            if (u.getTime().after(lastAddFollow)) {
                return null;
            }
        }
        //Ошибка, добавлять поьлзвоателя в followData не нужно
        return lastAddFollow;
    }

    protected abstract F createFollow(UserActivity userActivity);

    public ConcurrentLinkedQueue<F> getFollowData() {
        return followData;
    }

    public void setFollowData(ConcurrentLinkedQueue<F> followData) {
        this.followData = followData;
    }

    public void addUnFollow(String userName) {
        unFollowData.add(createUnFollow(new UserActivity(userName)));
        userMap.remove(userName);
    }

    protected abstract U createUnFollow(UserActivity userActivity);

    public ConcurrentLinkedQueue<U> getUnFollowData() {
        return unFollowData;
    }

    public void setUnFollowData(ConcurrentLinkedQueue<U> unFollowData) {
        this.unFollowData = unFollowData;
    }

    public <T> List<T> list(Function<String, T> mapFunction) {
        return getUserMap().keySet().stream().map(mapFunction).collect(Collectors.toList());
    }

    public List<String> sortedByDate() {
        List<Map.Entry<String, Date>> entries = new ArrayList<>(getUserMap().entrySet());
        entries.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        return entries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public String getLastObjectId() {
        return lastObjectId;
    }

    public void setLastObjectId(String lastObjectId) {
        this.lastObjectId = lastObjectId;
    }

    public Date getLastUpdateFinishTime() {
        return lastUpdateFinishTime;
    }

    public void setLastUpdateFinishTime(Date lastUpdateFinishTime) {
        this.lastUpdateFinishTime = lastUpdateFinishTime;
    }

    /**
     * Удаляем историю подписок и из списка подписчиков людей, которые были добавлены ранее даты clearDate
     */
    public void clearUsers(Date clearDate) {
        followData.clear();
        new HashSet<>(userMap.keySet()).stream().filter(userName -> userMap.get(userName).before(clearDate)).forEach(userName -> {
            userMap.remove(userName);
        });
    }
}
