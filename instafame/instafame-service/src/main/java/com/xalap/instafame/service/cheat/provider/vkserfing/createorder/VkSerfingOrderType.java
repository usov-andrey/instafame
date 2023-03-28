/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.vkserfing.createorder;

import com.xalap.instafame.service.cheat.provider.vkserfing.VkSerfingCheatProvider;

/**
 * @author Usov Andrey
 * @since 2020-07-21
 */
public enum VkSerfingOrderType {

    instagram_follower,
    instagram_like,
    instagram_comment,
    instagram_view_video,
    instagram_view_history;

    public String createOrderParams(String link, int count) {
        return String.format(VkSerfingCheatProvider.CREATE_ORDER_PARAMS, link, name(), count);
    }
}
