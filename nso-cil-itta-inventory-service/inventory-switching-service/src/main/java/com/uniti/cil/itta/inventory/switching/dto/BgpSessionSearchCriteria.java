package com.uniti.cil.itta.inventory.switching.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Bgp Session search endpoint. */
public class BgpSessionSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID localDeviceId;
    private String peerAddress;
    private Long localAsnMin;
    private Long localAsnMax;
    private Long remoteAsnMin;
    private Long remoteAsnMax;
    private List<BgpSessionSessionState> sessionState;
    private Integer prefixLimitMin;
    private Integer prefixLimitMax;
    private String region;
    private String owner;
    private List<BgpSessionCriticality> criticality;
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
    public UUID getLocalDeviceId() { return localDeviceId; }
    public void setLocalDeviceId(UUID localDeviceId) { this.localDeviceId = localDeviceId; }
    public String getPeerAddress() { return peerAddress; }
    public void setPeerAddress(String peerAddress) { this.peerAddress = peerAddress; }
    public Long getLocalAsnMin() { return localAsnMin; }
    public void setLocalAsnMin(Long localAsnMin) { this.localAsnMin = localAsnMin; }
    public Long getLocalAsnMax() { return localAsnMax; }
    public void setLocalAsnMax(Long localAsnMax) { this.localAsnMax = localAsnMax; }
    public Long getRemoteAsnMin() { return remoteAsnMin; }
    public void setRemoteAsnMin(Long remoteAsnMin) { this.remoteAsnMin = remoteAsnMin; }
    public Long getRemoteAsnMax() { return remoteAsnMax; }
    public void setRemoteAsnMax(Long remoteAsnMax) { this.remoteAsnMax = remoteAsnMax; }
    public List<BgpSessionSessionState> getSessionState() { return sessionState; }
    public void setSessionState(List<BgpSessionSessionState> sessionState) { this.sessionState = sessionState; }
    public Integer getPrefixLimitMin() { return prefixLimitMin; }
    public void setPrefixLimitMin(Integer prefixLimitMin) { this.prefixLimitMin = prefixLimitMin; }
    public Integer getPrefixLimitMax() { return prefixLimitMax; }
    public void setPrefixLimitMax(Integer prefixLimitMax) { this.prefixLimitMax = prefixLimitMax; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<BgpSessionCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<BgpSessionCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
