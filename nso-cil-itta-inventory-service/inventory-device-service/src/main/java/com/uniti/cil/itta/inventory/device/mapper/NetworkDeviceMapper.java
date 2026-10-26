package com.uniti.cil.itta.inventory.device.mapper;

import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.NetworkDevice;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between NetworkDevice entities and DTOs (kept explicit for auditability). */
@Component
public class NetworkDeviceMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "hostname", "device_role", "mgmt_ip", "serial_number", "model_id", "rack_unit_id", "effective_to", "labels", "lifecycle_stage", "source_system", "region");

    public NetworkDevice toEntity(NetworkDeviceCreateRequest request) {
        NetworkDevice entity = new NetworkDevice();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setHostname(request.getHostname());
        entity.setDeviceRole(request.getDeviceRole());
        entity.setMgmtIp(request.getMgmtIp());
        entity.setSerialNumber(request.getSerialNumber());
        entity.setModelId(request.getModelId());
        entity.setRackUnitId(request.getRackUnitId());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        return entity;
    }

    public void applyUpdate(NetworkDeviceUpdateRequest request, NetworkDevice entity) {
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
        if (request.getHostname() != null) {
            entity.setHostname(request.getHostname());
        }
        if (request.getDeviceRole() != null) {
            entity.setDeviceRole(request.getDeviceRole());
        }
        if (request.getMgmtIp() != null) {
            entity.setMgmtIp(request.getMgmtIp());
        }
        if (request.getSerialNumber() != null) {
            entity.setSerialNumber(request.getSerialNumber());
        }
        if (request.getModelId() != null) {
            entity.setModelId(request.getModelId());
        }
        if (request.getRackUnitId() != null) {
            entity.setRackUnitId(request.getRackUnitId());
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
        if (request.getRegion() != null) {
            entity.setRegion(request.getRegion());
        }
    }

    public NetworkDeviceResponse toResponse(NetworkDevice entity) {
        NetworkDeviceResponse response = new NetworkDeviceResponse();
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
        response.setHostname(entity.getHostname());
        response.setDeviceRole(entity.getDeviceRole());
        response.setMgmtIp(entity.getMgmtIp());
        response.setSerialNumber(entity.getSerialNumber());
        response.setModelId(entity.getModelId());
        response.setRackUnitId(entity.getRackUnitId());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        return response;
    }

    public NetworkDeviceSummary toSummary(NetworkDevice entity) {
        return new NetworkDeviceSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<NetworkDeviceResponse> toResponses(List<NetworkDevice> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(NetworkDevice entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getHostname(),
                entity.getDeviceRole(),
                entity.getMgmtIp(),
                entity.getSerialNumber(),
                entity.getModelId(),
                entity.getRackUnitId(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion());
    }
}
