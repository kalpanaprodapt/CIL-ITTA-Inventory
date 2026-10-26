package com.uniti.cil.itta.inventory.mpls.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for QosPolicy. */
public final class QosPolicyFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private QosPolicyFixtures() {
    }

    public static QosPolicyCreateRequest createRequest() {
        QosPolicyCreateRequest request = new QosPolicyCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setPolicyName("policyName-1");
        request.setClassCount(11);
        request.setCirMbps(1001L);
        request.setPirMbps(1001L);
        request.setMarkingScheme("markingScheme-1");
        request.setLabels("labels-1");
        request.setLifecycleStage(QosPolicyLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("QOSPOL-0001");
        request.setName("Qos Policy one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static QosPolicy entity() {
        QosPolicy entity = new QosPolicy();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setPolicyName("policyName-2");
        entity.setClassCount(12);
        entity.setCirMbps(1002L);
        entity.setPirMbps(1002L);
        entity.setMarkingScheme("markingScheme-2");
        entity.setLabels("labels-2");
        entity.setLifecycleStage(QosPolicyLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("QOSPOL-0002");
        entity.setName("Qos Policy two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static QosPolicyResponse response() {
        QosPolicyResponse response = new QosPolicyResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("QOSPOL-0001");
        response.setName("Qos Policy one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static QosPolicySearchCriteria criteria() {
        QosPolicySearchCriteria criteria = new QosPolicySearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
