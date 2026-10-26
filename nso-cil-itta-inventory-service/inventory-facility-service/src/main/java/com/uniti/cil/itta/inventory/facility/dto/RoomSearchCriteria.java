package com.uniti.cil.itta.inventory.facility.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Room search endpoint. */
public class RoomSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID floorId;
    private List<RoomRoomType> roomType;
    private BigDecimal areaSqmMin;
    private BigDecimal areaSqmMax;
    private BigDecimal coolingTonsMin;
    private BigDecimal coolingTonsMax;
    private String fireZone;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<RoomLifecycleStage> lifecycleStage;
    private String sourceSystem;

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
    public UUID getFloorId() { return floorId; }
    public void setFloorId(UUID floorId) { this.floorId = floorId; }
    public List<RoomRoomType> getRoomType() { return roomType; }
    public void setRoomType(List<RoomRoomType> roomType) { this.roomType = roomType; }
    public BigDecimal getAreaSqmMin() { return areaSqmMin; }
    public void setAreaSqmMin(BigDecimal areaSqmMin) { this.areaSqmMin = areaSqmMin; }
    public BigDecimal getAreaSqmMax() { return areaSqmMax; }
    public void setAreaSqmMax(BigDecimal areaSqmMax) { this.areaSqmMax = areaSqmMax; }
    public BigDecimal getCoolingTonsMin() { return coolingTonsMin; }
    public void setCoolingTonsMin(BigDecimal coolingTonsMin) { this.coolingTonsMin = coolingTonsMin; }
    public BigDecimal getCoolingTonsMax() { return coolingTonsMax; }
    public void setCoolingTonsMax(BigDecimal coolingTonsMax) { this.coolingTonsMax = coolingTonsMax; }
    public String getFireZone() { return fireZone; }
    public void setFireZone(String fireZone) { this.fireZone = fireZone; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<RoomLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<RoomLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
