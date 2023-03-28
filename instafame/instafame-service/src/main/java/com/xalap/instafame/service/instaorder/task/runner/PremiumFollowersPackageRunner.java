/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.runner;

import com.xalap.framework.utils.DateHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOFollowersPackage;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.settings.IORestoreType;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;
import lombok.val;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Усов Андрей
 * @since 17/04/2019
 */
public class PremiumFollowersPackageRunner extends FollowersPackageRunner {

    public PremiumFollowersPackageRunner(IOTaskService service, IOTaskProvider taskProvider) {
        super(service, taskProvider);
    }

    @Override
    protected void orderFollowersForNextPeriod(IOBean bean, Date date, int followersPerDay) {
        //Если доступна выдача лучших подписчиков, то распределяем 50 на 50
        Optional<CheatTaskBean> bestFollowersTaskOptional = getBestFollowersTaskOptional();
        Optional<CheatTaskBean> premiumCheatTaskOptional = getPremiumCheatTaskOptional(bean);
        if (bestFollowersTaskOptional.isPresent() && premiumCheatTaskOptional.isPresent()) {
            val bestCheatTaskBean = bestFollowersTaskOptional.get();
            val premiumCheatTaskBean = premiumCheatTaskOptional.get();
            if (bestCheatTaskBean.getMin() + premiumCheatTaskBean.getMin() <= followersPerDay) {
                //Для vip пакета мы выдаем наполовину лучших подписчиков, для премиум только одну треть.
                int bestFollowers = bean.getFollowersPackage().equals(IOFollowersPackage.vip) ? followersPerDay / 2 :
                        followersPerDay / 3;
                if (bestFollowers > bestCheatTaskBean.getMax()) {
                    bestFollowers = bestCheatTaskBean.getMax();
                }
                if (bestFollowers < bestCheatTaskBean.getMin()) {
                    bestFollowers = bestCheatTaskBean.getMin();
                }
                service.orderFollowers(bean, bestCheatTaskBean, bestFollowers, null, date);
                followersPerDay = followersPerDay - bestFollowers;
            }
        }
        orderPremiumFollowersPerDay(bean, followersPerDay, date);
    }

    private void orderPremiumFollowersPerDay(IOBean bean, int followersPerDay, Date date) {
        Optional<CheatTaskBean> premiumCheatTaskOptional = getPremiumCheatTaskOptional(bean);
        premiumCheatTaskOptional.ifPresent(cheatTaskBean -> orderFollowers(bean, date, followersPerDay, cheatTaskBean));
    }

