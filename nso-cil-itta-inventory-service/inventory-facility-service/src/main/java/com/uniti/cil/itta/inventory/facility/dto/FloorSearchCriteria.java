package com.uniti.cil.itta.inventory.facility.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Floor search endpoint. */
public class FloorSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID buildingId;
    private Integer levelMin;
    private Integer levelMax;
    private BigDecimal areaSqmMin;
    private BigDecimal areaSqmMax;
    private BigDecimal loadCapacityKgMin;
    private BigDecimal loadCapacityKgMax;
    private Boolean hasRaisedFloor;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;

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
    public UUID getBuildingId() { return buildingId; }
    public void setBuildingId(UUID buildingId) { this.buildingId = buildingId; }
    public Integer getLevelMin() { return levelMin; }
    public void setLevelMin(Integer levelMin) { this.levelMin = levelMin; }
    public Integer getLevelMax() { return levelMax; }
    public void setLevelMax(Integer levelMax) { this.levelMax = levelMax; }
    public BigDecimal getAreaSqmMin() { return areaSqmMin; }
    public void setAreaSqmMin(BigDecimal areaSqmMin) { this.areaSqmMin = areaSqmMin; }
    public BigDecimal getAreaSqmMax() { return areaSqmMax; }
    public void setAreaSqmMax(BigDecimal areaSqmMax) { this.areaSqmMax = areaSqmMax; }
    public BigDecimal getLoadCapacityKgMin() { return loadCapacityKgMin; }
    public void setLoadCapacityKgMin(BigDecimal loadCapacityKgMin) { this.loadCapacityKgMin = loadCapacityKgMin; }
    public BigDecimal getLoadCapacityKgMax() { return loadCapacityKgMax; }
    public void setLoadCapacityKgMax(BigDecimal loadCapacityKgMax) { this.loadCapacityKgMax = loadCapacityKgMax; }
    public Boolean getHasRaisedFloor() { return hasRaisedFloor; }
    public void setHasRaisedFloor(Boolean hasRaisedFloor) { this.hasRaisedFloor = hasRaisedFloor; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }}
