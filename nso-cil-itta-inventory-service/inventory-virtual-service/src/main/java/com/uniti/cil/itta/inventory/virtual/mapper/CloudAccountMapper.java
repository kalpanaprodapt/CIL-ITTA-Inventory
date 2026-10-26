package com.uniti.cil.itta.inventory.virtual.mapper;

import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.CloudAccount;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between CloudAccount entities and DTOs (kept explicit for auditability). */
@Component
public class CloudAccountMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "provider", "account_ref", "subscription_name", "monthly_budget", "environment", "notes", "effective_from", "effective_to", "labels", "lifecycle_stage");

    public CloudAccount toEntity(CloudAccountCreateRequest request) {
        CloudAccount entity = new CloudAccount();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setProvider(request.getProvider());
        entity.setAccountRef(request.getAccountRef());
        entity.setSubscriptionName(request.getSubscriptionName());
        entity.setMonthlyBudget(request.getMonthlyBudget());
        entity.setEnvironment(request.getEnvironment());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        return entity;
    }

    public void applyUpdate(CloudAccountUpdateRequest request, CloudAccount entity) {
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
        if (request.getProvider() != null) {
            entity.setProvider(request.getProvider());
        }
        if (request.getAccountRef() != null) {
            entity.setAccountRef(request.getAccountRef());
        }
        if (request.getSubscriptionName() != null) {
            entity.setSubscriptionName(request.getSubscriptionName());
        }
        if (request.getMonthlyBudget() != null) {
            entity.setMonthlyBudget(request.getMonthlyBudget());
        }
        if (request.getEnvironment() != null) {
            entity.setEnvironment(request.getEnvironment());
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

    public CloudAccountResponse toResponse(CloudAccount entity) {
        CloudAccountResponse response = new CloudAccountResponse();
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
        response.setProvider(entity.getProvider());
        response.setAccountRef(entity.getAccountRef());
        response.setSubscriptionName(entity.getSubscriptionName());
        response.setMonthlyBudget(entity.getMonthlyBudget());
        response.setEnvironment(entity.getEnvironment());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        return response;
    }

    public CloudAccountSummary toSummary(CloudAccount entity) {
        return new CloudAccountSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CloudAccountResponse> toResponses(List<CloudAccount> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(CloudAccount entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getProvider(),
                entity.getAccountRef(),
                entity.getSubscriptionName(),
                entity.getMonthlyBudget(),
                entity.getEnvironment(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage());
    }
}
