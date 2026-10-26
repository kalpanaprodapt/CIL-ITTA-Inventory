package com.uniti.cil.itta.inventory.radio.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Base Station search endpoint. */
public class BaseStationSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID siteId;
    private Long enbIdMin;
    private Long enbIdMax;
    private List<BaseStationTechnology> technology;
    private Integer sectorCountMin;
    private Integer sectorCountMax;
    private Integer cellCountMin;
    private Integer cellCountMax;
    private String labels;
    private List<BaseStationLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;

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
    public UUID getSiteId() { return siteId; }
    public void setSiteId(UUID siteId) { this.siteId = siteId; }
    public Long getEnbIdMin() { return enbIdMin; }
    public void setEnbIdMin(Long enbIdMin) { this.enbIdMin = enbIdMin; }
    public Long getEnbIdMax() { return enbIdMax; }
    public void setEnbIdMax(Long enbIdMax) { this.enbIdMax = enbIdMax; }
    public List<BaseStationTechnology> getTechnology() { return technology; }
    public void setTechnology(List<BaseStationTechnology> technology) { this.technology = technology; }
    public Integer getSectorCountMin() { return sectorCountMin; }
    public void setSectorCountMin(Integer sectorCountMin) { this.sectorCountMin = sectorCountMin; }
    public Integer getSectorCountMax() { return sectorCountMax; }
    public void setSectorCountMax(Integer sectorCountMax) { this.sectorCountMax = sectorCountMax; }
    public Integer getCellCountMin() { return cellCountMin; }
    public void setCellCountMin(Integer cellCountMin) { this.cellCountMin = cellCountMin; }
    public Integer getCellCountMax() { return cellCountMax; }
    public void setCellCountMax(Integer cellCountMax) { this.cellCountMax = cellCountMax; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<BaseStationLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<BaseStationLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
