package com.uniti.cil.itta.inventory.change.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Change Request search endpoint. */
public class ChangeRequestSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String changeNumber;
    private List<ChangeRequestChangeType> changeType;
    private List<ChangeRequestRiskLevel> riskLevel;
    private Instant plannedStartFrom;
    private Instant plannedStartTo;
    private Instant plannedEndFrom;
    private Instant plannedEndTo;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<ChangeRequestLifecycleStage> lifecycleStage;

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
    public String getChangeNumber() { return changeNumber; }
    public void setChangeNumber(String changeNumber) { this.changeNumber = changeNumber; }
    public List<ChangeRequestChangeType> getChangeType() { return changeType; }
    public void setChangeType(List<ChangeRequestChangeType> changeType) { this.changeType = changeType; }
    public List<ChangeRequestRiskLevel> getRiskLevel() { return riskLevel; }
    public void setRiskLevel(List<ChangeRequestRiskLevel> riskLevel) { this.riskLevel = riskLevel; }
    public Instant getPlannedStartFrom() { return plannedStartFrom; }
    public void setPlannedStartFrom(Instant plannedStartFrom) { this.plannedStartFrom = plannedStartFrom; }
    public Instant getPlannedStartTo() { return plannedStartTo; }
    public void setPlannedStartTo(Instant plannedStartTo) { this.plannedStartTo = plannedStartTo; }
    public Instant getPlannedEndFrom() { return plannedEndFrom; }
    public void setPlannedEndFrom(Instant plannedEndFrom) { this.plannedEndFrom = plannedEndFrom; }
    public Instant getPlannedEndTo() { return plannedEndTo; }
    public void setPlannedEndTo(Instant plannedEndTo) { this.plannedEndTo = plannedEndTo; }
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
    public List<ChangeRequestLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<ChangeRequestLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
