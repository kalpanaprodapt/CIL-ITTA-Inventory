package com.uniti.cil.itta.inventory.switching.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Read model returned by the Bgp Session API. */
public class BgpSessionResponse {

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
    private UUID localDeviceId;
    private String peerAddress;
    private Long localAsn;
    private Long remoteAsn;
    private BgpSessionSessionState sessionState;
    private Integer prefixLimit;
    private String region;
    private String owner;
    private BgpSessionCriticality criticality;
    private String costCenter;
    private String notes;

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
    public UUID getLocalDeviceId() { return localDeviceId; }
    public void setLocalDeviceId(UUID localDeviceId) { this.localDeviceId = localDeviceId; }
    public String getPeerAddress() { return peerAddress; }
    public void setPeerAddress(String peerAddress) { this.peerAddress = peerAddress; }
    public Long getLocalAsn() { return localAsn; }
    public void setLocalAsn(Long localAsn) { this.localAsn = localAsn; }
    public Long getRemoteAsn() { return remoteAsn; }
    public void setRemoteAsn(Long remoteAsn) { this.remoteAsn = remoteAsn; }
    public BgpSessionSessionState getSessionState() { return sessionState; }
    public void setSessionState(BgpSessionSessionState sessionState) { this.sessionState = sessionState; }
    public Integer getPrefixLimit() { return prefixLimit; }
    public void setPrefixLimit(Integer prefixLimit) { this.prefixLimit = prefixLimit; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public BgpSessionCriticality getCriticality() { return criticality; }
    public void setCriticality(BgpSessionCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }}
