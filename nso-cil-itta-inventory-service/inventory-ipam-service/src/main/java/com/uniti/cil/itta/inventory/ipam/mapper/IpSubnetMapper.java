package com.uniti.cil.itta.inventory.ipam.mapper;

import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.IpSubnet;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between IpSubnet entities and DTOs (kept explicit for auditability). */
@Component
public class IpSubnetMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "cidr", "ip_version", "vrf_id", "gateway", "vlan_id", "utilization_pct", "owner", "criticality", "cost_center", "notes", "effective_from");

    public IpSubnet toEntity(IpSubnetCreateRequest request) {
        IpSubnet entity = new IpSubnet();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCidr(request.getCidr());
        entity.setIpVersion(request.getIpVersion());
        entity.setVrfId(request.getVrfId());
        entity.setGateway(request.getGateway());
        entity.setVlanId(request.getVlanId());
        entity.setUtilizationPct(request.getUtilizationPct());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(IpSubnetUpdateRequest request, IpSubnet entity) {
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
        if (request.getCidr() != null) {
            entity.setCidr(request.getCidr());
        }
        if (request.getIpVersion() != null) {
            entity.setIpVersion(request.getIpVersion());
        }
        if (request.getVrfId() != null) {
            entity.setVrfId(request.getVrfId());
        }
        if (request.getGateway() != null) {
            entity.setGateway(request.getGateway());
        }
        if (request.getVlanId() != null) {
            entity.setVlanId(request.getVlanId());
        }
        if (request.getUtilizationPct() != null) {
            entity.setUtilizationPct(request.getUtilizationPct());
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

    public IpSubnetResponse toResponse(IpSubnet entity) {
        IpSubnetResponse response = new IpSubnetResponse();
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
        response.setCidr(entity.getCidr());
        response.setIpVersion(entity.getIpVersion());
        response.setVrfId(entity.getVrfId());
        response.setGateway(entity.getGateway());
        response.setVlanId(entity.getVlanId());
        response.setUtilizationPct(entity.getUtilizationPct());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public IpSubnetSummary toSummary(IpSubnet entity) {
        return new IpSubnetSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<IpSubnetResponse> toResponses(List<IpSubnet> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(IpSubnet entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCidr(),
                entity.getIpVersion(),
                entity.getVrfId(),
                entity.getGateway(),
                entity.getVlanId(),
                entity.getUtilizationPct(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
