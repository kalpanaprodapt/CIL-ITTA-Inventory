package com.uniti.cil.itta.inventory.customer.mapper;

import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.Customer;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Customer entities and DTOs (kept explicit for auditability). */
@Component
public class CustomerMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "legal_name", "segment", "tax_id", "industry", "account_manager", "cost_center", "notes", "effective_from", "effective_to", "labels");

    public Customer toEntity(CustomerCreateRequest request) {
        Customer entity = new Customer();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setLegalName(request.getLegalName());
        entity.setSegment(request.getSegment());
        entity.setTaxId(request.getTaxId());
        entity.setIndustry(request.getIndustry());
        entity.setAccountManager(request.getAccountManager());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setLabels(request.getLabels());
        return entity;
    }

    public void applyUpdate(CustomerUpdateRequest request, Customer entity) {
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
        if (request.getLegalName() != null) {
            entity.setLegalName(request.getLegalName());
        }
        if (request.getSegment() != null) {
            entity.setSegment(request.getSegment());
        }
        if (request.getTaxId() != null) {
            entity.setTaxId(request.getTaxId());
        }
        if (request.getIndustry() != null) {
            entity.setIndustry(request.getIndustry());
        }
        if (request.getAccountManager() != null) {
            entity.setAccountManager(request.getAccountManager());
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
        if (request.getLabels() != null) {
            entity.setLabels(request.getLabels());
        }
    }

    public CustomerResponse toResponse(Customer entity) {
        CustomerResponse response = new CustomerResponse();
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
        response.setLegalName(entity.getLegalName());
        response.setSegment(entity.getSegment());
        response.setTaxId(entity.getTaxId());
        response.setIndustry(entity.getIndustry());
        response.setAccountManager(entity.getAccountManager());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        response.setEffectiveTo(entity.getEffectiveTo());
        response.setLabels(entity.getLabels());
        return response;
    }

    public CustomerSummary toSummary(Customer entity) {
        return new CustomerSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CustomerResponse> toResponses(List<Customer> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Customer entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getLegalName(),
                entity.getSegment(),
                entity.getTaxId(),
                entity.getIndustry(),
                entity.getAccountManager(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLabels());
    }
}
