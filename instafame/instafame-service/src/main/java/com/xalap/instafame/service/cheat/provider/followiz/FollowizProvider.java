/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.followiz;

import com.xalap.instafame.service.cheat.provider.smmpanel.SmmPanelProvider;
import org.springframework.stereotype.Service;

/**
 * https://followiz.com/
 *
 * @author Usov Andrey
 * @since 06/02/2020
 */
@Service
public class FollowizProvider extends SmmPanelProvider {

    @Override
    public String getName() {
        return "Followiz";
    }

    @Override
    public String getApiKey() {
        return "qeqweqweqwe";
    }

    @Override
    public String getApiUrl() {
        return "https://followiz.com/api/v2";
    }
}
