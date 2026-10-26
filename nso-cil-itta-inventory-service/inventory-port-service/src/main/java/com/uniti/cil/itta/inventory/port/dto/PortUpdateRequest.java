package com.uniti.cil.itta.inventory.port.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Port; null properties are left unchanged. */
public class PortUpdateRequest {

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
    private UUID deviceId;
    private UUID cardId;
    @Size(max = 255)
    private String portNumber;
    private Long speedMbps;
    private PortMediaType mediaType;
    private PortAdminState adminState;
    @Size(max = 255)
    private String sourceSystem;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private PortCriticality criticality;
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
    public UUID getDeviceId() { return deviceId; }
    public void setDeviceId(UUID deviceId) { this.deviceId = deviceId; }
    public UUID getCardId() { return cardId; }
    public void setCardId(UUID cardId) { this.cardId = cardId; }
    public String getPortNumber() { return portNumber; }
    public void setPortNumber(String portNumber) { this.portNumber = portNumber; }
    public Long getSpeedMbps() { return speedMbps; }
    public void setSpeedMbps(Long speedMbps) { this.speedMbps = speedMbps; }
    public PortMediaType getMediaType() { return mediaType; }
    public void setMediaType(PortMediaType mediaType) { this.mediaType = mediaType; }
    public PortAdminState getAdminState() { return adminState; }
    public void setAdminState(PortAdminState adminState) { this.adminState = adminState; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public PortCriticality getCriticality() { return criticality; }
    public void setCriticality(PortCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
