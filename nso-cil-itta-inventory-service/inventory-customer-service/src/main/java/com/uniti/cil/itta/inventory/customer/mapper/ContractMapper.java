package com.uniti.cil.itta.inventory.customer.mapper;

import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.Contract;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Contract entities and DTOs (kept explicit for auditability). */
@Component
public class ContractMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "customer_id", "contract_number", "start_date", "end_date", "term_months", "auto_renew", "source_system", "region", "owner", "criticality", "cost_center");

    public Contract toEntity(ContractCreateRequest request) {
        Contract entity = new Contract();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setCustomerId(request.getCustomerId());
        entity.setContractNumber(request.getContractNumber());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setTermMonths(request.getTermMonths());
        entity.setAutoRenew(request.getAutoRenew());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(ContractUpdateRequest request, Contract entity) {
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
        if (request.getContractNumber() != null) {
            entity.setContractNumber(request.getContractNumber());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.setEndDate(request.getEndDate());
        }
        if (request.getTermMonths() != null) {
            entity.setTermMonths(request.getTermMonths());
        }
        if (request.getAutoRenew() != null) {
            entity.setAutoRenew(request.getAutoRenew());
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
        if (request.getCriticality() != null) {
            entity.setCriticality(request.getCriticality());
        }
        if (request.getCostCenter() != null) {
            entity.setCostCenter(request.getCostCenter());
        }
    }

    public ContractResponse toResponse(Contract entity) {
        ContractResponse response = new ContractResponse();
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
        response.setContractNumber(entity.getContractNumber());
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        response.setTermMonths(entity.getTermMonths());
        response.setAutoRenew(entity.getAutoRenew());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public ContractSummary toSummary(Contract entity) {
        return new ContractSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<ContractResponse> toResponses(List<Contract> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Contract entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getCustomerId(),
                entity.getContractNumber(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getTermMonths(),
                entity.getAutoRenew(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
