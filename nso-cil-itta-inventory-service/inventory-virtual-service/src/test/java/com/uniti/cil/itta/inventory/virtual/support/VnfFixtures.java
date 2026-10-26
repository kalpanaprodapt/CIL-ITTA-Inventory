package com.uniti.cil.itta.inventory.virtual.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Vnf. */
public final class VnfFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private VnfFixtures() {
    }

    public static VnfCreateRequest createRequest() {
        VnfCreateRequest request = new VnfCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVnfName("vnfName-1");
        request.setVnfType("vnfType-1");
        request.setFlavor("flavor-1");
        request.setVcpu(11);
        request.setMemoryGb(11);
        request.setInstanceCount(11);
        request.setLifecycleStage(VnfLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(VnfCriticality.LOW);
        request.setCode("VNF-0001");
        request.setName("Vnf one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Vnf entity() {
        Vnf entity = new Vnf();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVnfName("vnfName-2");
        entity.setVnfType("vnfType-2");
        entity.setFlavor("flavor-2");
        entity.setVcpu(12);
        entity.setMemoryGb(12);
        entity.setInstanceCount(12);
        entity.setLifecycleStage(VnfLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(VnfCriticality.MEDIUM);
        entity.setCode("VNF-0002");
        entity.setName("Vnf two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static VnfResponse response() {
        VnfResponse response = new VnfResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("VNF-0001");
        response.setName("Vnf one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static VnfSearchCriteria criteria() {
        VnfSearchCriteria criteria = new VnfSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
