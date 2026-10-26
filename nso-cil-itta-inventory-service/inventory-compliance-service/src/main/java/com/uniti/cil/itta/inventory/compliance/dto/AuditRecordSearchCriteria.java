package com.uniti.cil.itta.inventory.compliance.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Audit Record search endpoint. */
public class AuditRecordSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String actor;
    private String action;
    private String entityRef;
    private Instant occurredAtFrom;
    private Instant occurredAtTo;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<AuditRecordLifecycleStage> lifecycleStage;

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
    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getEntityRef() { return entityRef; }
    public void setEntityRef(String entityRef) { this.entityRef = entityRef; }
    public Instant getOccurredAtFrom() { return occurredAtFrom; }
    public void setOccurredAtFrom(Instant occurredAtFrom) { this.occurredAtFrom = occurredAtFrom; }
    public Instant getOccurredAtTo() { return occurredAtTo; }
    public void setOccurredAtTo(Instant occurredAtTo) { this.occurredAtTo = occurredAtTo; }
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
    public List<AuditRecordLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<AuditRecordLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }}
