package com.uniti.cil.itta.inventory.mpls.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Mpls Lsp; null properties are left unchanged. */
public class MplsLspUpdateRequest {

    private Long expectedVersion;

    @Size(max = 255)
    private String code;
    @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    @Size(max = 255)
    private String lspName;
    private UUID headEndId;
    private UUID tailEndId;
    private Long bandwidthMbps;
    private Integer setupPriority;
    private MplsLspPathType pathType;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private MplsLspCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    public Long getExpectedVersion() { return expectedVersion; }
    public void setExpectedVersion(Long expectedVersion) { this.expectedVersion = expectedVersion; }

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
    public String getLspName() { return lspName; }
    public void setLspName(String lspName) { this.lspName = lspName; }
    public UUID getHeadEndId() { return headEndId; }
    public void setHeadEndId(UUID headEndId) { this.headEndId = headEndId; }
    public UUID getTailEndId() { return tailEndId; }
    public void setTailEndId(UUID tailEndId) { this.tailEndId = tailEndId; }
    public Long getBandwidthMbps() { return bandwidthMbps; }
    public void setBandwidthMbps(Long bandwidthMbps) { this.bandwidthMbps = bandwidthMbps; }
    public Integer getSetupPriority() { return setupPriority; }
    public void setSetupPriority(Integer setupPriority) { this.setupPriority = setupPriority; }
    public MplsLspPathType getPathType() { return pathType; }
    public void setPathType(MplsLspPathType pathType) { this.pathType = pathType; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public MplsLspCriticality getCriticality() { return criticality; }
    public void setCriticality(MplsLspCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
