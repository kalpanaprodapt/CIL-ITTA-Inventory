package com.uniti.cil.itta.inventory.port.mapper;

import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.Interface;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Interface entities and DTOs (kept explicit for auditability). */
@Component
public class InterfaceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "port_id", "if_name", "if_index", "mac_address", "mtu", "oper_state", "owner", "criticality", "cost_center", "notes", "effective_from");

    public Interface toEntity(InterfaceCreateRequest request) {
        Interface entity = new Interface();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setPortId(request.getPortId());
        entity.setIfName(request.getIfName());
        entity.setIfIndex(request.getIfIndex());
        entity.setMacAddress(request.getMacAddress());
        entity.setMtu(request.getMtu());
        entity.setOperState(request.getOperState());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(InterfaceUpdateRequest request, Interface entity) {
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
        if (request.getPortId() != null) {
            entity.setPortId(request.getPortId());
        }
        if (request.getIfName() != null) {
            entity.setIfName(request.getIfName());
        }
        if (request.getIfIndex() != null) {
            entity.setIfIndex(request.getIfIndex());
        }
        if (request.getMacAddress() != null) {
            entity.setMacAddress(request.getMacAddress());
        }
        if (request.getMtu() != null) {
            entity.setMtu(request.getMtu());
        }
        if (request.getOperState() != null) {
            entity.setOperState(request.getOperState());
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

    public InterfaceResponse toResponse(Interface entity) {
        InterfaceResponse response = new InterfaceResponse();
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
        response.setPortId(entity.getPortId());
        response.setIfName(entity.getIfName());
        response.setIfIndex(entity.getIfIndex());
        response.setMacAddress(entity.getMacAddress());
        response.setMtu(entity.getMtu());
        response.setOperState(entity.getOperState());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public InterfaceSummary toSummary(Interface entity) {
        return new InterfaceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<InterfaceResponse> toResponses(List<Interface> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Interface entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getPortId(),
                entity.getIfName(),
                entity.getIfIndex(),
                entity.getMacAddress(),
                entity.getMtu(),
                entity.getOperState(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
