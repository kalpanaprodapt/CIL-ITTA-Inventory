package com.uniti.cil.itta.inventory.facility.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Power Feed; null properties are left unchanged. */
public class PowerFeedUpdateRequest {

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
    private UUID roomId;
    private PowerFeedFeedType feedType;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal voltage;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal amperage;
    @Size(max = 255)
    private String phase;
    @Size(max = 255)
    private String breakerLabel;
    @Size(max = 255)
    private String redundancyGroup;
    @Size(max = 255)
    private String owner;
    private PowerFeedCriticality criticality;
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
