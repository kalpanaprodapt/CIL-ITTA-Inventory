package com.uniti.cil.itta.inventory.mpls.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Pseudo Wire search endpoint. */
public class PseudoWireSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private Long vcIdMin;
    private Long vcIdMax;
    private UUID aEndId;
    private UUID zEndId;
    private String encapsulation;
    private Boolean controlWord;
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
    public Long getVcIdMin() { return vcIdMin; }
    public void setVcIdMin(Long vcIdMin) { this.vcIdMin = vcIdMin; }
    public Long getVcIdMax() { return vcIdMax; }
    public void setVcIdMax(Long vcIdMax) { this.vcIdMax = vcIdMax; }
    public UUID getAEndId() { return aEndId; }
    public void setAEndId(UUID aEndId) { this.aEndId = aEndId; }
    public UUID getZEndId() { return zEndId; }
    public void setZEndId(UUID zEndId) { this.zEndId = zEndId; }
    public String getEncapsulation() { return encapsulation; }
    public void setEncapsulation(String encapsulation) { this.encapsulation = encapsulation; }
    public Boolean getControlWord() { return controlWord; }
    public void setControlWord(Boolean controlWord) { this.controlWord = controlWord; }
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
