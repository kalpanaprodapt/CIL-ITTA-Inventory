package com.uniti.cil.itta.inventory.customer.mapper;

import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.CustomerContact;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between CustomerContact entities and DTOs (kept explicit for auditability). */
@Component
public class CustomerContactMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "customer_id", "full_name", "email", "phone", "contact_role", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public CustomerContact toEntity(CustomerContactCreateRequest request) {
        CustomerContact entity = new CustomerContact();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCustomerId(request.getCustomerId());
        entity.setFullName(request.getFullName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setContactRole(request.getContactRole());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(CustomerContactUpdateRequest request, CustomerContact entity) {
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
        if (request.getCustomerId() != null) {
            entity.setCustomerId(request.getCustomerId());
        }
        if (request.getFullName() != null) {
            entity.setFullName(request.getFullName());
        }
        if (request.getEmail() != null) {
            entity.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            entity.setPhone(request.getPhone());
        }
        if (request.getContactRole() != null) {
            entity.setContactRole(request.getContactRole());
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

    public CustomerContactResponse toResponse(CustomerContact entity) {
        CustomerContactResponse response = new CustomerContactResponse();
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
        response.setCustomerId(entity.getCustomerId());
        response.setFullName(entity.getFullName());
        response.setEmail(entity.getEmail());
        response.setPhone(entity.getPhone());
        response.setContactRole(entity.getContactRole());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public CustomerContactSummary toSummary(CustomerContact entity) {
        return new CustomerContactSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CustomerContactResponse> toResponses(List<CustomerContact> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(CustomerContact entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCustomerId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getContactRole(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
