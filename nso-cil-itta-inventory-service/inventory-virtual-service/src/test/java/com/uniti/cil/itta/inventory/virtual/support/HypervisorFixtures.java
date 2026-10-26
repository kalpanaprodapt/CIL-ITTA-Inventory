package com.uniti.cil.itta.inventory.virtual.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Hypervisor. */
public final class HypervisorFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private HypervisorFixtures() {
    }

    public static HypervisorCreateRequest createRequest() {
        HypervisorCreateRequest request = new HypervisorCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setHostDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setHypervisorType(HypervisorHypervisorType.KVM);
        request.setHvVersion("hvVersion-1");
        request.setVcpuCapacity(11);
        request.setMemoryGbCapacity(11);
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(HypervisorCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("HYPERV-0001");
        request.setName("Hypervisor one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Hypervisor entity() {
        Hypervisor entity = new Hypervisor();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setHostDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setHypervisorType(HypervisorHypervisorType.VMWARE);
        entity.setHvVersion("hvVersion-2");
        entity.setVcpuCapacity(12);
        entity.setMemoryGbCapacity(12);
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(HypervisorCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("HYPERV-0002");
        entity.setName("Hypervisor two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static HypervisorResponse response() {
        HypervisorResponse response = new HypervisorResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("HYPERV-0001");
        response.setName("Hypervisor one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static HypervisorSearchCriteria criteria() {
        HypervisorSearchCriteria criteria = new HypervisorSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
