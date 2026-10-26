package com.uniti.cil.itta.inventory.optical.mapper;

import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.Amplifier;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Amplifier entities and DTOs (kept explicit for auditability). */
@Component
public class AmplifierMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "system_id", "amp_type", "gain_db", "output_power_dbm", "noise_figure_db", "lifecycle_stage", "source_system", "region", "owner", "criticality");

    public Amplifier toEntity(AmplifierCreateRequest request) {
        Amplifier entity = new Amplifier();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSystemId(request.getSystemId());
        entity.setAmpType(request.getAmpType());
        entity.setGainDb(request.getGainDb());
        entity.setOutputPowerDbm(request.getOutputPowerDbm());
        entity.setNoiseFigureDb(request.getNoiseFigureDb());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        return entity;
    }

    public void applyUpdate(AmplifierUpdateRequest request, Amplifier entity) {
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
        if (request.getSystemId() != null) {
            entity.setSystemId(request.getSystemId());
        }
        if (request.getAmpType() != null) {
            entity.setAmpType(request.getAmpType());
        }
        if (request.getGainDb() != null) {
            entity.setGainDb(request.getGainDb());
        }
        if (request.getOutputPowerDbm() != null) {
            entity.setOutputPowerDbm(request.getOutputPowerDbm());
        }
        if (request.getNoiseFigureDb() != null) {
            entity.setNoiseFigureDb(request.getNoiseFigureDb());
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

    public AmplifierResponse toResponse(Amplifier entity) {
        AmplifierResponse response = new AmplifierResponse();
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
        response.setSystemId(entity.getSystemId());
        response.setAmpType(entity.getAmpType());
        response.setGainDb(entity.getGainDb());
        response.setOutputPowerDbm(entity.getOutputPowerDbm());
        response.setNoiseFigureDb(entity.getNoiseFigureDb());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        return response;
    }

    public AmplifierSummary toSummary(Amplifier entity) {
        return new AmplifierSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<AmplifierResponse> toResponses(List<Amplifier> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Amplifier entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSystemId(),
                entity.getAmpType(),
                entity.getGainDb(),
                entity.getOutputPowerDbm(),
                entity.getNoiseFigureDb(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality());
    }
}
