package com.uniti.cil.itta.inventory.radio.mapper;

import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.RadioUnit;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between RadioUnit entities and DTOs (kept explicit for auditability). */
@Component
public class RadioUnitMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "site_id", "unit_type", "band_mhz", "tx_power_dbm", "serial_number", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public RadioUnit toEntity(RadioUnitCreateRequest request) {
        RadioUnit entity = new RadioUnit();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setSiteId(request.getSiteId());
        entity.setUnitType(request.getUnitType());
        entity.setBandMhz(request.getBandMhz());
        entity.setTxPowerDbm(request.getTxPowerDbm());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(RadioUnitUpdateRequest request, RadioUnit entity) {
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
        if (request.getSiteId() != null) {
            entity.setSiteId(request.getSiteId());
        }
        if (request.getUnitType() != null) {
            entity.setUnitType(request.getUnitType());
        }
        if (request.getBandMhz() != null) {
            entity.setBandMhz(request.getBandMhz());
        }
        if (request.getTxPowerDbm() != null) {
            entity.setTxPowerDbm(request.getTxPowerDbm());
        }
        if (request.getSerialNumber() != null) {
            entity.setSerialNumber(request.getSerialNumber());
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
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
        }
        if (request.getSourceSystem() != null) {
            entity.setSourceSystem(request.getSourceSystem());
        }
    }

    public RadioUnitResponse toResponse(RadioUnit entity) {
        RadioUnitResponse response = new RadioUnitResponse();
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
        response.setSiteId(entity.getSiteId());
        response.setUnitType(entity.getUnitType());
        response.setBandMhz(entity.getBandMhz());
        response.setTxPowerDbm(entity.getTxPowerDbm());
        response.setSerialNumber(entity.getSerialNumber());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public RadioUnitSummary toSummary(RadioUnit entity) {
        return new RadioUnitSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<RadioUnitResponse> toResponses(List<RadioUnit> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(RadioUnit entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getSiteId(),
                entity.getUnitType(),
                entity.getBandMhz(),
                entity.getTxPowerDbm(),
                entity.getSerialNumber(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
