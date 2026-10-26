package com.uniti.cil.itta.inventory.capacity.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Forecast Model search endpoint. */
public class ForecastModelSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String modelName;
    private List<ForecastModelAlgorithm> algorithm;
    private Instant trainedOnFrom;
    private Instant trainedOnTo;
    private BigDecimal mapeMin;
    private BigDecimal mapeMax;
    private List<ForecastModelCriticality> criticality;
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
    public String getModelName() { return modelName; }
    public void setModelName(String modelName) { this.modelName = modelName; }
    public List<ForecastModelAlgorithm> getAlgorithm() { return algorithm; }
    public void setAlgorithm(List<ForecastModelAlgorithm> algorithm) { this.algorithm = algorithm; }
    public Instant getTrainedOnFrom() { return trainedOnFrom; }
    public void setTrainedOnFrom(Instant trainedOnFrom) { this.trainedOnFrom = trainedOnFrom; }
    public Instant getTrainedOnTo() { return trainedOnTo; }
    public void setTrainedOnTo(Instant trainedOnTo) { this.trainedOnTo = trainedOnTo; }
    public BigDecimal getMapeMin() { return mapeMin; }
    public void setMapeMin(BigDecimal mapeMin) { this.mapeMin = mapeMin; }
    public BigDecimal getMapeMax() { return mapeMax; }
    public void setMapeMax(BigDecimal mapeMax) { this.mapeMax = mapeMax; }
    public List<ForecastModelCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<ForecastModelCriticality> criticality) { this.criticality = criticality; }
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
