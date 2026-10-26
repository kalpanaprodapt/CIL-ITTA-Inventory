package com.uniti.cil.itta.inventory.virtual.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Vnf search endpoint. */
public class VnfSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String vnfName;
    private String vnfType;
    private String flavor;
    private Integer vcpuMin;
    private Integer vcpuMax;
    private Integer memoryGbMin;
    private Integer memoryGbMax;
    private Integer instanceCountMin;
    private Integer instanceCountMax;
    private List<VnfLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<VnfCriticality> criticality;

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
    public String getVnfName() { return vnfName; }
    public void setVnfName(String vnfName) { this.vnfName = vnfName; }
    public String getVnfType() { return vnfType; }
    public void setVnfType(String vnfType) { this.vnfType = vnfType; }
    public String getFlavor() { return flavor; }
    public void setFlavor(String flavor) { this.flavor = flavor; }
    public Integer getVcpuMin() { return vcpuMin; }
    public void setVcpuMin(Integer vcpuMin) { this.vcpuMin = vcpuMin; }
    public Integer getVcpuMax() { return vcpuMax; }
    public void setVcpuMax(Integer vcpuMax) { this.vcpuMax = vcpuMax; }
    public Integer getMemoryGbMin() { return memoryGbMin; }
    public void setMemoryGbMin(Integer memoryGbMin) { this.memoryGbMin = memoryGbMin; }
    public Integer getMemoryGbMax() { return memoryGbMax; }
    public void setMemoryGbMax(Integer memoryGbMax) { this.memoryGbMax = memoryGbMax; }
    public Integer getInstanceCountMin() { return instanceCountMin; }
    public void setInstanceCountMin(Integer instanceCountMin) { this.instanceCountMin = instanceCountMin; }
    public Integer getInstanceCountMax() { return instanceCountMax; }
    public void setInstanceCountMax(Integer instanceCountMax) { this.instanceCountMax = instanceCountMax; }
    public List<VnfLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<VnfLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<VnfCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<VnfCriticality> criticality) { this.criticality = criticality; }}
