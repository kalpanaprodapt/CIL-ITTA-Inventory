package com.uniti.cil.itta.inventory.ipam.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Ip Subnet search endpoint. */
public class IpSubnetSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String cidr;
    private List<IpSubnetIpVersion> ipVersion;
    private UUID vrfId;
    private String gateway;
    private UUID vlanId;
    private BigDecimal utilizationPctMin;
    private BigDecimal utilizationPctMax;
    private String owner;
    private List<IpSubnetCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;

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
    public String getCidr() { return cidr; }
    public void setCidr(String cidr) { this.cidr = cidr; }
    public List<IpSubnetIpVersion> getIpVersion() { return ipVersion; }
    public void setIpVersion(List<IpSubnetIpVersion> ipVersion) { this.ipVersion = ipVersion; }
    public UUID getVrfId() { return vrfId; }
    public void setVrfId(UUID vrfId) { this.vrfId = vrfId; }
    public String getGateway() { return gateway; }
    public void setGateway(String gateway) { this.gateway = gateway; }
    public UUID getVlanId() { return vlanId; }
    public void setVlanId(UUID vlanId) { this.vlanId = vlanId; }
    public BigDecimal getUtilizationPctMin() { return utilizationPctMin; }
    public void setUtilizationPctMin(BigDecimal utilizationPctMin) { this.utilizationPctMin = utilizationPctMin; }
    public BigDecimal getUtilizationPctMax() { return utilizationPctMax; }
    public void setUtilizationPctMax(BigDecimal utilizationPctMax) { this.utilizationPctMax = utilizationPctMax; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<IpSubnetCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<IpSubnetCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
