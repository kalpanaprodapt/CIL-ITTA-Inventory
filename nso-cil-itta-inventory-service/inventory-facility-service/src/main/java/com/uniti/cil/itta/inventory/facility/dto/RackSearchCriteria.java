package com.uniti.cil.itta.inventory.facility.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Rack search endpoint. */
public class RackSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID roomId;
    private Integer rackUnitsMin;
    private Integer rackUnitsMax;
    private Integer widthMmMin;
    private Integer widthMmMax;
    private Integer depthMmMin;
    private Integer depthMmMax;
    private BigDecimal maxWeightKgMin;
    private BigDecimal maxWeightKgMax;
    private String rowLabel;
    private String position;
    private String labels;
    private List<RackLifecycleStage> lifecycleStage;
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
    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }
    public Integer getRackUnitsMin() { return rackUnitsMin; }
    public void setRackUnitsMin(Integer rackUnitsMin) { this.rackUnitsMin = rackUnitsMin; }
    public Integer getRackUnitsMax() { return rackUnitsMax; }
    public void setRackUnitsMax(Integer rackUnitsMax) { this.rackUnitsMax = rackUnitsMax; }
    public Integer getWidthMmMin() { return widthMmMin; }
    public void setWidthMmMin(Integer widthMmMin) { this.widthMmMin = widthMmMin; }
    public Integer getWidthMmMax() { return widthMmMax; }
    public void setWidthMmMax(Integer widthMmMax) { this.widthMmMax = widthMmMax; }
    public Integer getDepthMmMin() { return depthMmMin; }
    public void setDepthMmMin(Integer depthMmMin) { this.depthMmMin = depthMmMin; }
    public Integer getDepthMmMax() { return depthMmMax; }
    public void setDepthMmMax(Integer depthMmMax) { this.depthMmMax = depthMmMax; }
    public BigDecimal getMaxWeightKgMin() { return maxWeightKgMin; }
    public void setMaxWeightKgMin(BigDecimal maxWeightKgMin) { this.maxWeightKgMin = maxWeightKgMin; }
    public BigDecimal getMaxWeightKgMax() { return maxWeightKgMax; }
    public void setMaxWeightKgMax(BigDecimal maxWeightKgMax) { this.maxWeightKgMax = maxWeightKgMax; }
    public String getRowLabel() { return rowLabel; }
    public void setRowLabel(String rowLabel) { this.rowLabel = rowLabel; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<RackLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<RackLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
