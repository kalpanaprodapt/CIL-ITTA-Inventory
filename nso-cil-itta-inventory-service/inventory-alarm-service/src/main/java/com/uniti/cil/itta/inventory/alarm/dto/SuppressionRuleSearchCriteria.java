package com.uniti.cil.itta.inventory.alarm.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Suppression Rule search endpoint. */
public class SuppressionRuleSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String ruleName;
    private Instant startsAtFrom;
    private Instant startsAtTo;
    private Instant endsAtFrom;
    private Instant endsAtTo;
    private String reason;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<SuppressionRuleCriticality> criticality;
    private String costCenter;

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
    public Instant getStartsAtFrom() { return startsAtFrom; }
    public void setStartsAtFrom(Instant startsAtFrom) { this.startsAtFrom = startsAtFrom; }
    public Instant getStartsAtTo() { return startsAtTo; }
    public void setStartsAtTo(Instant startsAtTo) { this.startsAtTo = startsAtTo; }
    public Instant getEndsAtFrom() { return endsAtFrom; }
    public void setEndsAtFrom(Instant endsAtFrom) { this.endsAtFrom = endsAtFrom; }
    public Instant getEndsAtTo() { return endsAtTo; }
    public void setEndsAtTo(Instant endsAtTo) { this.endsAtTo = endsAtTo; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<SuppressionRuleCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<SuppressionRuleCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
