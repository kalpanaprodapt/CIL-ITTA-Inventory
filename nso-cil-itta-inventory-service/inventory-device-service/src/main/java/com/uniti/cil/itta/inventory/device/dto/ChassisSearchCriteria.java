package com.uniti.cil.itta.inventory.device.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Chassis search endpoint. */
public class ChassisSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID deviceId;
    private Integer slotCountMin;
    private Integer slotCountMax;
    private String serialNumber;
    private BigDecimal backplaneGbpsMin;
    private BigDecimal backplaneGbpsMax;
    private String redundancyMode;
    private List<ChassisLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<ChassisCriticality> criticality;

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
    public UUID getDeviceId() { return deviceId; }
    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }
    public Integer getSlotCountMin() { return slotCountMin; }
    public void setSlotCountMin(Integer slotCountMin) { this.slotCountMin = slotCountMin; }
    public Integer getSlotCountMax() { return slotCountMax; }
    public void setSlotCountMax(Integer slotCountMax) { this.slotCountMax = slotCountMax; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public BigDecimal getBackplaneGbpsMin() { return backplaneGbpsMin; }
    public void setBackplaneGbpsMin(BigDecimal backplaneGbpsMin) { this.backplaneGbpsMin = backplaneGbpsMin; }
    public BigDecimal getBackplaneGbpsMax() { return backplaneGbpsMax; }
    public void setBackplaneGbpsMax(BigDecimal backplaneGbpsMax) { this.backplaneGbpsMax = backplaneGbpsMax; }
    public String getRedundancyMode() { return redundancyMode; }
    public void setRedundancyMode(String redundancyMode) { this.redundancyMode = redundancyMode; }
    public List<ChassisLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<ChassisLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<ChassisCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<ChassisCriticality> criticality) { this.criticality = criticality; }}
