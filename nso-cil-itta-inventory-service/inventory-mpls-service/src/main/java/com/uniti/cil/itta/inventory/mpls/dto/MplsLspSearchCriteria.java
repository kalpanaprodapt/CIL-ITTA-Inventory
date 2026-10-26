package com.uniti.cil.itta.inventory.mpls.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Mpls Lsp search endpoint. */
public class MplsLspSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String lspName;
    private UUID headEndId;
    private UUID tailEndId;
    private Long bandwidthMbpsMin;
    private Long bandwidthMbpsMax;
    private Integer setupPriorityMin;
    private Integer setupPriorityMax;
    private List<MplsLspPathType> pathType;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<MplsLspCriticality> criticality;
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
    public String getLspName() { return lspName; }
    public void setLspName(String lspName) { this.lspName = lspName; }
    public UUID getHeadEndId() { return headEndId; }
    public void setHeadEndId(UUID headEndId) { this.headEndId = headEndId; }
    public UUID getTailEndId() { return tailEndId; }
    public void setTailEndId(UUID tailEndId) { this.tailEndId = tailEndId; }
    public Long getBandwidthMbpsMin() { return bandwidthMbpsMin; }
    public void setBandwidthMbpsMin(Long bandwidthMbpsMin) { this.bandwidthMbpsMin = bandwidthMbpsMin; }
    public Long getBandwidthMbpsMax() { return bandwidthMbpsMax; }
    public void setBandwidthMbpsMax(Long bandwidthMbpsMax) { this.bandwidthMbpsMax = bandwidthMbpsMax; }
    public Integer getSetupPriorityMin() { return setupPriorityMin; }
    public void setSetupPriorityMin(Integer setupPriorityMin) { this.setupPriorityMin = setupPriorityMin; }
    public Integer getSetupPriorityMax() { return setupPriorityMax; }
    public void setSetupPriorityMax(Integer setupPriorityMax) { this.setupPriorityMax = setupPriorityMax; }
    public List<MplsLspPathType> getPathType() { return pathType; }
    public void setPathType(List<MplsLspPathType> pathType) { this.pathType = pathType; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<MplsLspCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<MplsLspCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
