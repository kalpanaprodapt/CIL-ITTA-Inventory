package com.uniti.cil.itta.inventory.port.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Sub Interface search endpoint. */
public class SubInterfaceSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID interfaceId;
    private Integer vlanTagMin;
    private Integer vlanTagMax;
    private String encapsulation;
    private String ipv4Address;
    private String ipv6Address;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;
    private String labels;

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
    public UUID getInterfaceId() { return interfaceId; }
    public void setInterfaceId(UUID interfaceId) { this.interfaceId = interfaceId; }
    public Integer getVlanTagMin() { return vlanTagMin; }
    public void setVlanTagMin(Integer vlanTagMin) { this.vlanTagMin = vlanTagMin; }
    public Integer getVlanTagMax() { return vlanTagMax; }
    public void setVlanTagMax(Integer vlanTagMax) { this.vlanTagMax = vlanTagMax; }
    public String getEncapsulation() { return encapsulation; }
    public void setEncapsulation(String encapsulation) { this.encapsulation = encapsulation; }
    public String getIpv4Address() { return ipv4Address; }
    public void setIpv4Address(String ipv4Address) { this.ipv4Address = ipv4Address; }
    public String getIpv6Address() { return ipv6Address; }
    public void setIpv6Address(String ipv6Address) { this.ipv6Address = ipv6Address; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }}
