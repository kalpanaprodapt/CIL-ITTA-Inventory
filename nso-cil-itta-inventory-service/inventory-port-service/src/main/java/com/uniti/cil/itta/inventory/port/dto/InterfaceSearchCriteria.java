package com.uniti.cil.itta.inventory.port.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Interface search endpoint. */
public class InterfaceSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID portId;
    private String ifName;
    private Integer ifIndexMin;
    private Integer ifIndexMax;
    private String macAddress;
    private Integer mtuMin;
    private Integer mtuMax;
    private List<InterfaceOperState> operState;
    private String owner;
    private List<InterfaceCriticality> criticality;
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
    public UUID getPortId() { return portId; }
    public void setPortId(UUID portId) { this.portId = portId; }
    public String getIfName() { return ifName; }
    public void setIfName(String ifName) { this.ifName = ifName; }
    public Integer getIfIndexMin() { return ifIndexMin; }
    public void setIfIndexMin(Integer ifIndexMin) { this.ifIndexMin = ifIndexMin; }
    public Integer getIfIndexMax() { return ifIndexMax; }
    public void setIfIndexMax(Integer ifIndexMax) { this.ifIndexMax = ifIndexMax; }
    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public Integer getMtuMin() { return mtuMin; }
    public void setMtuMin(Integer mtuMin) { this.mtuMin = mtuMin; }
    public Integer getMtuMax() { return mtuMax; }
    public void setMtuMax(Integer mtuMax) { this.mtuMax = mtuMax; }
    public List<InterfaceOperState> getOperState() { return operState; }
    public void setOperState(List<InterfaceOperState> operState) { this.operState = operState; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<InterfaceCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<InterfaceCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
