package com.uniti.cil.itta.inventory.ipam.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Ip Subnet; null properties are left unchanged. */
public class IpSubnetUpdateRequest {

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
    private String cidr;
    private IpSubnetIpVersion ipVersion;
    private UUID vrfId;
    @Size(max = 255)
    private String gateway;
    private UUID vlanId;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal utilizationPct;
    @Size(max = 255)
    private String owner;
    private IpSubnetCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
    private LocalDate effectiveFrom;
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
    public String getCidr() { return cidr; }
    public void setCidr(String cidr) { this.cidr = cidr; }
    public IpSubnetIpVersion getIpVersion() { return ipVersion; }
    public void setIpVersion(IpSubnetIpVersion ipVersion) { this.ipVersion = ipVersion; }
    public UUID getVrfId() { return vrfId; }
    public void setVrfId(UUID vrfId) { this.vrfId = vrfId; }
    public String getGateway() { return gateway; }
    public void setGateway(String gateway) { this.gateway = gateway; }
    public UUID getVlanId() { return vlanId; }
    public void setVlanId(UUID vlanId) { this.vlanId = vlanId; }
    public BigDecimal getUtilizationPct() { return utilizationPct; }
    public void setUtilizationPct(BigDecimal utilizationPct) { this.utilizationPct = utilizationPct; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public IpSubnetCriticality getCriticality() { return criticality; }
    public void setCriticality(IpSubnetCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }}
