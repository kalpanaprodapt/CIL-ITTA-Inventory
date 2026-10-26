package com.uniti.cil.itta.inventory.mpls.mapper;

import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.PseudoWire;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between PseudoWire entities and DTOs (kept explicit for auditability). */
@Component
public class PseudoWireMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "vc_id", "a_end_id", "z_end_id", "encapsulation", "control_word", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public PseudoWire toEntity(PseudoWireCreateRequest request) {
        PseudoWire entity = new PseudoWire();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setVcId(request.getVcId());
        entity.setAEndId(request.getAEndId());
        entity.setZEndId(request.getZEndId());
        entity.setEncapsulation(request.getEncapsulation());
        entity.setControlWord(request.getControlWord());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(PseudoWireUpdateRequest request, PseudoWire entity) {
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
        if (request.getVcId() != null) {
            entity.setVcId(request.getVcId());
        }
        if (request.getAEndId() != null) {
            entity.setAEndId(request.getAEndId());
        }
        if (request.getZEndId() != null) {
            entity.setZEndId(request.getZEndId());
        }
        if (request.getEncapsulation() != null) {
            entity.setEncapsulation(request.getEncapsulation());
        }
        if (request.getControlWord() != null) {
            entity.setControlWord(request.getControlWord());
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

    public PseudoWireResponse toResponse(PseudoWire entity) {
        PseudoWireResponse response = new PseudoWireResponse();
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
        response.setVcId(entity.getVcId());
        response.setAEndId(entity.getAEndId());
        response.setZEndId(entity.getZEndId());
        response.setEncapsulation(entity.getEncapsulation());
        response.setControlWord(entity.getControlWord());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public PseudoWireSummary toSummary(PseudoWire entity) {
        return new PseudoWireSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<PseudoWireResponse> toResponses(List<PseudoWire> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(PseudoWire entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getVcId(),
                entity.getAEndId(),
                entity.getZEndId(),
                entity.getEncapsulation(),
                entity.getControlWord(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
