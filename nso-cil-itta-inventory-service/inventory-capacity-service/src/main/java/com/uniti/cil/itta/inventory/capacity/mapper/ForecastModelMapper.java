package com.uniti.cil.itta.inventory.capacity.mapper;

import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.ForecastModel;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between ForecastModel entities and DTOs (kept explicit for auditability). */
@Component
public class ForecastModelMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "model_name", "algorithm", "trained_on", "mape", "criticality", "cost_center", "notes", "effective_from", "effective_to");

    public ForecastModel toEntity(ForecastModelCreateRequest request) {
        ForecastModel entity = new ForecastModel();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setModelName(request.getModelName());
        entity.setAlgorithm(request.getAlgorithm());
        entity.setTrainedOn(request.getTrainedOn());
        entity.setMape(request.getMape());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        return entity;
    }

    public void applyUpdate(ForecastModelUpdateRequest request, ForecastModel entity) {
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
        if (request.getModelName() != null) {
            entity.setModelName(request.getModelName());
        }
        if (request.getAlgorithm() != null) {
            entity.setAlgorithm(request.getAlgorithm());
        }
        if (request.getTrainedOn() != null) {
            entity.setTrainedOn(request.getTrainedOn());
        }
        if (request.getMape() != null) {
            entity.setMape(request.getMape());
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
        if (request.getEffectiveTo() != null) {
            entity.setEffectiveTo(request.getEffectiveTo());
        }
    }

    public ForecastModelResponse toResponse(ForecastModel entity) {
        ForecastModelResponse response = new ForecastModelResponse();
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
        response.setModelName(entity.getModelName());
        response.setAlgorithm(entity.getAlgorithm());
        response.setTrainedOn(entity.getTrainedOn());
        response.setMape(entity.getMape());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        return response;
    }

    public ForecastModelSummary toSummary(ForecastModel entity) {
        return new ForecastModelSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ForecastModelResponse> toResponses(List<ForecastModel> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(ForecastModel entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getModelName(),
                entity.getAlgorithm(),
                entity.getTrainedOn(),
                entity.getMape(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo());
    }
}
