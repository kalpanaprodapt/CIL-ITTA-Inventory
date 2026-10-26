package com.uniti.cil.itta.inventory.switching.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Vlan. */
public final class VlanFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private VlanFixtures() {
    }

    public static VlanCreateRequest createRequest() {
        VlanCreateRequest request = new VlanCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVlanId(11);
        request.setVlanName("vlanName-1");
        request.setVlanType(VlanVlanType.DATA);
        request.setPoolId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setStpMode("stpMode-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(VlanLifecycleStage.PLAN);
        request.setCode("VLAN-0001");
        request.setName("Vlan one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Vlan entity() {
        Vlan entity = new Vlan();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVlanId(12);
        entity.setVlanName("vlanName-2");
        entity.setVlanType(VlanVlanType.VOICE);
        entity.setPoolId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setStpMode("stpMode-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(VlanLifecycleStage.BUILD);
        entity.setCode("VLAN-0002");
        entity.setName("Vlan two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static VlanResponse response() {
        VlanResponse response = new VlanResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("VLAN-0001");
        response.setName("Vlan one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static VlanSearchCriteria criteria() {
        VlanSearchCriteria criteria = new VlanSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
