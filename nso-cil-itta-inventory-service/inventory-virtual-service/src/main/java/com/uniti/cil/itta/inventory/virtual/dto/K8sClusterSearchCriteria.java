package com.uniti.cil.itta.inventory.virtual.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the K8s Cluster search endpoint. */
public class K8sClusterSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String clusterName;
    private String clusterVersion;
    private Integer nodeCountMin;
    private Integer nodeCountMax;
    private UUID cloudAccountId;
    private String region;
    private String apiEndpoint;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;
    private List<K8sClusterLifecycleStage> lifecycleStage;
    private String sourceSystem;
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
    public String getClusterName() { return clusterName; }
    public void setClusterName(String clusterName) { this.clusterName = clusterName; }
    public String getClusterVersion() { return clusterVersion; }
    public void setClusterVersion(String clusterVersion) { this.clusterVersion = clusterVersion; }
    public Integer getNodeCountMin() { return nodeCountMin; }
    public void setNodeCountMin(Integer nodeCountMin) { this.nodeCountMin = nodeCountMin; }
    public Integer getNodeCountMax() { return nodeCountMax; }
    public void setNodeCountMax(Integer nodeCountMax) { this.nodeCountMax = nodeCountMax; }
    public UUID getCloudAccountId() { return cloudAccountId; }
    public void setCloudAccountId(UUID cloudAccountId) { this.cloudAccountId = cloudAccountId; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getApiEndpoint() { return apiEndpoint; }
    public void setApiEndpoint(String apiEndpoint) { this.apiEndpoint = apiEndpoint; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<K8sClusterLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<K8sClusterLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
