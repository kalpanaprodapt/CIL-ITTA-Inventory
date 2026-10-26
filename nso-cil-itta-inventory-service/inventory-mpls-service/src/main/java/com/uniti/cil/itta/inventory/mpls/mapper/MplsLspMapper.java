package com.uniti.cil.itta.inventory.mpls.mapper;

import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.MplsLsp;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between MplsLsp entities and DTOs (kept explicit for auditability). */
@Component
public class MplsLspMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "lsp_name", "head_end_id", "tail_end_id", "bandwidth_mbps", "setup_priority", "path_type", "source_system", "region", "owner", "criticality", "cost_center");

    public MplsLsp toEntity(MplsLspCreateRequest request) {
        MplsLsp entity = new MplsLsp();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setLspName(request.getLspName());
        entity.setHeadEndId(request.getHeadEndId());
        entity.setTailEndId(request.getTailEndId());
        entity.setBandwidthMbps(request.getBandwidthMbps());
        entity.setSetupPriority(request.getSetupPriority());
        entity.setPathType(request.getPathType());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(MplsLspUpdateRequest request, MplsLsp entity) {
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
        if (request.getLspName() != null) {
            entity.setLspName(request.getLspName());
        }
        if (request.getHeadEndId() != null) {
            entity.setHeadEndId(request.getHeadEndId());
        }
        if (request.getTailEndId() != null) {
            entity.setTailEndId(request.getTailEndId());
        }
        if (request.getBandwidthMbps() != null) {
            entity.setBandwidthMbps(request.getBandwidthMbps());
        }
        if (request.getSetupPriority() != null) {
            entity.setSetupPriority(request.getSetupPriority());
        }
        if (request.getPathType() != null) {
            entity.setPathType(request.getPathType());
        }
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
        }
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
        }
        if (request.getCriticality() != null) {
            entity.setCriticality(request.getCriticality());
        }
        if (request.getCostCenter() != null) {
            entity.setCostCenter(request.getCostCenter());
        }
    }

    public MplsLspResponse toResponse(MplsLsp entity) {
        MplsLspResponse response = new MplsLspResponse();
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
        response.setLspName(entity.getLspName());
        response.setHeadEndId(entity.getHeadEndId());
        response.setTailEndId(entity.getTailEndId());
        response.setBandwidthMbps(entity.getBandwidthMbps());
        response.setSetupPriority(entity.getSetupPriority());
        response.setPathType(entity.getPathType());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public MplsLspSummary toSummary(MplsLsp entity) {
        return new MplsLspSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<MplsLspResponse> toResponses(List<MplsLsp> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(MplsLsp entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getLspName(),
                entity.getHeadEndId(),
                entity.getTailEndId(),
                entity.getBandwidthMbps(),
                entity.getSetupPriority(),
                entity.getPathType(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
