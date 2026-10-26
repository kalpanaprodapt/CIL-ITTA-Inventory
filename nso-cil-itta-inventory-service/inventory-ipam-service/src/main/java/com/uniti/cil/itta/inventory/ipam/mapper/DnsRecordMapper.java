package com.uniti.cil.itta.inventory.ipam.mapper;

import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.DnsRecord;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between DnsRecord entities and DTOs (kept explicit for auditability). */
@Component
public class DnsRecordMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "zone_name", "record_type", "record_value", "ttl", "source_system", "region", "owner", "criticality", "cost_center");

    public DnsRecord toEntity(DnsRecordCreateRequest request) {
        DnsRecord entity = new DnsRecord();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setZoneName(request.getZoneName());
        entity.setRecordType(request.getRecordType());
        entity.setRecordValue(request.getRecordValue());
        entity.setTtl(request.getTtl());
        entity.setSourceSystem(request.getSourceSystem());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        return entity;
    }

    public void applyUpdate(DnsRecordUpdateRequest request, DnsRecord entity) {
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
        if (request.getZoneName() != null) {
            entity.setZoneName(request.getZoneName());
        }
        if (request.getRecordType() != null) {
            entity.setRecordType(request.getRecordType());
        }
        if (request.getRecordValue() != null) {
            entity.setRecordValue(request.getRecordValue());
        }
        if (request.getTtl() != null) {
            entity.setTtl(request.getTtl());
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

    public DnsRecordResponse toResponse(DnsRecord entity) {
        DnsRecordResponse response = new DnsRecordResponse();
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
        response.setZoneName(entity.getZoneName());
        response.setRecordType(entity.getRecordType());
        response.setRecordValue(entity.getRecordValue());
        response.setTtl(entity.getTtl());
        response.setSourceSystem(entity.getSourceSystem());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        return response;
    }

    public DnsRecordSummary toSummary(DnsRecord entity) {
        return new DnsRecordSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<DnsRecordResponse> toResponses(List<DnsRecord> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(DnsRecord entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getZoneName(),
                entity.getRecordType(),
                entity.getRecordValue(),
                entity.getTtl(),
                entity.getSourceSystem(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter());
    }
}
