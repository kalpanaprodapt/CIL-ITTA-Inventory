package com.uniti.cil.itta.inventory.mpls.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Qos Policy search endpoint. */
public class QosPolicySearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String policyName;
    private Integer classCountMin;
    private Integer classCountMax;
    private Long cirMbpsMin;
    private Long cirMbpsMax;
    private Long pirMbpsMin;
    private Long pirMbpsMax;
    private String markingScheme;
    private String labels;
    private List<QosPolicyLifecycleStage> lifecycleStage;
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
    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }
    public Integer getClassCountMin() { return classCountMin; }
    public void setClassCountMin(Integer classCountMin) { this.classCountMin = classCountMin; }
    public Integer getClassCountMax() { return classCountMax; }
    public void setClassCountMax(Integer classCountMax) { this.classCountMax = classCountMax; }
    public Long getCirMbpsMin() { return cirMbpsMin; }
    public void setCirMbpsMin(Long cirMbpsMin) { this.cirMbpsMin = cirMbpsMin; }
    public Long getCirMbpsMax() { return cirMbpsMax; }
    public void setCirMbpsMax(Long cirMbpsMax) { this.cirMbpsMax = cirMbpsMax; }
    public Long getPirMbpsMin() { return pirMbpsMin; }
    public void setPirMbpsMin(Long pirMbpsMin) { this.pirMbpsMin = pirMbpsMin; }
    public Long getPirMbpsMax() { return pirMbpsMax; }
    public void setPirMbpsMax(Long pirMbpsMax) { this.pirMbpsMax = pirMbpsMax; }
    public String getMarkingScheme() { return markingScheme; }
    public void setMarkingScheme(String markingScheme) { this.markingScheme = markingScheme; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<QosPolicyLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<QosPolicyLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
