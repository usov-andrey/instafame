/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.audit;

import com.xalap.framework.data.filter.FilterCrudService;
import com.xalap.framework.domain.filter.SearchFilter;
import com.xalap.framework.domain.page.request.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Service for managing audit events.
 * <p>
 */
@Service
public class PersistentAuditEventService extends FilterCrudService<PersistentAuditEvent,
        PersistenceAuditEventRepository, Long, SearchFilter> {

    private final Logger log = LoggerFactory.getLogger(PersistentAuditEventService.class);

    public PersistentAuditEventService() {
        super();
    }

    /**
     * Old audit events should be automatically deleted after 30 days.
     * <p>
     * This is scheduled to get fired at 12:00 (am).
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void removeOldAuditEvents() {
        repository()
                .findByAuditEventDateBefore(Instant.now().minus(30, ChronoUnit.DAYS))
                .forEach(auditEvent -> {
                    log.debug("Deleting audit data {}", auditEvent);
                    delete(auditEvent);
                });
    }

    public List<PersistentAuditEvent> findPage(PageRequest pageable, SearchFilter filter) {
        return repository().findByPrincipalContainingOrAuditEventTypeContaining(
                pageable(pageable), filter.getSearch(), filter.getSearch());
    }


    public long count(SearchFilter filter) {
        return repository().countByPrincipalContainingOrAuditEventTypeContaining(filter.getSearch(), filter.getSearch());
    }

}