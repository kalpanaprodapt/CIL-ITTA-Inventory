package com.uniti.cil.itta.inventory.capacity.mapper;

import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.Reservation;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Reservation entities and DTOs (kept explicit for auditability). */
@Component
public class ReservationMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "resource_ref", "reserved_for", "quantity", "start_date", "end_date", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public Reservation toEntity(ReservationCreateRequest request) {
        Reservation entity = new Reservation();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setResourceRef(request.getResourceRef());
        entity.setReservedFor(request.getReservedFor());
        entity.setQuantity(request.getQuantity());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(ReservationUpdateRequest request, Reservation entity) {
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
        if (request.getResourceRef() != null) {
            entity.setResourceRef(request.getResourceRef());
        }
        if (request.getReservedFor() != null) {
            entity.setReservedFor(request.getReservedFor());
        }
        if (request.getQuantity() != null) {
            entity.setQuantity(request.getQuantity());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.setEndDate(request.getEndDate());
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
        if (request.getLifecycleStage() != null) {
            entity.setLifecycleStage(request.getLifecycleStage());
        }
    }

    public ReservationResponse toResponse(Reservation entity) {
        ReservationResponse response = new ReservationResponse();
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
        response.setResourceRef(entity.getResourceRef());
        response.setReservedFor(entity.getReservedFor());
        response.setQuantity(entity.getQuantity());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public ReservationSummary toSummary(Reservation entity) {
        return new ReservationSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ReservationResponse> toResponses(List<Reservation> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Reservation entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getResourceRef(),
                entity.getReservedFor(),
                entity.getQuantity(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
