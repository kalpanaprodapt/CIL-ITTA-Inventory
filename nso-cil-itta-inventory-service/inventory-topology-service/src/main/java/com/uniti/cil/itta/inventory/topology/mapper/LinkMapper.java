package com.uniti.cil.itta.inventory.topology.mapper;

import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.Link;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Link entities and DTOs (kept explicit for auditability). */
@Component
public class LinkMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "a_interface_id", "z_interface_id", "link_type", "speed_mbps", "discovered_by", "region", "owner", "criticality", "cost_center", "notes");

    public Link toEntity(LinkCreateRequest request) {
        Link entity = new Link();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setAInterfaceId(request.getAInterfaceId());
        entity.setZInterfaceId(request.getZInterfaceId());
        entity.setLinkType(request.getLinkType());
        entity.setSpeedMbps(request.getSpeedMbps());
        entity.setDiscoveredBy(request.getDiscoveredBy());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(LinkUpdateRequest request, Link entity) {
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
        if (request.getAInterfaceId() != null) {
            entity.setAInterfaceId(request.getAInterfaceId());
        }
        if (request.getZInterfaceId() != null) {
            entity.setZInterfaceId(request.getZInterfaceId());
        }
        if (request.getLinkType() != null) {
            entity.setLinkType(request.getLinkType());
        }
        if (request.getSpeedMbps() != null) {
            entity.setSpeedMbps(request.getSpeedMbps());
        }
        if (request.getDiscoveredBy() != null) {
            entity.setDiscoveredBy(request.getDiscoveredBy());
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
        if (request.getNotes() != null) {
            entity.setNotes(request.getNotes());
        }
    }

    public LinkResponse toResponse(Link entity) {
        LinkResponse response = new LinkResponse();
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
        response.setAInterfaceId(entity.getAInterfaceId());
        response.setZInterfaceId(entity.getZInterfaceId());
        response.setLinkType(entity.getLinkType());
        response.setSpeedMbps(entity.getSpeedMbps());
        response.setDiscoveredBy(entity.getDiscoveredBy());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public LinkSummary toSummary(Link entity) {
        return new LinkSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<LinkResponse> toResponses(List<Link> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Link entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getAInterfaceId(),
                entity.getZInterfaceId(),
                entity.getLinkType(),
                entity.getSpeedMbps(),
                entity.getDiscoveredBy(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
