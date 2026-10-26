package com.uniti.cil.itta.inventory.optical.mapper;

import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.WdmSystem;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between WdmSystem entities and DTOs (kept explicit for auditability). */
@Component
public class WdmSystemMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "system_name", "channel_capacity", "band", "span_count", "vendor_id", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public WdmSystem toEntity(WdmSystemCreateRequest request) {
        WdmSystem entity = new WdmSystem();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSystemName(request.getSystemName());
        entity.setChannelCapacity(request.getChannelCapacity());
        entity.setBand(request.getBand());
        entity.setSpanCount(request.getSpanCount());
        entity.setVendorId(request.getVendorId());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(WdmSystemUpdateRequest request, WdmSystem entity) {
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
        if (request.getSystemName() != null) {
            entity.setSystemName(request.getSystemName());
        }
        if (request.getChannelCapacity() != null) {
            entity.setChannelCapacity(request.getChannelCapacity());
        }
        if (request.getBand() != null) {
            entity.setBand(request.getBand());
        }
        if (request.getSpanCount() != null) {
            entity.setSpanCount(request.getSpanCount());
        }
        if (request.getVendorId() != null) {
            entity.setVendorId(request.getVendorId());
        }
        if (request.getNotes() != null) {
            entity.setNotes(request.getNotes());
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
    }

    public WdmSystemResponse toResponse(WdmSystem entity) {
        WdmSystemResponse response = new WdmSystemResponse();
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
        response.setSystemName(entity.getSystemName());
        response.setChannelCapacity(entity.getChannelCapacity());
        response.setBand(entity.getBand());
        response.setSpanCount(entity.getSpanCount());
        response.setVendorId(entity.getVendorId());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public WdmSystemSummary toSummary(WdmSystem entity) {
        return new WdmSystemSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<WdmSystemResponse> toResponses(List<WdmSystem> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(WdmSystem entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSystemName(),
                entity.getChannelCapacity(),
                entity.getBand(),
                entity.getSpanCount(),
                entity.getVendorId(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
