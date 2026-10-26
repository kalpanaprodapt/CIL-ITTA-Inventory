package com.uniti.cil.itta.inventory.software.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Patch search endpoint. */
public class PatchSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID versionId;
    private String patchId;
    private List<PatchSeverity> severity;
    private LocalDate releasedOnFrom;
    private LocalDate releasedOnTo;
    private Boolean rebootRequired;
    private List<PatchCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;
    private LocalDate effectiveToFrom;
    private LocalDate effectiveToTo;

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
    public UUID getVersionId() { return versionId; }
    public void setVersionId(UUID versionId) { this.versionId = versionId; }
    public String getPatchId() { return patchId; }
    public void setPatchId(String patchId) { this.patchId = patchId; }
    public List<PatchSeverity> getSeverity() { return severity; }
    public void setSeverity(List<PatchSeverity> severity) { this.severity = severity; }
    public LocalDate getReleasedOnFrom() { return releasedOnFrom; }
    public void setReleasedOnFrom(LocalDate releasedOnFrom) { this.releasedOnFrom = releasedOnFrom; }
    public LocalDate getReleasedOnTo() { return releasedOnTo; }
    public void setReleasedOnTo(LocalDate releasedOnTo) { this.releasedOnTo = releasedOnTo; }
    public Boolean getRebootRequired() { return rebootRequired; }
    public void setRebootRequired(Boolean rebootRequired) { this.rebootRequired = rebootRequired; }
    public List<PatchCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<PatchCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }
    public LocalDate getEffectiveToFrom() { return effectiveToFrom; }
    public void setEffectiveToFrom(LocalDate effectiveToFrom) { this.effectiveToFrom = effectiveToFrom; }
    public LocalDate getEffectiveToTo() { return effectiveToTo; }
    public void setEffectiveToTo(LocalDate effectiveToTo) { this.effectiveToTo = effectiveToTo; }}
