package com.uniti.cil.itta.inventory.ipam.mapper;

import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.IpAddress;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between IpAddress entities and DTOs (kept explicit for auditability). */
@Component
public class IpAddressMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "subnet_id", "address", "assigned_to_id", "assignment_type", "dns_name", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public IpAddress toEntity(IpAddressCreateRequest request) {
        IpAddress entity = new IpAddress();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSubnetId(request.getSubnetId());
        entity.setAddress(request.getAddress());
        entity.setAssignedToId(request.getAssignedToId());
        entity.setAssignmentType(request.getAssignmentType());
        entity.setDnsName(request.getDnsName());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(IpAddressUpdateRequest request, IpAddress entity) {
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
        if (request.getSubnetId() != null) {
            entity.setSubnetId(request.getSubnetId());
        }
        if (request.getAddress() != null) {
            entity.setAddress(request.getAddress());
        }
        if (request.getAssignedToId() != null) {
            entity.setAssignedToId(request.getAssignedToId());
        }
        if (request.getAssignmentType() != null) {
            entity.setAssignmentType(request.getAssignmentType());
        }
        if (request.getDnsName() != null) {
            entity.setDnsName(request.getDnsName());
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

    public IpAddressResponse toResponse(IpAddress entity) {
        IpAddressResponse response = new IpAddressResponse();
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
        response.setSubnetId(entity.getSubnetId());
        response.setAddress(entity.getAddress());
        response.setAssignedToId(entity.getAssignedToId());
        response.setAssignmentType(entity.getAssignmentType());
        response.setDnsName(entity.getDnsName());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public IpAddressSummary toSummary(IpAddress entity) {
        return new IpAddressSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<IpAddressResponse> toResponses(List<IpAddress> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(IpAddress entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSubnetId(),
                entity.getAddress(),
                entity.getAssignedToId(),
                entity.getAssignmentType(),
                entity.getDnsName(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
