package com.uniti.cil.itta.inventory.capacity.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Threshold Policy. */
public class ThresholdPolicyCreateRequest {

    @NotBlank @Size(max = 255)
    private String code;
    @NotBlank @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    @NotNull
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    @NotBlank @Size(max = 255)
    private String policyName;
    @NotBlank @Size(max = 255)
    private String metric;
    @NotNull @Digits(integer = 14, fraction = 4)
    private BigDecimal warnPct;
    @NotNull @Digits(integer = 14, fraction = 4)
    private BigDecimal critPct;
    private Integer evaluationWindowMinutes;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private ThresholdPolicyCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LifecycleStatus getStatus() { return status; }
    public void setStatus(LifecycleStatus status) { this.status = status; }
    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }
    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }
    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }
    public BigDecimal getWarnPct() { return warnPct; }
    public void setWarnPct(BigDecimal warnPct) { this.warnPct = warnPct; }
    public BigDecimal getCritPct() { return critPct; }
    public void setCritPct(BigDecimal critPct) { this.critPct = critPct; }
    public Integer getEvaluationWindowMinutes() { return evaluationWindowMinutes; }
    public void setEvaluationWindowMinutes(Integer evaluationWindowMinutes) { this.evaluationWindowMinutes = evaluationWindowMinutes; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public ThresholdPolicyCriticality getCriticality() { return criticality; }
    public void setCriticality(ThresholdPolicyCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }}
