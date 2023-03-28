/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Entity
@DiscriminatorValue("Followers")
public class IOFollowersTaskBean extends IOTaskBean {


}
