package com.uniti.cil.itta.inventory.mpls.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for PseudoWire. */
public final class PseudoWireFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PseudoWireFixtures() {
    }

    public static PseudoWireCreateRequest createRequest() {
        PseudoWireCreateRequest request = new PseudoWireCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVcId(1001L);
        request.setAEndId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setZEndId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setEncapsulation("encapsulation-1");
        request.setControlWord(Boolean.TRUE);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("PSEUDO-0001");
        request.setName("Pseudo Wire one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static PseudoWire entity() {
        PseudoWire entity = new PseudoWire();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVcId(1002L);
        entity.setAEndId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setZEndId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setEncapsulation("encapsulation-2");
        entity.setControlWord(Boolean.FALSE);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("PSEUDO-0002");
        entity.setName("Pseudo Wire two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PseudoWireResponse response() {
        PseudoWireResponse response = new PseudoWireResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("PSEUDO-0001");
        response.setName("Pseudo Wire one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PseudoWireSearchCriteria criteria() {
        PseudoWireSearchCriteria criteria = new PseudoWireSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
