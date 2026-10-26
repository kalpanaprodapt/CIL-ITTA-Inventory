package com.uniti.cil.itta.inventory.facility.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Power Feed search endpoint. */
public class PowerFeedSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID roomId;
    private List<PowerFeedFeedType> feedType;
    private BigDecimal voltageMin;
    private BigDecimal voltageMax;
    private BigDecimal amperageMin;
    private BigDecimal amperageMax;
    private String phase;
    private String breakerLabel;
    private String redundancyGroup;
    private String owner;
    private List<PowerFeedCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;

    public String getQ() { return q; }
    public void setQ(String q) { this.q = q; }
    public List<LifecycleStatus> getStatus() { return status; }
    public void setStatus(List<LifecycleStatus> status) { this.status = status; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }
    public List<PowerFeedFeedType> getFeedType() { return feedType; }
    public void setFeedType(List<PowerFeedFeedType> feedType) { this.feedType = feedType; }
    public BigDecimal getVoltageMin() { return voltageMin; }
    public void setVoltageMin(BigDecimal voltageMin) { this.voltageMin = voltageMin; }
    public BigDecimal getVoltageMax() { return voltageMax; }
    public void setVoltageMax(BigDecimal voltageMax) { this.voltageMax = voltageMax; }
    public BigDecimal getAmperageMin() { return amperageMin; }
    public void setAmperageMin(BigDecimal amperageMin) { this.amperageMin = amperageMin; }
    public BigDecimal getAmperageMax() { return amperageMax; }
    public void setAmperageMax(BigDecimal amperageMax) { this.amperageMax = amperageMax; }
    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }
    public String getBreakerLabel() { return breakerLabel; }
    public void setBreakerLabel(String breakerLabel) { this.breakerLabel = breakerLabel; }
    public String getRedundancyGroup() { return redundancyGroup; }
    public void setRedundancyGroup(String redundancyGroup) { this.redundancyGroup = redundancyGroup; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<PowerFeedCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<PowerFeedCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
