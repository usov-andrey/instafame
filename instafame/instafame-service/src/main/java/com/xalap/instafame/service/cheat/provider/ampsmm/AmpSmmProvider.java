/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.ampsmm;

import com.xalap.instafame.service.cheat.provider.smmpanel.SmmPanelProvider;
import org.springframework.stereotype.Service;

/**
 * https://amp-smm.services/api
 *
 * @author Usov Andrey
 * @since 05/02/2020
 */
@Service
public class AmpSmmProvider extends SmmPanelProvider {

    @Override
    public String getName() {
        return "AmpSMM";
    }

    @Override
    public String getApiKey() {
        return "wqeqweqweqwe";
    }

    @Override
    public String getApiUrl() {
        return "https://amp-smm.services/api/v2";
    }
}
