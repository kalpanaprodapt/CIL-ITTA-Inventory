package com.uniti.cil.itta.inventory.geography.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Building search endpoint. */
public class BuildingSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID siteId;
    private Integer floorsMin;
    private Integer floorsMax;
    private Integer yearBuiltMin;
    private Integer yearBuiltMax;
    private List<BuildingOwnership> ownership;
    private LocalDate leaseExpiryFrom;
    private LocalDate leaseExpiryTo;
    private Integer securityLevelMin;
    private Integer securityLevelMax;
    private List<BuildingLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<BuildingCriticality> criticality;

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
    public UUID getSiteId() { return siteId; }
    public void setSiteId(UUID siteId) { this.siteId = siteId; }
    public Integer getFloorsMin() { return floorsMin; }
    public void setFloorsMin(Integer floorsMin) { this.floorsMin = floorsMin; }
    public Integer getFloorsMax() { return floorsMax; }
    public void setFloorsMax(Integer floorsMax) { this.floorsMax = floorsMax; }
    public Integer getYearBuiltMin() { return yearBuiltMin; }
    public void setYearBuiltMin(Integer yearBuiltMin) { this.yearBuiltMin = yearBuiltMin; }
    public Integer getYearBuiltMax() { return yearBuiltMax; }
    public void setYearBuiltMax(Integer yearBuiltMax) { this.yearBuiltMax = yearBuiltMax; }
    public List<BuildingOwnership> getOwnership() { return ownership; }
    public void setOwnership(List<BuildingOwnership> ownership) { this.ownership = ownership; }
    public LocalDate getLeaseExpiryFrom() { return leaseExpiryFrom; }
    public void setLeaseExpiryFrom(LocalDate leaseExpiryFrom) { this.leaseExpiryFrom = leaseExpiryFrom; }
    public LocalDate getLeaseExpiryTo() { return leaseExpiryTo; }
    public void setLeaseExpiryTo(LocalDate leaseExpiryTo) { this.leaseExpiryTo = leaseExpiryTo; }
    public Integer getSecurityLevelMin() { return securityLevelMin; }
    public void setSecurityLevelMin(Integer securityLevelMin) { this.securityLevelMin = securityLevelMin; }
    public Integer getSecurityLevelMax() { return securityLevelMax; }
    public void setSecurityLevelMax(Integer securityLevelMax) { this.securityLevelMax = securityLevelMax; }
    public List<BuildingLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<BuildingLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<BuildingCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<BuildingCriticality> criticality) { this.criticality = criticality; }}
