package com.uniti.cil.itta.inventory.customer.mapper;

import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.CustomerAccount;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between CustomerAccount entities and DTOs (kept explicit for auditability). */
@Component
public class CustomerAccountMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "customer_id", "account_number", "billing_cycle", "currency", "credit_limit", "effective_from", "effective_to", "labels", "lifecycle_stage", "source_system");

    public CustomerAccount toEntity(CustomerAccountCreateRequest request) {
        CustomerAccount entity = new CustomerAccount();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCustomerId(request.getCustomerId());
        entity.setAccountNumber(request.getAccountNumber());
        entity.setBillingCycle(request.getBillingCycle());
        entity.setCurrency(request.getCurrency());
        entity.setCreditLimit(request.getCreditLimit());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        entity.setLifecycleStage(request.getLifecycleStage());
        entity.setSourceSystem(request.getSourceSystem());
        return entity;
    }

    public void applyUpdate(CustomerAccountUpdateRequest request, CustomerAccount entity) {
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
        if (request.getAccountNumber() != null) {
            entity.setAccountNumber(request.getAccountNumber());
        }
        if (request.getBillingCycle() != null) {
            entity.setBillingCycle(request.getBillingCycle());
        }
        if (request.getCurrency() != null) {
            entity.setCurrency(request.getCurrency());
        }
        if (request.getCreditLimit() != null) {
            entity.setCreditLimit(request.getCreditLimit());
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

    public CustomerAccountResponse toResponse(CustomerAccount entity) {
        CustomerAccountResponse response = new CustomerAccountResponse();
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
        response.setAccountNumber(entity.getAccountNumber());
        response.setBillingCycle(entity.getBillingCycle());
        response.setCurrency(entity.getCurrency());
        response.setCreditLimit(entity.getCreditLimit());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        response.setLifecycleStage(entity.getLifecycleStage());
        response.setSourceSystem(entity.getSourceSystem());
        return response;
    }

    public CustomerAccountSummary toSummary(CustomerAccount entity) {
        return new CustomerAccountSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CustomerAccountResponse> toResponses(List<CustomerAccount> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(CustomerAccount entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCustomerId(),
                entity.getAccountNumber(),
                entity.getBillingCycle(),
                entity.getCurrency(),
                entity.getCreditLimit(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels(),
                entity.getLifecycleStage(),
                entity.getSourceSystem());
    }
}
