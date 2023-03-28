/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.settings.InstaOrderSettingsService;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.provider.IOTaskProvider;
import com.xalap.instafame.service.instaorder.task.runner.*;

import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 16/04/2019
 */
public enum IOFollowersPackage {
    /**
     * Любые самые дешевые подписчики, возможно даже боты, с аватарками
     */
    economy(1, "Эконом", "E") {
        @Override
        CheatAutoRunner create(IOTaskService ioTaskService, IOTaskProvider taskProvider) {
            return new EconomyFollowersPackageRunner(ioTaskService, taskProvider);
        }

        @Override
        public boolean noRefill() {
            return true;
        }
    },
    /**
     * Подписчики с гарантией c аватаркой и публикациями
     */
    premium(2, "Премиум", "P") {
        @Override
        CheatAutoRunner create(IOTaskService ioTaskService, IOTaskProvider taskProvider) {
            return new PremiumFollowersPackageRunner(ioTaskService, taskProvider);
        }

        @Override
        public boolean noRefill() {
            return false;
        }
    },
    vip(3, "VIP", "V") {
        @Override
        CheatAutoRunner create(IOTaskService ioTaskService, IOTaskProvider taskProvider) {
            return new VIPFollowersPackageRunner(ioTaskService, taskProvider);
        }

        @Override
        public boolean noRefill() {
            return false;
        }
    },
    none(0, "Нет", "-") {
        @Override
        CheatAutoRunner create(IOTaskService ioTaskService, IOTaskProvider taskProvider) {
            return new EmptyCheatAutoRunner(ioTaskService, taskProvider);
        }

        @Override
        public boolean noRefill() {
            return true;
        }
    },

    ruPremium(4, "Прeмиум", "Ру") {//Используем здесь английскую букву e, чтобы отличать при продаже, но чтобы клиент не заметил
        @Override
        CheatAutoRunner create(IOTaskService ioTaskService, IOTaskProvider taskProvider) {
            return new RuPremiumFollowersPackageRunner(ioTaskService, taskProvider);
        }

        @Override
        public boolean noRefill() {
            return false;
        }
    },

    live(5, "Живых", "L") {
        @Override
        CheatAutoRunner create(IOTaskService ioTaskService, IOTaskProvider taskProvider) {
            return new LiveFollowersPackageRunner(ioTaskService, taskProvider);
        }

        @Override
        public boolean noRefill() {
            return true;
        }
    };

    private final int index;
    /**
     * В родительском падеже
     */
    private final String caption;
    private final String letter;

    IOFollowersPackage(int index, String caption, String letter) {
        this.index = index;
        this.caption = caption;
        this.letter = letter;
    }

    public static IOFollowersPackage getValue(String name) {
        for (IOFollowersPackage followersPackage : values()) {
            if (name.startsWith(followersPackage.caption)) {
                return followersPackage;
            }
        }

        throw new IllegalStateException("Not found package in " + name);
    }

    public String getCaption() {
        return caption;
    }

    public CheatAutoRunner getRunner(IOTaskService ioTaskService, IOTaskProvider taskProvider) {
        return create(ioTaskService, taskProvider);
    }

    abstract CheatAutoRunner create(IOTaskService ioTaskService, IOTaskProvider taskProvider);

    public int getIndex() {
        return index;
    }

    public Optional<CheatTaskBean> getLikesTask(InstaOrderSettingsService settings) {
        return settings.getLikesTask();
    }

    public String letter() {
        return letter;
    }

    public abstract boolean noRefill();
}
