package com.uniti.cil.itta.inventory.switching.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Bgp Session; null properties are left unchanged. */
public class BgpSessionUpdateRequest {

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
    private UUID localDeviceId;
    @Size(max = 255)
    private String peerAddress;
    private Long localAsn;
    private Long remoteAsn;
    private BgpSessionSessionState sessionState;
    private Integer prefixLimit;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private BgpSessionCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
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
