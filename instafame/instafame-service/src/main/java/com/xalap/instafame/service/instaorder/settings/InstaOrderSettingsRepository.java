/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.settings;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Усов Андрей
 * @since 08/05/2019
 */
@Repository
public interface InstaOrderSettingsRepository extends PageableRepository<InstaOrderSettingsBean, Integer> {
}
