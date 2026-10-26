package com.uniti.cil.itta.inventory.port.mapper;

import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.SubInterface;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between SubInterface entities and DTOs (kept explicit for auditability). */
@Component
public class SubInterfaceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "interface_id", "vlan_tag", "encapsulation", "ipv4_address", "ipv6_address", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public SubInterface toEntity(SubInterfaceCreateRequest request) {
        SubInterface entity = new SubInterface();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setInterfaceId(request.getInterfaceId());
        entity.setVlanTag(request.getVlanTag());
        entity.setEncapsulation(request.getEncapsulation());
        entity.setIpv4Address(request.getIpv4Address());
        entity.setIpv6Address(request.getIpv6Address());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(SubInterfaceUpdateRequest request, SubInterface entity) {
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
        if (request.getInterfaceId() != null) {
            entity.setInterfaceId(request.getInterfaceId());
        }
        if (request.getVlanTag() != null) {
            entity.setVlanTag(request.getVlanTag());
        }
        if (request.getEncapsulation() != null) {
            entity.setEncapsulation(request.getEncapsulation());
        }
        if (request.getIpv4Address() != null) {
            entity.setIpv4Address(request.getIpv4Address());
        }
        if (request.getIpv6Address() != null) {
            entity.setIpv6Address(request.getIpv6Address());
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
        if (request.getEffectiveTo() != null) {
            entity.setEffectiveTo(request.getEffectiveTo());
        }
        if (request.getLabels() != null) {
            entity.setLabels(request.getLabels());
        }
    }

    public SubInterfaceResponse toResponse(SubInterface entity) {
        SubInterfaceResponse response = new SubInterfaceResponse();
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
        response.setInterfaceId(entity.getInterfaceId());
        response.setVlanTag(entity.getVlanTag());
        response.setEncapsulation(entity.getEncapsulation());
        response.setIpv4Address(entity.getIpv4Address());
        response.setIpv6Address(entity.getIpv6Address());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public SubInterfaceSummary toSummary(SubInterface entity) {
        return new SubInterfaceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SubInterfaceResponse> toResponses(List<SubInterface> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(SubInterface entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getInterfaceId(),
                entity.getVlanTag(),
                entity.getEncapsulation(),
                entity.getIpv4Address(),
                entity.getIpv6Address(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