    @Override
    public void restore(IOBean parentBean) {
        if (service.getSettings().getRestoreType() != IORestoreType.ADD_NEW_TASK) {
            refill(parentBean);
        }

        val instaOrderStats = new IOStats(parentBean, taskProvider.getTasks(parentBean));
        /*
        Считаем, что 300% подписчиков за месяц восстанвления - то максимум, что мы можем дать
        Если больше, то значит у него до этго были накрученные подписчики
        Считаем, что 300% - это 30 дней, находим сколько сейчас мы можем дать максимум процентов подписчиков для текущего дня
        И если эти проценты больше чем текущий процент выданных подписчиков, то добавляем подписчиков
         */
        //
        //Сколько процентов от исходного всего мы можем восстановить за все время(200%-300%)
        int maxPercent = parentBean.getRestoreMaxPercent() != null ?
                parentBean.getRestoreMaxPercent() :
                service.getSettings().getRestoreMaxPercent();
        //До скольки процентов восстанавливать(100-110%)
        int restoreLevel = parentBean.getRestoreLevelPercent() != null ?
                parentBean.getRestoreLevelPercent() :
                service.getSettings().getRestoreLevelPercent();
        //Сколько минимально подписчиков мы можем заказать при восстановлении
        int restoreMinFollowers = parentBean.getRestoreMinFollowers() != null ?
                parentBean.getRestoreMinFollowers() :
                service.getSettings().getRestoreMinFollowers();
        if (maxPercent <= 100) {
            //Значит восстанавливать не нужно
            return;
        }

        int followersMaxPercent = parentBean.refillingHours().currentPercent() * (maxPercent - 100) / 100 + 100;
        //Какой процент у него сейчас относительно идеального(если бы не отписывались подписчики)
        int realFollowerPercent = instaOrderStats.getFollowers().currentPercent();
        if (followersMaxPercent > realFollowerPercent) {
            //Делаем до 110 процентов подпиисчиков, но не больше 300% за весь заказ
            int maxFollowers = instaOrderStats.getFollowers().getMax() * maxPercent / 100 - instaOrderStats.getFollowers().getCurrent();
            int followers = instaOrderStats.getRealFollowers().getMax() * restoreLevel / 100 - instaOrderStats.getRealFollowers().getCurrent();
            if (followers > maxFollowers) {
                followers = maxFollowers;
            }
            //За раз мы не можем отправлять больше чем половина исходного заказа
            if (followers > instaOrderStats.getFollowers().getMax() * 100 / 150) {
                followers = instaOrderStats.getFollowers().getMax() * 100 / 150;
            }
            Optional<CheatTaskBean> premiumCheatTaskOptional = getPremiumCheatTaskOptional(parentBean);
            if (premiumCheatTaskOptional.isPresent() &&
                    followers >= premiumCheatTaskOptional.get().getMin()
                    && followers >= restoreMinFollowers) {
                Optional<CheatTaskBean> bestFollowersTaskOptional = getBestFollowersTaskOptional();
                if (bestFollowersTaskOptional.isPresent() && bestFollowersTaskOptional.get().equals(premiumCheatTaskOptional.get())) {
                    log.debug("Only Best followers, can't restore:{}", bestFollowersTaskOptional);
                } else {
                    orderPremiumFollowersPerDay(parentBean, followers, new Date());
                }
            }
        } else {
            log.debug("Not restore followers: maxPercent={} current={} order={}",
                    followersMaxPercent, instaOrderStats.getFollowers().currentPercent(), parentBean);
        }
    }

    private void refill(IOBean parentBean) {
        //Находим самый последний заказ подписчиков, у CheatTaskBean которого задано refillDays.
        List<IOTaskBean> followerTasks = taskProvider.getTasks(parentBean, InstaOrderTaskType.followers);
        Optional<IOTaskBean> lastTaskWithRefill = followerTasks.stream()
                .filter(IOTaskBean::refillable)
                .min((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        lastTaskWithRefill.ifPresent(ioTaskBean -> {
            //Если прошло больше суток с момента последнего запуска восстановления, то отправляем запрос еще раз
            if (ioTaskBean.getRefillTime() == null ||
                    DateHelper.daysBetweenDates(ioTaskBean.getRefillTime(), new Date()) > 1) {
                service.refill(ioTaskBean);
            }
        });
    }

    @Override
    public void manualRestore(IOBean parentBean) {
        //Восстанавливаем до 100%
        val instaOrderStats = new IOStats(parentBean, taskProvider.getTasks(parentBean));

        int restoreLevel = parentBean.getRestoreLevelPercent() != null ?
                parentBean.getRestoreLevelPercent() :
                service.getSettings().getRestoreLevelPercent();

        int followers = instaOrderStats.getRealFollowers().getMax() * restoreLevel / 100 - instaOrderStats.getRealFollowers().getCurrent();
        Optional<CheatTaskBean> premiumCheatTaskOptional = getPremiumCheatTaskOptional(parentBean);
        if (premiumCheatTaskOptional.isPresent() && followers >= premiumCheatTaskOptional.get().getMin()) {
            orderPremiumFollowersPerDay(parentBean, followers, new Date());
        }
    }

    private Optional<CheatTaskBean> getPremiumCheatTaskOptional(IOBean bean) {
        if (bean.getFollowersTask() != null && bean.getFollowersTask().activeAndEnabled()) {
            return Optional.of(bean.getFollowersTask());
        }
        return getCheatTaskBean();
    }

    protected Optional<CheatTaskBean> getCheatTaskBean() {
        return service.getSettings().getBestRussianFollowersTask(service.getCheatTaskService());
    }

    private Optional<CheatTaskBean> getBestFollowersTaskOptional() {
        return service.getSettings().getRealFollowersTask();
    }
}
