package com.uniti.cil.itta.inventory.device.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Power Supply search endpoint. */
public class PowerSupplySearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID chassisId;
    private Integer slotNumberMin;
    private Integer slotNumberMax;
    private Integer wattageMin;
    private Integer wattageMax;
    private UUID feedId;
    private String serialNumber;
    private BigDecimal efficiencyMin;
    private BigDecimal efficiencyMax;
    private List<PowerSupplyCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;

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
    public UUID getChassisId() { return chassisId; }
    public void setChassisId(UUID chassisId) { this.chassisId = chassisId; }
    public Integer getSlotNumberMin() { return slotNumberMin; }
    public void setSlotNumberMin(Integer slotNumberMin) { this.slotNumberMin = slotNumberMin; }
    public Integer getSlotNumberMax() { return slotNumberMax; }
    public void setSlotNumberMax(Integer slotNumberMax) { this.slotNumberMax = slotNumberMax; }
    public Integer getWattageMin() { return wattageMin; }
    public void setWattageMin(Integer wattageMin) { this.wattageMin = wattageMin; }
    public Integer getWattageMax() { return wattageMax; }
    public void setWattageMax(Integer wattageMax) { this.wattageMax = wattageMax; }
    public UUID getFeedId() { return feedId; }
    public void setFeedId(UUID feedId) { this.feedId = feedId; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public BigDecimal getEfficiencyMin() { return efficiencyMin; }
    public void setEfficiencyMin(BigDecimal efficiencyMin) { this.efficiencyMin = efficiencyMin; }
    public BigDecimal getEfficiencyMax() { return efficiencyMax; }
    public void setEfficiencyMax(BigDecimal efficiencyMax) { this.efficiencyMax = efficiencyMax; }
    public List<PowerSupplyCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<PowerSupplyCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }}
