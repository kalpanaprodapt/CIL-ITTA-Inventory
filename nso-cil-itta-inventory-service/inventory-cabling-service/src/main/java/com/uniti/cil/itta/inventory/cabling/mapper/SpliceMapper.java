package com.uniti.cil.itta.inventory.cabling.mapper;

import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.Splice;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Splice entities and DTOs (kept explicit for auditability). */
@Component
public class SpliceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "enclosure_id", "a_strand_id", "z_strand_id", "loss_db", "spliced_on", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public Splice toEntity(SpliceCreateRequest request) {
        Splice entity = new Splice();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setEnclosureId(request.getEnclosureId());
        entity.setAStrandId(request.getAStrandId());
        entity.setZStrandId(request.getZStrandId());
        entity.setLossDb(request.getLossDb());
        entity.setSplicedOn(request.getSplicedOn());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(SpliceUpdateRequest request, Splice entity) {
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
        if (request.getEnclosureId() != null) {
            entity.setEnclosureId(request.getEnclosureId());
        }
        if (request.getAStrandId() != null) {
            entity.setAStrandId(request.getAStrandId());
        }
        if (request.getZStrandId() != null) {
            entity.setZStrandId(request.getZStrandId());
        }
        if (request.getLossDb() != null) {
            entity.setLossDb(request.getLossDb());
        }
        if (request.getSplicedOn() != null) {
            entity.setSplicedOn(request.getSplicedOn());
        }
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
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
    }

    public SpliceResponse toResponse(Splice entity) {
        SpliceResponse response = new SpliceResponse();
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
        response.setEnclosureId(entity.getEnclosureId());
        response.setAStrandId(entity.getAStrandId());
        response.setZStrandId(entity.getZStrandId());
        response.setLossDb(entity.getLossDb());
        response.setSplicedOn(entity.getSplicedOn());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public SpliceSummary toSummary(Splice entity) {
        return new SpliceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SpliceResponse> toResponses(List<Splice> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Splice entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getEnclosureId(),
                entity.getAStrandId(),
                entity.getZStrandId(),
                entity.getLossDb(),
                entity.getSplicedOn(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
