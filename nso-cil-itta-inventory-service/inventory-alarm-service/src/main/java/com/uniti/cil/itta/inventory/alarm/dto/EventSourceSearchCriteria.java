package com.uniti.cil.itta.inventory.alarm.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Event Source search endpoint. */
public class EventSourceSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String sourceName;
    private List<EventSourceProtocol> protocol;
    private String endpoint;
    private Integer heartbeatSecondsMin;
    private Integer heartbeatSecondsMax;
    private String labels;
    private List<EventSourceLifecycleStage> lifecycleStage;
    private String sourceSystem;
    private String region;
    private String owner;

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
    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }
    public List<EventSourceProtocol> getProtocol() { return protocol; }
    public void setProtocol(List<EventSourceProtocol> protocol) { this.protocol = protocol; }
    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public Integer getHeartbeatSecondsMin() { return heartbeatSecondsMin; }
    public void setHeartbeatSecondsMin(Integer heartbeatSecondsMin) { this.heartbeatSecondsMin = heartbeatSecondsMin; }
    public Integer getHeartbeatSecondsMax() { return heartbeatSecondsMax; }
    public void setHeartbeatSecondsMax(Integer heartbeatSecondsMax) { this.heartbeatSecondsMax = heartbeatSecondsMax; }
    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }
    public List<EventSourceLifecycleStage> getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(List<EventSourceLifecycleStage> lifecycleStage) { this.lifecycleStage = lifecycleStage; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }}
