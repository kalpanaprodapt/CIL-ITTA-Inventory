package com.uniti.cil.itta.inventory.customer.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Sla Profile search endpoint. */
public class SlaProfileSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String profileName;
    private BigDecimal availabilityPctMin;
    private BigDecimal availabilityPctMax;
    private Integer mttrMinutesMin;
    private Integer mttrMinutesMax;
    private BigDecimal latencyMsMin;
    private BigDecimal latencyMsMax;
    private BigDecimal jitterMsMin;
    private BigDecimal jitterMsMax;
    private String owner;
    private List<SlaProfileCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;

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
    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }
    public BigDecimal getAvailabilityPctMin() { return availabilityPctMin; }
    public void setAvailabilityPctMin(BigDecimal availabilityPctMin) { this.availabilityPctMin = availabilityPctMin; }
    public BigDecimal getAvailabilityPctMax() { return availabilityPctMax; }
    public void setAvailabilityPctMax(BigDecimal availabilityPctMax) { this.availabilityPctMax = availabilityPctMax; }
    public Integer getMttrMinutesMin() { return mttrMinutesMin; }
    public void setMttrMinutesMin(Integer mttrMinutesMin) { this.mttrMinutesMin = mttrMinutesMin; }
    public Integer getMttrMinutesMax() { return mttrMinutesMax; }
    public void setMttrMinutesMax(Integer mttrMinutesMax) { this.mttrMinutesMax = mttrMinutesMax; }
    public BigDecimal getLatencyMsMin() { return latencyMsMin; }
    public void setLatencyMsMin(BigDecimal latencyMsMin) { this.latencyMsMin = latencyMsMin; }
    public BigDecimal getLatencyMsMax() { return latencyMsMax; }
    public void setLatencyMsMax(BigDecimal latencyMsMax) { this.latencyMsMax = latencyMsMax; }
    public BigDecimal getJitterMsMin() { return jitterMsMin; }
    public void setJitterMsMin(BigDecimal jitterMsMin) { this.jitterMsMin = jitterMsMin; }
    public BigDecimal getJitterMsMax() { return jitterMsMax; }
    public void setJitterMsMax(BigDecimal jitterMsMax) { this.jitterMsMax = jitterMsMax; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<SlaProfileCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<SlaProfileCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
