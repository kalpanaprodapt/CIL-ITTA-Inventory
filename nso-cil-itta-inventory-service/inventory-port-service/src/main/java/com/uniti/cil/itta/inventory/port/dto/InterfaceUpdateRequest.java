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

/** Partial update payload for a Interface; null properties are left unchanged. */
public class InterfaceUpdateRequest {

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
    private UUID portId;
    @Size(max = 255)
    private String ifName;
    private Integer ifIndex;
    @Size(max = 255)
    private String macAddress;
    private Integer mtu;
    private InterfaceOperState operState;
    @Size(max = 255)
    private String owner;
    private InterfaceCriticality criticality;
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
    public UUID getPortId() { return portId; }
    public void setPortId(UUID portId) { this.portId = portId; }
    public String getIfName() { return ifName; }
    public void setIfName(String ifName) { this.ifName = ifName; }
    public Integer getIfIndex() { return ifIndex; }
    public void setIfIndex(Integer ifIndex) { this.ifIndex = ifIndex; }
    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }
    public Integer getMtu() { return mtu; }
    public void setMtu(Integer mtu) { this.mtu = mtu; }
    public InterfaceOperState getOperState() { return operState; }
    public void setOperState(InterfaceOperState operState) { this.operState = operState; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public InterfaceCriticality getCriticality() { return criticality; }
    public void setCriticality(InterfaceCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }}
