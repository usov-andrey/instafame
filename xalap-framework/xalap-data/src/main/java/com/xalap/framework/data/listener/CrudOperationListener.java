/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.listener;

import com.xalap.framework.domain.holder.IdHolder;

/**
 * @author Усов Андрей
 * @since 10/06/2019
 */
public interface CrudOperationListener {

    <T extends IdHolder<Id>, Id> void onEvent(CrudOperationStage stage, CrudOperation operation, T bean);

}
