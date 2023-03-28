/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.audit;

import com.xalap.framework.domain.filter.SearchFilter;
import com.xalap.framework.domain.page.FilterPageableService;
import com.xalap.framework.domain.page.PageableEntityService;

/**
 * Сервис для AuditEvent
 *
 * @author Usov Andrey
 * @since 14.05.2021
 */
public interface AuditEventContract extends PageableEntityService<AuditEventDto, Long>,
        FilterPageableService<AuditEventDto, SearchFilter> {

}
