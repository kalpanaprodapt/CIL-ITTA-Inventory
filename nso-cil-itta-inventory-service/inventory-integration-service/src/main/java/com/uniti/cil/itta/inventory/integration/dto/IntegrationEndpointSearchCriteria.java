package com.uniti.cil.itta.inventory.integration.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Integration Endpoint search endpoint. */
public class IntegrationEndpointSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String endpointName;
    private String targetSystem;
    private String baseUrl;
    private List<IntegrationEndpointAuthType> authType;
    private Integer timeoutMsMin;
    private Integer timeoutMsMax;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<IntegrationEndpointCriticality> criticality;
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
    public String getEndpointName() { return endpointName; }
    public void setEndpointName(String endpointName) { this.endpointName = endpointName; }
    public String getTargetSystem() { return targetSystem; }
    public void setTargetSystem(String targetSystem) { this.targetSystem = targetSystem; }
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    public List<IntegrationEndpointAuthType> getAuthType() { return authType; }
    public void setAuthType(List<IntegrationEndpointAuthType> authType) { this.authType = authType; }
    public Integer getTimeoutMsMin() { return timeoutMsMin; }
    public void setTimeoutMsMin(Integer timeoutMsMin) { this.timeoutMsMin = timeoutMsMin; }
    public Integer getTimeoutMsMax() { return timeoutMsMax; }
    public void setTimeoutMsMax(Integer timeoutMsMax) { this.timeoutMsMax = timeoutMsMax; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<IntegrationEndpointCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<IntegrationEndpointCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
