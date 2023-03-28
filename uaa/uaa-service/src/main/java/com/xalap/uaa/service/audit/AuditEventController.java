/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.audit;

import com.xalap.framework.data.filter.FilterDtoCrudService;
import com.xalap.framework.domain.filter.SearchFilter;
import com.xalap.uaa.api.audit.AuditEventContract;
import com.xalap.uaa.api.audit.AuditEventDto;
import org.springframework.stereotype.Service;

/**
 * @author Usov Andrey
 * @since 14.05.2021
 */
@Service
public class AuditEventController extends FilterDtoCrudService<AuditEventDto, PersistentAuditEvent,
        PersistentAuditEventService, Long, SearchFilter> implements AuditEventContract {

}
