/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.direct.service.settings;

import com.xalap.framework.data.SingleRowService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author Усов Андрей
 * @since 28/01/2020
 */
@Service
public class InstagramDirectSettingsService extends SingleRowService<InstagramDirectSettingsBean, InstagramDirectSettingsRepository, Integer> {

    public InstagramDirectSettingsService() {
        super(0);
    }

    /**
     * @return В случае не задано ранее значение, возвращаем 0
     */
    public @NotNull String getLastActivityAt() {
        String lastActivityAt = get().getLastActivityAt();
        return lastActivityAt != null ? lastActivityAt : "0";
    }

}
