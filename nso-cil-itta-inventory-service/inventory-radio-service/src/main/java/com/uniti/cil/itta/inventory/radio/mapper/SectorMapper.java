package com.uniti.cil.itta.inventory.radio.mapper;

import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.Sector;
import java.util.List;
import org.springframework.stereotype.Component;

/** Hand written mapper between Sector entities and DTOs (kept explicit for auditability). */
@Component
public class SectorMapper {

    public static final List<String> CSV_HEADER = List.of("code", "name", "description", "status", "external_ref", "base_station_id", "sector_number", "pci", "frequency_mhz", "bandwidth_mhz", "owner", "criticality", "cost_center", "notes", "effective_from");

    public Sector toEntity(SectorCreateRequest request) {
        Sector entity = new Sector();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStatus(request.getStatus());
        entity.setExternalRef(request.getExternalRef());
        entity.setBaseStationId(request.getBaseStationId());
        entity.setSectorNumber(request.getSectorNumber());
        entity.setPci(request.getPci());
        entity.setFrequencyMhz(request.getFrequencyMhz());
        entity.setBandwidthMhz(request.getBandwidthMhz());
        entity.setOwner(request.getOwner());
        entity.setCriticality(request.getCriticality());
        entity.setCostCenter(request.getCostCenter());
        entity.setNotes(request.getNotes());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        return entity;
    }

    public void applyUpdate(SectorUpdateRequest request, Sector entity) {
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
        if (request.getBaseStationId() != null) {
            entity.setBaseStationId(request.getBaseStationId());
        }
        if (request.getSectorNumber() != null) {
            entity.setSectorNumber(request.getSectorNumber());
        }
        if (request.getPci() != null) {
            entity.setPci(request.getPci());
        }
        if (request.getFrequencyMhz() != null) {
            entity.setFrequencyMhz(request.getFrequencyMhz());
        }
        if (request.getBandwidthMhz() != null) {
            entity.setBandwidthMhz(request.getBandwidthMhz());
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
        if (request.getEffectiveFrom() != null) {
            entity.setEffectiveFrom(request.getEffectiveFrom());
        }
    }

    public SectorResponse toResponse(Sector entity) {
        SectorResponse response = new SectorResponse();
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
        response.setBaseStationId(entity.getBaseStationId());
        response.setSectorNumber(entity.getSectorNumber());
        response.setPci(entity.getPci());
        response.setFrequencyMhz(entity.getFrequencyMhz());
        response.setBandwidthMhz(entity.getBandwidthMhz());
        response.setOwner(entity.getOwner());
        response.setCriticality(entity.getCriticality());
        response.setCostCenter(entity.getCostCenter());
        response.setNotes(entity.getNotes());
        response.setEffectiveFrom(entity.getEffectiveFrom());
        return response;
    }

    public SectorSummary toSummary(Sector entity) {
        return new SectorSummary(entity.getId(), entity.getCode(), entity.getName(), entity.getStatus());
    }

    public List<SectorResponse> toResponses(List<Sector> entities) {
        return entities.stream().map(this::toResponse).toList();
    }

    public List<Object> toCsvRow(Sector entity) {
        return java.util.Arrays.asList(
                entity.getCode(),
                entity.getName(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getExternalRef(),
                entity.getBaseStationId(),
                entity.getSectorNumber(),
                entity.getPci(),
                entity.getFrequencyMhz(),
                entity.getBandwidthMhz(),
                entity.getOwner(),
                entity.getCriticality(),
                entity.getCostCenter(),
                entity.getNotes(),
                entity.getEffectiveFrom());
    }
}
