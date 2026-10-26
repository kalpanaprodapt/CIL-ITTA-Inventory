package com.uniti.cil.itta.inventory.integration.mapper;

import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.ImportBatch;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ImportBatch entities and DTOs (kept explicit for auditability). */
@Component
public class ImportBatchMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "file_name", "entity_type", "total_rows", "processed_rows", "failed_rows", "batch_state", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public ImportBatch toEntity(ImportBatchCreateRequest request) {
        ImportBatch entity = new ImportBatch();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setFileName(request.getFileName());
        entity.setEntityType(request.getEntityType());
        entity.setTotalRows(request.getTotalRows());
        entity.setProcessedRows(request.getProcessedRows());
        entity.setFailedRows(request.getFailedRows());
        entity.setBatchState(request.getBatchState());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(ImportBatchUpdateRequest request, ImportBatch entity) {
        if (request.getCode() != null) {
            entity.setCode(request.getCode());
        }
        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        if (request.getExternalRef() != null) {
            entity.setExternalRef(request.getExternalRef());
        }
        if (request.getFileName() != null) {
            entity.setFileName(request.getFileName());
        }
        if (request.getEntityType() != null) {
            entity.setEntityType(request.getEntityType());
        }
        if (request.getTotalRows() != null) {
            entity.setTotalRows(request.getTotalRows());
        }
        if (request.getProcessedRows() != null) {
            entity.setProcessedRows(request.getProcessedRows());
        }
        if (request.getFailedRows() != null) {
            entity.setFailedRows(request.getFailedRows());
        }
        if (request.getBatchState() != null) {
            entity.setBatchState(request.getBatchState());
        }
        if (request.getEffectiveFrom() != null) {
            entity.setEffectiveFrom(request.getEffectiveFrom());
        }
        if (request.getEffectiveTo() != null) {
            entity.setEffectiveTo(request.getEffectiveTo());
        }
        if (request.getLabels() != null) {
            entity.setLabels(request.getLabels());
        }
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
        }
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
        }
    }

    public ImportBatchResponse toResponse(ImportBatch entity) {
        ImportBatchResponse response = new ImportBatchResponse();
        response.setId(entity.getId());
        response.setTenantId(entity.getTenantId());
        response.setVersion(entity.getVersion());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setCreatedBy(entity.getCreatedBy());
        response.setUpdatedBy(entity.getUpdatedBy());
        response.setCode(entity.getCode());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setStatus(entity.getStatus());
        response.setExternalRef(entity.getExternalRef());
        response.setFileName(entity.getFileName());
        response.setEntityType(entity.getEntityType());
        response.setTotalRows(entity.getTotalRows());
        response.setProcessedRows(entity.getProcessedRows());
        response.setFailedRows(entity.getFailedRows());
        response.setBatchState(entity.getBatchState());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public ImportBatchSummary toSummary(ImportBatch entity) {
        return new ImportBatchSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ImportBatchResponse> toResponses(List<ImportBatch> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ImportBatch entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getFileName(),
                entity.getEntityType(),
                entity.getTotalRows(),
                entity.getProcessedRows(),
                entity.getFailedRows(),
                entity.getBatchState(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
