package com.uniti.cil.itta.inventory.alarm.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for TicketLink. */
public final class TicketLinkFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private TicketLinkFixtures() {
    }

    public static TicketLinkCreateRequest createRequest() {
        TicketLinkCreateRequest request = new TicketLinkCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setAlarmId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setTicketSystem(TicketLinkTicketSystem.SERVICENOW);
        request.setTicketNumber("ticketNumber-1");
        request.setLinkedAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setOwner("owner-1");
        request.setCriticality(TicketLinkCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("TICKET-0001");
        request.setName("Ticket Link one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static TicketLink entity() {
        TicketLink entity = new TicketLink();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setAlarmId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setTicketSystem(TicketLinkTicketSystem.JIRA);
        entity.setTicketNumber("ticketNumber-2");
        entity.setLinkedAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setOwner("owner-2");
        entity.setCriticality(TicketLinkCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("TICKET-0002");
        entity.setName("Ticket Link two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static TicketLinkResponse response() {
        TicketLinkResponse response = new TicketLinkResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("TICKET-0001");
        response.setName("Ticket Link one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static TicketLinkSearchCriteria criteria() {
        TicketLinkSearchCriteria criteria = new TicketLinkSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
