/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Усов Андрей
 * @since 10/07/2019
 */
@Component
public class InstaOrderSettingsProvider {

    private static final Integer ID = 0;

    @Autowired
    private InstaOrderSettingsRepository repository;

    @PostConstruct
    public void init() {
        if (!repository.findById(ID).isPresent()) {
            InstaOrderSettingsBean bean = new InstaOrderSettingsBean();
            bean.setId(ID);
            repository.save(bean);
        }
    }

    @Cacheable("instaOrderSettings")
    public InstaOrderSettingsBean get() {
        return repository.findById(ID).orElse(null);
    }

    @CacheEvict("instaOrderSettings")
    public void clearCache() {

    }

}
