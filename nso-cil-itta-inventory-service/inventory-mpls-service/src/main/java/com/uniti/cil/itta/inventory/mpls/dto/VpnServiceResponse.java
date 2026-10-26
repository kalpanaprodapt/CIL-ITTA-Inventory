package com.uniti.cil.itta.inventory.mpls.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Read model returned by the Vpn Service API. */
public class VpnServiceResponse {

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
    private String vpnName;
    private VpnServiceVpnType vpnType;
    private UUID vrfId;
    private UUID customerId;
    private VpnServiceTopology topology;
    private String owner;
    private VpnServiceCriticality criticality;
    private String costCenter;
    private String notes;
    private LocalDate effectiveFrom;

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
    public String getVpnName() { return vpnName; }
    public void setVpnName(String vpnName) { this.vpnName = vpnName; }
    public VpnServiceVpnType getVpnType() { return vpnType; }
    public void setVpnType(VpnServiceVpnType vpnType) { this.vpnType = vpnType; }
    public UUID getVrfId() { return vrfId; }
    public void setVrfId(UUID vrfId) { this.vrfId = vrfId; }
    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }
    public VpnServiceTopology getTopology() { return topology; }
    public void setTopology(VpnServiceTopology topology) { this.topology = topology; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public VpnServiceCriticality getCriticality() { return criticality; }
    public void setCriticality(VpnServiceCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }}
