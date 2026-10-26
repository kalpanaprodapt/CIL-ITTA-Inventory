package com.uniti.cil.itta.inventory.integration.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ImportBatch. */
public final class ImportBatchFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ImportBatchFixtures() {
    }

    public static ImportBatchCreateRequest createRequest() {
        ImportBatchCreateRequest request = new ImportBatchCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setFileName("fileName-1");
        request.setEntityType("entityType-1");
        request.setTotalRows(11);
        request.setProcessedRows(11);
        request.setFailedRows(11);
        request.setBatchState(ImportBatchBatchState.UPLOADED);
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(ImportBatchLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("IMPORT-0001");
        request.setName("Import Batch one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ImportBatch entity() {
        ImportBatch entity = new ImportBatch();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setFileName("fileName-2");
        entity.setEntityType("entityType-2");
        entity.setTotalRows(12);
        entity.setProcessedRows(12);
        entity.setFailedRows(12);
        entity.setBatchState(ImportBatchBatchState.VALIDATING);
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(ImportBatchLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("IMPORT-0002");
        entity.setName("Import Batch two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ImportBatchResponse response() {
        ImportBatchResponse response = new ImportBatchResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("IMPORT-0001");
        response.setName("Import Batch one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ImportBatchSearchCriteria criteria() {
        ImportBatchSearchCriteria criteria = new ImportBatchSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
