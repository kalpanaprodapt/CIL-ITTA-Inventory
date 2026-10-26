package com.uniti.cil.itta.inventory.mpls.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Read model returned by the Qos Policy API. */
public class QosPolicyResponse {

    private UUID id;
    private String tenantId;
    private Long version;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    private String code;
    private String name;
    private String description;
    private LifecycleStatus status;
    private String externalRef;
    private String policyName;
    private Integer classCount;
    private Long cirMbps;
    private Long pirMbps;
    private String markingScheme;
    private String labels;
    private QosPolicyLifecycleStage lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getVersion() { return version; }
    public void setVersion(Long version) { this.version = version; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

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
    public Integer getClassCount() { return classCount; }
    public void setClassCount(Integer classCount) { this.classCount = classCount; }
    public Long getCirMbps() { return cirMbps; }
    public void setCirMbps(Long cirMbps) { this.cirMbps = cirMbps; }
    public Long getPirMbps() { return pirMbps; }
    public void setPirMbps(Long pirMbps) { this.pirMbps = pirMbps; }
    public String getMarkingScheme() { return markingScheme; }
    public void setMarkingScheme(String markingScheme) { this.markingScheme = markingScheme; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public QosPolicyLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(QosPolicyLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
