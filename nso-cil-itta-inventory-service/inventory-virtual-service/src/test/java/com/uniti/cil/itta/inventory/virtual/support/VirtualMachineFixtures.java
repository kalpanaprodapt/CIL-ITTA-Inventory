package com.uniti.cil.itta.inventory.virtual.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for VirtualMachine. */
public final class VirtualMachineFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private VirtualMachineFixtures() {
    }

    public static VirtualMachineCreateRequest createRequest() {
        VirtualMachineCreateRequest request = new VirtualMachineCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setHypervisorId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setVmName("vmName-1");
        request.setVcpu(11);
        request.setMemoryGb(11);
        request.setDiskGb(11);
        request.setPowerState(VirtualMachinePowerState.ON);
        request.setCriticality(VirtualMachineCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("VIRTUA-0001");
        request.setName("Virtual Machine one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static VirtualMachine entity() {
        VirtualMachine entity = new VirtualMachine();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setHypervisorId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setVmName("vmName-2");
        entity.setVcpu(12);
        entity.setMemoryGb(12);
        entity.setDiskGb(12);
        entity.setPowerState(VirtualMachinePowerState.OFF);
        entity.setCriticality(VirtualMachineCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("VIRTUA-0002");
        entity.setName("Virtual Machine two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static VirtualMachineResponse response() {
        VirtualMachineResponse response = new VirtualMachineResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("VIRTUA-0001");
        response.setName("Virtual Machine one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static VirtualMachineSearchCriteria criteria() {
        VirtualMachineSearchCriteria criteria = new VirtualMachineSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
