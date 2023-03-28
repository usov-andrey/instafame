/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.audit;

import com.xalap.framework.domain.holder.IdHolder;

import java.time.Instant;

/**
 * @author Usov Andrey
 * @since 14.05.2021
 */
public interface AuditEventDto extends IdHolder<Long> {

    String EVENT_ID = "event_id";
    String COLUMN_AUDIT_EVENT_DATE = "auditEventDate";

    String getPrincipal();

    Instant getAuditEventDate();

    String getAuditEventType();


}
