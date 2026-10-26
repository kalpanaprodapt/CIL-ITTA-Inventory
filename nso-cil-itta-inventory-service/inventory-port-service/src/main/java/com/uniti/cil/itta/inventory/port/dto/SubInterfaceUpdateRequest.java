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

/** Partial update payload for a Sub Interface; null properties are left unchanged. */
public class SubInterfaceUpdateRequest {

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
    private UUID interfaceId;
    private Integer vlanTag;
    @Size(max = 255)
    private String encapsulation;
    @Size(max = 255)
    private String ipv4Address;
    @Size(max = 255)
    private String ipv6Address;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
    @Size(max = 255)
    private String labels;
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
    public UUID getInterfaceId() { return interfaceId; }
    public void setInterfaceId(UUID interfaceId) { this.interfaceId = interfaceId; }
    public Integer getVlanTag() { return vlanTag; }
    public void setVlanTag(Integer vlanTag) { this.vlanTag = vlanTag; }
    public String getEncapsulation() { return encapsulation; }
    public void setEncapsulation(String encapsulation) { this.encapsulation = encapsulation; }
    public String getIpv4Address() { return ipv4Address; }
    public void setIpv4Address(String ipv4Address) { this.ipv4Address = ipv4Address; }
    public String getIpv6Address() { return ipv6Address; }
    public void setIpv6Address(String ipv6Address) { this.ipv6Address = ipv6Address; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }
    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }}
