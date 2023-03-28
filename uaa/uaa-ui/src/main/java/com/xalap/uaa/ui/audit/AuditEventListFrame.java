/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.ui.audit;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.uaa.api.audit.AuditEventContract;
import com.xalap.uaa.api.audit.AuditEventDto;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.dataprovider.filter.GridSearchSqlDataProvider;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import static com.xalap.uaa.api.Role.ROLE_ADMIN;
import static com.xalap.uaa.api.audit.AuditEventDto.COLUMN_AUDIT_EVENT_DATE;
import static com.xalap.uaa.api.audit.AuditEventDto.EVENT_ID;
import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.asc;
import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;

/**
 * События связанные с действиями пользователей в системе
 *
 * @author Usov Andrey
 * @since 2020-04-14
 */
@Route(value = AuditEventListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Аудит")
@Secured(ROLE_ADMIN)
public class AuditEventListFrame extends RootEntityListFrame<AuditEventDto> {

    static final String VIEW_NAME = "auditEvents";

    @Autowired
    public AuditEventListFrame(ServiceRef<AuditEventContract> service) {
        super(service, asc(EVENT_ID));

        setDataProviderWithSearch(
                new GridSearchSqlDataProvider<>(
                        service,
                        desc(COLUMN_AUDIT_EVENT_DATE)));
        GridColumns<AuditEventDto> columns = gridPanel.columns();
        columns.addInstant("Date", AuditEventDto::getAuditEventDate);
        columns
                .add("Principal", AuditEventDto::getPrincipal)
                .add("EventType", AuditEventDto::getAuditEventType)
                .add("Data", AuditEventDto::toString)
        ;
        addCreateButton();
    }

}
