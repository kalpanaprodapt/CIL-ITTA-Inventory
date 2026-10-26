package com.uniti.cil.itta.inventory.capacity.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Threshold Policy search endpoint. */
public class ThresholdPolicySearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String policyName;
    private String metric;
    private BigDecimal warnPctMin;
    private BigDecimal warnPctMax;
    private BigDecimal critPctMin;
    private BigDecimal critPctMax;
    private Integer evaluationWindowMinutesMin;
    private Integer evaluationWindowMinutesMax;
    private String region;
    private String owner;
    private List<ThresholdPolicyCriticality> criticality;
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
    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }
    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }
    public BigDecimal getWarnPctMin() { return warnPctMin; }
    public void setWarnPctMin(BigDecimal warnPctMin) { this.warnPctMin = warnPctMin; }
    public BigDecimal getWarnPctMax() { return warnPctMax; }
    public void setWarnPctMax(BigDecimal warnPctMax) { this.warnPctMax = warnPctMax; }
    public BigDecimal getCritPctMin() { return critPctMin; }
    public void setCritPctMin(BigDecimal critPctMin) { this.critPctMin = critPctMin; }
    public BigDecimal getCritPctMax() { return critPctMax; }
    public void setCritPctMax(BigDecimal critPctMax) { this.critPctMax = critPctMax; }
    public Integer getEvaluationWindowMinutesMin() { return evaluationWindowMinutesMin; }
    public void setEvaluationWindowMinutesMin(Integer evaluationWindowMinutesMin) { this.evaluationWindowMinutesMin = evaluationWindowMinutesMin; }
    public Integer getEvaluationWindowMinutesMax() { return evaluationWindowMinutesMax; }
    public void setEvaluationWindowMinutesMax(Integer evaluationWindowMinutesMax) { this.evaluationWindowMinutesMax = evaluationWindowMinutesMax; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<ThresholdPolicyCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<ThresholdPolicyCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
