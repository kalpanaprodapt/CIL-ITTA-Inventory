package com.uniti.cil.itta.inventory.mpls.mapper;

import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.VpnService;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between VpnService entities and DTOs (kept explicit for auditability). */
@Component
public class VpnServiceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "vpn_name", "vpn_type", "vrf_id", "customer_id", "topology", "owner", "criticality", "cost_center", "notes", "effective_from");

    public VpnService toEntity(VpnServiceCreateRequest request) {
        VpnService entity = new VpnService();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVpnName(request.getVpnName());
        entity.setVpnType(request.getVpnType());
        entity.setVrfId(request.getVrfId());
        entity.setCustomerId(request.getCustomerId());
        entity.setTopology(request.getTopology());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(VpnServiceUpdateRequest request, VpnService entity) {
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
        if (request.getVpnName() != null) {
            entity.setVpnName(request.getVpnName());
        }
        if (request.getVpnType() != null) {
            entity.setVpnType(request.getVpnType());
        }
        if (request.getVrfId() != null) {
            entity.setVrfId(request.getVrfId());
        }
        if (request.getCustomerId() != null) {
            entity.setCustomerId(request.getCustomerId());
        }
        if (request.getTopology() != null) {
            entity.setTopology(request.getTopology());
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
        if (request.getNotes() != null) {
            entity.setNotes(request.getNotes());
        }
        if (request.getEffectiveFrom() != null) {
            entity.setEffectiveFrom(request.getEffectiveFrom());
        }
    }

    public VpnServiceResponse toResponse(VpnService entity) {
        VpnServiceResponse response = new VpnServiceResponse();
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
        response.setVpnName(entity.getVpnName());
        response.setVpnType(entity.getVpnType());
        response.setVrfId(entity.getVrfId());
        response.setCustomerId(entity.getCustomerId());
        response.setTopology(entity.getTopology());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public VpnServiceSummary toSummary(VpnService entity) {
        return new VpnServiceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<VpnServiceResponse> toResponses(List<VpnService> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(VpnService entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVpnName(),
                entity.getVpnType(),
                entity.getVrfId(),
                entity.getCustomerId(),
                entity.getTopology(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
