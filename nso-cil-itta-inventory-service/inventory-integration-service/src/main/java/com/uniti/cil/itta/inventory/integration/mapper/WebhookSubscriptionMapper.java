package com.uniti.cil.itta.inventory.integration.mapper;

import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.WebhookSubscription;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between WebhookSubscription entities and DTOs (kept explicit for auditability). */
@Component
public class WebhookSubscriptionMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "callback_url", "event_types", "secret_ref", "active", "retry_limit", "labels", "lifecycle_stage", "source_system", "region", "owner");

    public WebhookSubscription toEntity(WebhookSubscriptionCreateRequest request) {
        WebhookSubscription entity = new WebhookSubscription();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCallbackUrl(request.getCallbackUrl());
        entity.setEventTypes(request.getEventTypes());
        entity.setSecretRef(request.getSecretRef());
        entity.setActive(request.getActive());
        entity.setRetryLimit(request.getRetryLimit());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        return entity;
    }

    public void applyUpdate(WebhookSubscriptionUpdateRequest request, WebhookSubscription entity) {
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
        if (request.getCallbackUrl() != null) {
            entity.setCallbackUrl(request.getCallbackUrl());
        }
        if (request.getEventTypes() != null) {
            entity.setEventTypes(request.getEventTypes());
        }
        if (request.getSecretRef() != null) {
            entity.setSecretRef(request.getSecretRef());
        }
        if (request.getActive() != null) {
            entity.setActive(request.getActive());
        }
        if (request.getRetryLimit() != null) {
            entity.setRetryLimit(request.getRetryLimit());
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

    public WebhookSubscriptionResponse toResponse(WebhookSubscription entity) {
        WebhookSubscriptionResponse response = new WebhookSubscriptionResponse();
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
        response.setCallbackUrl(entity.getCallbackUrl());
        response.setEventTypes(entity.getEventTypes());
        response.setSecretRef(entity.getSecretRef());
        response.setActive(entity.getActive());
        response.setRetryLimit(entity.getRetryLimit());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        return response;
    }

    public WebhookSubscriptionSummary toSummary(WebhookSubscription entity) {
        return new WebhookSubscriptionSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<WebhookSubscriptionResponse> toResponses(List<WebhookSubscription> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(WebhookSubscription entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCallbackUrl(),
                entity.getEventTypes(),
                entity.getSecretRef(),
                entity.getActive(),
                entity.getRetryLimit(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner());
    }
}
