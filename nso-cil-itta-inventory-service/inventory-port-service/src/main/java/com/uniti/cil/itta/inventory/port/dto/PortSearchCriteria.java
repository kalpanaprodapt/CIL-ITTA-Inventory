package com.uniti.cil.itta.inventory.port.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Port search endpoint. */
public class PortSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID deviceId;
    private UUID cardId;
    private String portNumber;
    private Long speedMbpsMin;
    private Long speedMbpsMax;
    private List<PortMediaType> mediaType;
    private List<PortAdminState> adminState;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<PortCriticality> criticality;
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
    public UUID getDeviceId() { return deviceId; }
    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }
    public UUID getCardId() { return cardId; }
    public void setCardId(UUID cardId) { this.cardId = cardId; }
    public String getPortNumber() { return portNumber; }
    public void setPortNumber(String portNumber) { this.portNumber = portNumber; }
    public Long getSpeedMbpsMin() { return speedMbpsMin; }
    public void setSpeedMbpsMin(Long speedMbpsMin) { this.speedMbpsMin = speedMbpsMin; }
    public Long getSpeedMbpsMax() { return speedMbpsMax; }
    public void setSpeedMbpsMax(Long speedMbpsMax) { this.speedMbpsMax = speedMbpsMax; }
    public List<PortMediaType> getMediaType() { return mediaType; }
    public void setMediaType(List<PortMediaType> mediaType) { this.mediaType = mediaType; }
    public List<PortAdminState> getAdminState() { return adminState; }
    public void setAdminState(List<PortAdminState> adminState) { this.adminState = adminState; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<PortCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<PortCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
