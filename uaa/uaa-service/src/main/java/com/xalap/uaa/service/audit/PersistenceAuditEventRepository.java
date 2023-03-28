/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.audit;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the {@link PersistentAuditEvent} entity.
 */
public interface PersistenceAuditEventRepository extends PageableRepository<PersistentAuditEvent, Long> {

    List<PersistentAuditEvent> findByPrincipalContainingOrAuditEventTypeContaining(Pageable pageable, String principal, String type);

    long countByPrincipalContainingOrAuditEventTypeContaining(String principal, String type);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principal, Instant after, String type);

    List<PersistentAuditEvent> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable);

    List<PersistentAuditEvent> findByAuditEventDateBefore(Instant before);
}

