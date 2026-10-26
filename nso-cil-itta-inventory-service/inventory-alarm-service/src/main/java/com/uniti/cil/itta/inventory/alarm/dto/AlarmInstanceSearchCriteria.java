package com.uniti.cil.itta.inventory.alarm.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Alarm Instance search endpoint. */
public class AlarmInstanceSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID definitionId;
    private String sourceRef;
    private Instant raisedAtFrom;
    private Instant raisedAtTo;
    private Instant clearedAtFrom;
    private Instant clearedAtTo;
    private String ackedBy;
    private List<AlarmInstanceAlarmState> alarmState;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<AlarmInstanceLifecycleStage> lifecycleStage;
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
    public UUID getDefinitionId() { return definitionId; }
    public void setDefinitionId(UUID definitionId) { this.definitionId = definitionId; }
    public String getSourceRef() { return sourceRef; }
    public void setSourceRef(String sourceRef) { this.sourceRef = sourceRef; }
    public Instant getRaisedAtFrom() { return raisedAtFrom; }
    public void setRaisedAtFrom(Instant raisedAtFrom) { this.raisedAtFrom = raisedAtFrom; }
    public Instant getRaisedAtTo() { return raisedAtTo; }
    public void setRaisedAtTo(Instant raisedAtTo) { this.raisedAtTo = raisedAtTo; }
    public Instant getClearedAtFrom() { return clearedAtFrom; }
    public void setClearedAtFrom(Instant clearedAtFrom) { this.clearedAtFrom = clearedAtFrom; }
    public Instant getClearedAtTo() { return clearedAtTo; }
    public void setClearedAtTo(Instant clearedAtTo) { this.clearedAtTo = clearedAtTo; }
    public String getAckedBy() { return ackedBy; }
    public void setAckedBy(String ackedBy) { this.ackedBy = ackedBy; }
    public List<AlarmInstanceAlarmState> getAlarmState() { return alarmState; }
    public void setAlarmState(List<AlarmInstanceAlarmState> alarmState) { this.alarmState = alarmState; }
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
    public List<AlarmInstanceLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<AlarmInstanceLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }}
