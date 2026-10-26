package com.uniti.cil.itta.inventory.switching.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Vlan search endpoint. */
public class VlanSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private Integer vlanIdMin;
    private Integer vlanIdMax;
    private String vlanName;
    private List<VlanVlanType> vlanType;
    private UUID poolId;
    private String stpMode;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<VlanLifecycleStage> lifecycleStage;

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
    public Integer getVlanIdMin() { return vlanIdMin; }
    public void setVlanIdMin(Integer vlanIdMin) { this.vlanIdMin = vlanIdMin; }
    public Integer getVlanIdMax() { return vlanIdMax; }
    public void setVlanIdMax(Integer vlanIdMax) { this.vlanIdMax = vlanIdMax; }
    public String getVlanName() { return vlanName; }
    public void setVlanName(String vlanName) { this.vlanName = vlanName; }
    public List<VlanVlanType> getVlanType() { return vlanType; }
    public void setVlanType(List<VlanVlanType> vlanType) { this.vlanType = vlanType; }
    public UUID getPoolId() { return poolId; }
    public void setPoolId(UUID poolId) { this.poolId = poolId; }
    public String getStpMode() { return stpMode; }
    public void setStpMode(String stpMode) { this.stpMode = stpMode; }
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
    public List<VlanLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<VlanLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
