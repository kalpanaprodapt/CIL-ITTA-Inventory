package com.uniti.cil.itta.inventory.assetmgmt.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Invoice. */
public final class InvoiceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private InvoiceFixtures() {
    }

    public static InvoiceCreateRequest createRequest() {
        InvoiceCreateRequest request = new InvoiceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setInvoiceNumber("invoiceNumber-1");
        request.setPoId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setAmount(new BigDecimal("13.50"));
        request.setDueDate(LocalDate.of(2026, 2, 11));
        request.setPaid(Boolean.TRUE);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("INVOIC-0001");
        request.setName("Invoice one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Invoice entity() {
        Invoice entity = new Invoice();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setInvoiceNumber("invoiceNumber-2");
        entity.setPoId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setAmount(new BigDecimal("14.50"));
        entity.setDueDate(LocalDate.of(2026, 3, 12));
        entity.setPaid(Boolean.FALSE);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("INVOIC-0002");
        entity.setName("Invoice two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static InvoiceResponse response() {
        InvoiceResponse response = new InvoiceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("INVOIC-0001");
        response.setName("Invoice one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static InvoiceSearchCriteria criteria() {
        InvoiceSearchCriteria criteria = new InvoiceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
