package com.uniti.cil.itta.inventory.alarm.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for EventSource. */
public final class EventSourceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private EventSourceFixtures() {
    }

    public static EventSourceCreateRequest createRequest() {
        EventSourceCreateRequest request = new EventSourceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSourceName("sourceName-1");
        request.setProtocol(EventSourceProtocol.SNMP_TRAP);
        request.setEndpoint("endpoint-1");
        request.setHeartbeatSeconds(11);
        request.setLabels("labels-1");
        request.setLifecycleStage(EventSourceLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("EVENTS-0001");
        request.setName("Event Source one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static EventSource entity() {
        EventSource entity = new EventSource();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSourceName("sourceName-2");
        entity.setProtocol(EventSourceProtocol.SYSLOG);
        entity.setEndpoint("endpoint-2");
        entity.setHeartbeatSeconds(12);
        entity.setLabels("labels-2");
        entity.setLifecycleStage(EventSourceLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("EVENTS-0002");
        entity.setName("Event Source two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static EventSourceResponse response() {
        EventSourceResponse response = new EventSourceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("EVENTS-0001");
        response.setName("Event Source one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static EventSourceSearchCriteria criteria() {
        EventSourceSearchCriteria criteria = new EventSourceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
