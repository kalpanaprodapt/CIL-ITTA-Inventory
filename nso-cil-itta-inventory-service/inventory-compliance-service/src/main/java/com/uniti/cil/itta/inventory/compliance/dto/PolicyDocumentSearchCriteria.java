package com.uniti.cil.itta.inventory.compliance.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Policy Document search endpoint. */
public class PolicyDocumentSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String title;
    private String docVersion;
    private LocalDate effectiveDateFrom;
    private LocalDate effectiveDateTo;
    private String owner;
    private String documentUrl;
    private List<PolicyDocumentLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private List<PolicyDocumentCriticality> criticality;
    private String costCenter;

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
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDocVersion() { return docVersion; }
    public void setDocVersion(String docVersion) { this.docVersion = docVersion; }
    public LocalDate getEffectiveDateFrom() { return effectiveDateFrom; }
    public void setEffectiveDateFrom(LocalDate effectiveDateFrom) { this.effectiveDateFrom = effectiveDateFrom; }
    public LocalDate getEffectiveDateTo() { return effectiveDateTo; }
    public void setEffectiveDateTo(LocalDate effectiveDateTo) { this.effectiveDateTo = effectiveDateTo; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public String getDocumentUrl() { return documentUrl; }
    public void setDocumentUrl(String documentUrl) { this.documentUrl = documentUrl; }
    public List<PolicyDocumentLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<PolicyDocumentLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public List<PolicyDocumentCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<PolicyDocumentCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
