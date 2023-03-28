/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider;

import com.xalap.framework.data.CrudService;
import org.springframework.stereotype.Service;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Service
public class CheatTaskProviderService extends CrudService<CheatTaskProviderBean, CheatTaskProviderRepository, Integer> {

    public CheatTaskProviderService() {
        super();
    }

    /**
     * Активен сейчас провайдер с именем name или нет
     */
    public boolean isActive(String name) {
        CheatTaskProviderBean byName = repository().findByName(name);
        return byName != null && byName.isActive();
    }
}
