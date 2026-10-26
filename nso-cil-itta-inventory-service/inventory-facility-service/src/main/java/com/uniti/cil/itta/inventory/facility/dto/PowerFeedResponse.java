package com.uniti.cil.itta.inventory.facility.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Read model returned by the Power Feed API. */
public class PowerFeedResponse {

    private UUID id;
    private String tenantId;
    private Long version;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    private String code;
    private String name;
    private String description;
    private LifecycleStatus status;
    private String externalRef;
    private UUID roomId;
    private PowerFeedFeedType feedType;
    private BigDecimal voltage;
    private BigDecimal amperage;
    private String phase;
    private String breakerLabel;
    private String redundancyGroup;
    private String owner;
    private PowerFeedCriticality criticality;
    private String costCenter;
    private String notes;
    private LocalDate effectiveFrom;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

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
    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }
    public PowerFeedFeedType getFeedType() { return feedType; }
    public void setFeedType(PowerFeedFeedType feedType) { this.feedType = feedType; }
    public BigDecimal getVoltage() { return voltage; }
    public void setVoltage(BigDecimal voltage) { this.voltage = voltage; }
    public BigDecimal getAmperage() { return amperage; }
    public void setAmperage(BigDecimal amperage) { this.amperage = amperage; }
    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
    public String getBreakerLabel() { return breakerLabel; }
    public void setBreakerLabel(String breakerLabel) { this.breakerLabel = breakerLabel; }
    public String getRedundancyGroup() { return redundancyGroup; }
    public void setRedundancyGroup(String redundancyGroup) { this.redundancyGroup = redundancyGroup; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public PowerFeedCriticality getCriticality() { return criticality; }
    public void setCriticality(PowerFeedCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }}
