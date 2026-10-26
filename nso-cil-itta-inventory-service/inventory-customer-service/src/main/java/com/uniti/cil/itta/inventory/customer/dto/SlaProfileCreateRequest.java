package com.uniti.cil.itta.inventory.customer.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.model.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Payload used to create a Sla Profile. */
public class SlaProfileCreateRequest {

    @NotBlank @Size(max = 255)
    private String code;
    @NotBlank @Size(max = 255)
    private String name;
    @Size(max = 4000)
    private String description;
    @NotNull
    private LifecycleStatus status;
    @Size(max = 255)
    private String externalRef;
    @NotBlank @Size(max = 255)
    private String profileName;
    @NotNull @Digits(integer = 14, fraction = 4)
    private BigDecimal availabilityPct;
    private Integer mttrMinutes;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal latencyMs;
    @Digits(integer = 14, fraction = 4)
    private BigDecimal jitterMs;
    @Size(max = 4000)
    private String creditPolicy;
    @Size(max = 255)
    private String owner;
    private SlaProfileCriticality criticality;
    @Size(max = 255)
    private String costCenter;
    @Size(max = 4000)
    private String notes;
    private LocalDate effectiveFrom;
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
    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }
    public BigDecimal getAvailabilityPct() { return availabilityPct; }
    public void setAvailabilityPct(BigDecimal availabilityPct) { this.availabilityPct = availabilityPct; }
    public Integer getMttrMinutes() { return mttrMinutes; }
    public void setMttrMinutes(Integer mttrMinutes) { this.mttrMinutes = mttrMinutes; }
    public BigDecimal getLatencyMs() { return latencyMs; }
    public void setLatencyMs(BigDecimal latencyMs) { this.latencyMs = latencyMs; }
    public BigDecimal getJitterMs() { return jitterMs; }
    public void setJitterMs(BigDecimal jitterMs) { this.jitterMs = jitterMs; }
    public String getCreditPolicy() { return creditPolicy; }
    public void setCreditPolicy(String creditPolicy) { this.creditPolicy = creditPolicy; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public SlaProfileCriticality getCriticality() { return criticality; }
    public void setCriticality(SlaProfileCriticality criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }}
