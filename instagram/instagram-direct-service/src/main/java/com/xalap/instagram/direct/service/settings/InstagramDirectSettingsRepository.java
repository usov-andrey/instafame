/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.direct.service.settings;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Усов Андрей
 * @since 28/01/2020
 */
@Repository
public interface InstagramDirectSettingsRepository extends CrudRepository<InstagramDirectSettingsBean, Integer> {

}
