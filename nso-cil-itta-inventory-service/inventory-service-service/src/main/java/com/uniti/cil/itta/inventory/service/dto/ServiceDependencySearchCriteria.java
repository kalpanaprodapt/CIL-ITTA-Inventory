package com.uniti.cil.itta.inventory.service.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Service Dependency search endpoint. */
public class ServiceDependencySearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID serviceId;
    private UUID dependsOnId;
    private List<ServiceDependencyDependencyType> dependencyType;
    private BigDecimal impactScoreMin;
    private BigDecimal impactScoreMax;
    private List<ServiceDependencyCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;

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
    public UUID getServiceId() { return serviceId; }
    public void setServiceId(UUID serviceId) { this.serviceId = serviceId; }
    public UUID getDependsOnId() { return dependsOnId; }
    public void setDependsOnId(UUID dependsOnId) { this.dependsOnId = dependsOnId; }
    public List<ServiceDependencyDependencyType> getDependencyType() { return dependencyType; }
    public void setDependencyType(List<ServiceDependencyDependencyType> dependencyType) { this.dependencyType = dependencyType; }
    public BigDecimal getImpactScoreMin() { return impactScoreMin; }
    public void setImpactScoreMin(BigDecimal impactScoreMin) { this.impactScoreMin = impactScoreMin; }
    public BigDecimal getImpactScoreMax() { return impactScoreMax; }
    public void setImpactScoreMax(BigDecimal impactScoreMax) { this.impactScoreMax = impactScoreMax; }
    public List<ServiceDependencyCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<ServiceDependencyCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }}
