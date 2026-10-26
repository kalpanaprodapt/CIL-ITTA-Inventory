package com.uniti.cil.itta.inventory.switching.mapper;

import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.Vlan;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Vlan entities and DTOs (kept explicit for auditability). */
@Component
public class VlanMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "vlan_id", "vlan_name", "vlan_type", "pool_id", "stp_mode", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public Vlan toEntity(VlanCreateRequest request) {
        Vlan entity = new Vlan();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVlanId(request.getVlanId());
        entity.setVlanName(request.getVlanName());
        entity.setVlanType(request.getVlanType());
        entity.setPoolId(request.getPoolId());
        entity.setStpMode(request.getStpMode());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(VlanUpdateRequest request, Vlan entity) {
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
        if (request.getVlanId() != null) {
            entity.setVlanId(request.getVlanId());
        }
        if (request.getVlanName() != null) {
            entity.setVlanName(request.getVlanName());
        }
        if (request.getVlanType() != null) {
            entity.setVlanType(request.getVlanType());
        }
        if (request.getPoolId() != null) {
            entity.setPoolId(request.getPoolId());
        }
        if (request.getStpMode() != null) {
            entity.setStpMode(request.getStpMode());
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

    public VlanResponse toResponse(Vlan entity) {
        VlanResponse response = new VlanResponse();
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
        response.setVlanId(entity.getVlanId());
        response.setVlanName(entity.getVlanName());
        response.setVlanType(entity.getVlanType());
        response.setPoolId(entity.getPoolId());
        response.setStpMode(entity.getStpMode());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public VlanSummary toSummary(Vlan entity) {
        return new VlanSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<VlanResponse> toResponses(List<Vlan> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Vlan entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVlanId(),
                entity.getVlanName(),
                entity.getVlanType(),
                entity.getPoolId(),
                entity.getStpMode(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
