package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Sector; null properties are left unchanged. */
public class SectorUpdateRequest {

    private Long expectedVersion;

    @Size(max = 255)
    private String code;
    @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    private UUID baseStationId;
    private Integer sectorNumber;
    private Integer pci;
    private Integer frequencyMhz;
    private Integer bandwidthMhz;
    @Size(max = 255)
    private String owner;
    private SectorCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
    private LocalDate effectiveFrom;
    public Long getExpectedVersion() { return expectedVersion; }
    public void setExpectedVersion(Long expectedVersion) { this.expectedVersion = expectedVersion; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LifecycleStatus getStatus() { return status; }
    public void setStatus(LifecycleStatus status) { this.status = status; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public UUID getBaseStationId() { return baseStationId; }
    public void setBaseStationId(UUID baseStationId) { this.baseStationId = baseStationId; }
    public Integer getSectorNumber() { return sectorNumber; }
    public void setSectorNumber(Integer sectorNumber) { this.sectorNumber = sectorNumber; }
    public Integer getPci() { return pci; }
    public void setPci(Integer pci) { this.pci = pci; }
    public Integer getFrequencyMhz() { return frequencyMhz; }
    public void setFrequencyMhz(Integer frequencyMhz) { this.frequencyMhz = frequencyMhz; }
    public Integer getBandwidthMhz() { return bandwidthMhz; }
    public void setBandwidthMhz(Integer bandwidthMhz) { this.bandwidthMhz = bandwidthMhz; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public SectorCriticality getCriticality() { return criticality; }
    public void setCriticality(SectorCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }}
