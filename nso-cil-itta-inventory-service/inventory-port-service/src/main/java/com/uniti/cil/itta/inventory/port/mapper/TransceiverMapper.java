package com.uniti.cil.itta.inventory.port.mapper;

import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.Transceiver;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Transceiver entities and DTOs (kept explicit for auditability). */
@Component
public class TransceiverMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "port_id", "form_factor", "wavelength_nm", "reach_km", "serial_number", "vendor_part", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public Transceiver toEntity(TransceiverCreateRequest request) {
        Transceiver entity = new Transceiver();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setPortId(request.getPortId());
        entity.setFormFactor(request.getFormFactor());
        entity.setWavelengthNm(request.getWavelengthNm());
        entity.setReachKm(request.getReachKm());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setVendorPart(request.getVendorPart());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(TransceiverUpdateRequest request, Transceiver entity) {
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
        if (request.getFormFactor() != null) {
            entity.setFormFactor(request.getFormFactor());
        }
        if (request.getWavelengthNm() != null) {
            entity.setWavelengthNm(request.getWavelengthNm());
        }
        if (request.getReachKm() != null) {
            entity.setReachKm(request.getReachKm());
        }
        if (request.getSerialNumber() != null) {
            entity.setSerialNumber(request.getSerialNumber());
        }
        if (request.getVendorPart() != null) {
            entity.setVendorPart(request.getVendorPart());
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
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
        if (request.getOwner() != null) {
            entity.setOwner(request.getOwner());
        }
    }

    public TransceiverResponse toResponse(Transceiver entity) {
        TransceiverResponse response = new TransceiverResponse();
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
        response.setFormFactor(entity.getFormFactor());
        response.setWavelengthNm(entity.getWavelengthNm());
        response.setReachKm(entity.getReachKm());
        response.setSerialNumber(entity.getSerialNumber());
        response.setVendorPart(entity.getVendorPart());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public TransceiverSummary toSummary(Transceiver entity) {
        return new TransceiverSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<TransceiverResponse> toResponses(List<Transceiver> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Transceiver entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getPortId(),
                entity.getFormFactor(),
                entity.getWavelengthNm(),
                entity.getReachKm(),
                entity.getSerialNumber(),
                entity.getVendorPart(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
