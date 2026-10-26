package com.uniti.cil.itta.inventory.circuit.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Partial update payload for a Circuit Provider; null properties are left unchanged. */
public class CircuitProviderUpdateRequest {

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
    private String providerName;
    @Size(max = 255)
    private String accountNumber;
    @Size(max = 255)
    private String noc;
    @Size(max = 255)
    private String escalationEmail;
    private CircuitProviderProviderType providerType;
    @Size(max = 255)
    private String owner;
    private CircuitProviderCriticality criticality;
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
    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getNoc() { return noc; }
    public void setNoc(String noc) { this.noc = noc; }
    public String getEscalationEmail() { return escalationEmail; }
    public void setEscalationEmail(String escalationEmail) { this.escalationEmail = escalationEmail; }
    public CircuitProviderProviderType getProviderType() { return providerType; }
    public void setProviderType(CircuitProviderProviderType providerType) { this.providerType = providerType; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public CircuitProviderCriticality getCriticality() { return criticality; }
    public void setCriticality(CircuitProviderCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }}
