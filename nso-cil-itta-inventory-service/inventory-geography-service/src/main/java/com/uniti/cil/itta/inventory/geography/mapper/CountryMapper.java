package com.uniti.cil.itta.inventory.geography.mapper;

import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.Country;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Country entities and DTOs (kept explicit for auditability). */
@Component
public class CountryMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "iso_code", "iso_numeric", "dial_code", "currency", "regulatory_body", "timezone", "region", "owner", "criticality", "cost_center", "notes");

    public Country toEntity(CountryCreateRequest request) {
        Country entity = new Country();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setIsoCode(request.getIsoCode());
        entity.setIsoNumeric(request.getIsoNumeric());
        entity.setDialCode(request.getDialCode());
        entity.setCurrency(request.getCurrency());
        entity.setRegulatoryBody(request.getRegulatoryBody());
        entity.setTimezone(request.getTimezone());
        entity.setRegion(request.getRegion());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public void applyUpdate(CountryUpdateRequest request, Country entity) {
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
        if (request.getIsoCode() != null) {
            entity.setIsoCode(request.getIsoCode());
        }
        if (request.getIsoNumeric() != null) {
            entity.setIsoNumeric(request.getIsoNumeric());
        }
        if (request.getDialCode() != null) {
            entity.setDialCode(request.getDialCode());
        }
        if (request.getCurrency() != null) {
            entity.setCurrency(request.getCurrency());
        }
        if (request.getRegulatoryBody() != null) {
            entity.setRegulatoryBody(request.getRegulatoryBody());
        }
        if (request.getTimezone() != null) {
            entity.setTimezone(request.getTimezone());
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
        if (request.getNotes() != null) {
            entity.setNotes(request.getNotes());
        }
    }

    public CountryResponse toResponse(Country entity) {
        CountryResponse response = new CountryResponse();
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
        response.setIsoCode(entity.getIsoCode());
        response.setIsoNumeric(entity.getIsoNumeric());
        response.setDialCode(entity.getDialCode());
        response.setCurrency(entity.getCurrency());
        response.setRegulatoryBody(entity.getRegulatoryBody());
        response.setTimezone(entity.getTimezone());
        response.setRegion(entity.getRegion());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        return response;
    }

    public CountrySummary toSummary(Country entity) {
        return new CountrySummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<CountryResponse> toResponses(List<Country> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Country entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getIsoCode(),
                entity.getIsoNumeric(),
                entity.getDialCode(),
                entity.getCurrency(),
                entity.getRegulatoryBody(),
                entity.getTimezone(),
                entity.getRegion(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes());
    }
}
