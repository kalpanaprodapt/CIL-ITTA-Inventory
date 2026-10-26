package com.uniti.cil.itta.inventory.topology.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Link; null properties are left unchanged. */
public class LinkUpdateRequest {

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
    private UUID aInterfaceId;
    private UUID zInterfaceId;
    private LinkLinkType linkType;
    private Long speedMbps;
    @Size(max = 255)
    private String discoveredBy;
    @Size(max = 255)
    private String region;
    @Size(max = 255)
    private String owner;
    private LinkCriticality criticality;
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
    public UUID getAInterfaceId() { return aInterfaceId; }
    public void setAInterfaceId(UUID aInterfaceId) { this.aInterfaceId = aInterfaceId; }
    public UUID getZInterfaceId() { return zInterfaceId; }
    public void setZInterfaceId(UUID zInterfaceId) { this.zInterfaceId = zInterfaceId; }
    public LinkLinkType getLinkType() { return linkType; }
    public void setLinkType(LinkLinkType linkType) { this.linkType = linkType; }
    public Long getSpeedMbps() { return speedMbps; }
    public void setSpeedMbps(Long speedMbps) { this.speedMbps = speedMbps; }
    public String getDiscoveredBy() { return discoveredBy; }
    public void setDiscoveredBy(String discoveredBy) { this.discoveredBy = discoveredBy; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public LinkCriticality getCriticality() { return criticality; }
    public void setCriticality(LinkCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }}
