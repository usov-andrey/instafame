/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.settings;

import com.xalap.framework.data.CrudService;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instagram.direct.service.settings.InstagramDirectSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 08/05/2019
 */
@Service
public class InstaOrderSettingsService extends CrudService<InstaOrderSettingsBean, InstaOrderSettingsRepository, Integer> {

    @Autowired
    private InstaOrderSettingsProvider settingsProvider;
    @Autowired
    private InstagramDirectSettingsService directSettingsService;

    public InstaOrderSettingsService() {
        super();
    }


    public InstaOrderSettingsBean bean() {
        return settingsProvider.get();
    }

    private Optional<CheatTaskBean> optional(CheatTaskBean bean) {
        return bean != null && bean.activeAndEnabled() ? Optional.of(bean) : Optional.empty();
    }

    public Optional<CheatTaskBean> getPremiumFollowersTask() {
        return optional(bean().getPremiumFollowersTask());
    }

    public Optional<CheatTaskBean> getVipFollowersTask() {
        return optional(bean().getVipFollowersTask());
    }

    public Optional<CheatTaskBean> getRealFollowersTask() {
        return optional(bean().getRealFollowersTask());
    }

    public Optional<CheatTaskBean> getCustomCommentsTask() {
        return optional(bean().getCustomCommentsTask());
    }

    public Optional<CheatTaskBean> getViewsTask() {
        return optional(bean().getDefaultViewsTask());
    }

    public Optional<CheatTaskBean> getLikesTask() {
        return optional(bean().getDefaultLikesTask());
    }

    /**
     * Когда на сервисах большая очередь, то тяжело выполнять сроки доставки подписчиков, поэтому включаем максимальную скорость
     */
    public boolean isMaximumFollowersSpeed() {
        return bean().isMaximumFollowersSpeed();
    }

    public Optional<CheatTaskBean> getEconomyCheatTask() {
        return optional(bean().getEconomyFollowersTask());
    }

    public boolean notifyDisabledTask(CheatTaskBean disabledTaskBean) {
        InstaOrderSettingsBean bean = bean();
        return disabledTaskBean.equals(bean.getCustomCommentsTask())
                || disabledTaskBean.equals(bean.getDefaultLikesTask())
                || disabledTaskBean.equals(bean.getDefaultViewsTask());
    }

    @Override
    public void delete(InstaOrderSettingsBean bean) {
        super.delete(bean);
        clearCache();
    }

    @Override
    public InstaOrderSettingsBean save(InstaOrderSettingsBean bean) {
        bean = super.save(bean);
        boolean isAllowToSend = bean.isAllowSendToInstagram();
        clearCache();
        directSettingsService.update(instagramDirectSettingsBean -> instagramDirectSettingsBean.setAllowToSend(isAllowToSend));
        return bean;
    }

    public void clearCache() {
        settingsProvider.clearCache();
    }

    public int getRestoreMaxPercent() {
        return bean().getRestoreMaxPercent();
    }

    public int getRestoreLevelPercent() {
        return bean().getRestoreLevelPercent();
    }

    public int getVipLikesCount() {
        return bean().getVipLikesCount();
    }

    public int getVipCommentsCount() {
        return bean().getVipCommentsCount();
    }

    public boolean isAllowedSendToInstagram() {
        return bean().isAllowSendToInstagram();
    }

    /**
     * Возвращает PremiumFollowersTask или если она не активна, то первую с включенной галочкой russian
     */
    public Optional<CheatTaskBean> getBestRussianFollowersTask(CheatTaskService cheatTaskService) {
        Optional<CheatTaskBean> premiumCheatTask = getPremiumFollowersTask();
        if (premiumCheatTask.isEmpty()) {
            List<CheatTaskBean> tasks = cheatTaskService.getEnabledSortedTasks(InstaOrderTaskType.followers);
            if (!tasks.isEmpty()) {
                //Возвращаем самых дешевых русских активных подписчиков
                premiumCheatTask = tasks.stream().filter(CheatTaskBean::isRussian).findFirst();
            }
        }
        return premiumCheatTask;
    }

    public int getRestoreMinFollowers() {
        return bean().getRestoreMinFollowers();
    }

    public IORestoreType getRestoreType() {
        return bean().getRestoreType();
    }
}
