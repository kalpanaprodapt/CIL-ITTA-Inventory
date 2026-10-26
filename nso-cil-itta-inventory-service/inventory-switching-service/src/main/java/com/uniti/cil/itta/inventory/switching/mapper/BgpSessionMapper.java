package com.uniti.cil.itta.inventory.switching.mapper;

import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.BgpSession;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between BgpSession entities and DTOs (kept explicit for auditability). */
@Component
public class BgpSessionMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "local_device_id", "peer_address", "local_asn", "remote_asn", "session_state", "prefix_limit", "region", "owner", "criticality", "cost_center", "notes");

    public BgpSession toEntity(BgpSessionCreateRequest request) {
        BgpSession entity = new BgpSession();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setLocalDeviceId(request.getLocalDeviceId());
        entity.setPeerAddress(request.getPeerAddress());
        entity.setLocalAsn(request.getLocalAsn());
        entity.setRemoteAsn(request.getRemoteAsn());
        entity.setSessionState(request.getSessionState());
        entity.setPrefixLimit(request.getPrefixLimit());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(BgpSessionUpdateRequest request, BgpSession entity) {
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
        if (request.getLocalDeviceId() != null) {
            entity.setLocalDeviceId(request.getLocalDeviceId());
        }
        if (request.getPeerAddress() != null) {
            entity.setPeerAddress(request.getPeerAddress());
        }
        if (request.getLocalAsn() != null) {
            entity.setLocalAsn(request.getLocalAsn());
        }
        if (request.getRemoteAsn() != null) {
            entity.setRemoteAsn(request.getRemoteAsn());
        }
        if (request.getSessionState() != null) {
            entity.setSessionState(request.getSessionState());
        }
        if (request.getPrefixLimit() != null) {
            entity.setPrefixLimit(request.getPrefixLimit());
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

    public BgpSessionResponse toResponse(BgpSession entity) {
        BgpSessionResponse response = new BgpSessionResponse();
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
        response.setLocalDeviceId(entity.getLocalDeviceId());
        response.setPeerAddress(entity.getPeerAddress());
        response.setLocalAsn(entity.getLocalAsn());
        response.setRemoteAsn(entity.getRemoteAsn());
        response.setSessionState(entity.getSessionState());
        response.setPrefixLimit(entity.getPrefixLimit());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public BgpSessionSummary toSummary(BgpSession entity) {
        return new BgpSessionSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<BgpSessionResponse> toResponses(List<BgpSession> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(BgpSession entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getLocalDeviceId(),
                entity.getPeerAddress(),
                entity.getLocalAsn(),
                entity.getRemoteAsn(),
                entity.getSessionState(),
                entity.getPrefixLimit(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
