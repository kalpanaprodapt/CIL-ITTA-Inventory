package com.uniti.cil.itta.inventory.device.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Fan Tray search endpoint. */
public class FanTraySearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID chassisId;
    private Integer slotNumberMin;
    private Integer slotNumberMax;
    private Integer fanCountMin;
    private Integer fanCountMax;
    private List<FanTrayAirflow> airflow;
    private String serialNumber;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<FanTrayLifecycleStage> lifecycleStage;

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
    public Integer getFanCountMin() { return fanCountMin; }
    public void setFanCountMin(Integer fanCountMin) { this.fanCountMin = fanCountMin; }
    public Integer getFanCountMax() { return fanCountMax; }
    public void setFanCountMax(Integer fanCountMax) { this.fanCountMax = fanCountMax; }
    public List<FanTrayAirflow> getAirflow() { return airflow; }
    public void setAirflow(List<FanTrayAirflow> airflow) { this.airflow = airflow; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
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
    public List<FanTrayLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<FanTrayLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
