package com.uniti.cil.itta.inventory.discovery.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Reconciliation Rule search endpoint. */
public class ReconciliationRuleSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String ruleName;
    private String matchKey;
    private List<ReconciliationRuleAction> action;
    private Integer priorityMin;
    private Integer priorityMax;
    private String labels;
    private List<ReconciliationRuleLifecycleStage> lifecycleStage;
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
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    public String getMatchKey() { return matchKey; }
    public void setMatchKey(String matchKey) { this.matchKey = matchKey; }
    public List<ReconciliationRuleAction> getAction() { return action; }
    public void setAction(List<ReconciliationRuleAction> action) { this.action = action; }
    public Integer getPriorityMin() { return priorityMin; }
    public void setPriorityMin(Integer priorityMin) { this.priorityMin = priorityMin; }
    public Integer getPriorityMax() { return priorityMax; }
    public void setPriorityMax(Integer priorityMax) { this.priorityMax = priorityMax; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<ReconciliationRuleLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<ReconciliationRuleLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
