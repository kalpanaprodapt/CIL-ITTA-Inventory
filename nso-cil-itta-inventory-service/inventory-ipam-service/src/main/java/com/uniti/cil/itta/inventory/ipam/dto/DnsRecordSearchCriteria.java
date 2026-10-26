package com.uniti.cil.itta.inventory.ipam.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Dns Record search endpoint. */
public class DnsRecordSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private String zoneName;
    private List<DnsRecordRecordType> recordType;
    private String recordValue;
    private Integer ttlMin;
    private Integer ttlMax;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<DnsRecordCriticality> criticality;
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
    public String getZoneName() { return zoneName; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }
    public List<DnsRecordRecordType> getRecordType() { return recordType; }
    public void setRecordType(List<DnsRecordRecordType> recordType) { this.recordType = recordType; }
    public String getRecordValue() { return recordValue; }
    public void setRecordValue(String recordValue) { this.recordValue = recordValue; }
    public Integer getTtlMin() { return ttlMin; }
    public void setTtlMin(Integer ttlMin) { this.ttlMin = ttlMin; }
    public Integer getTtlMax() { return ttlMax; }
    public void setTtlMax(Integer ttlMax) { this.ttlMax = ttlMax; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<DnsRecordCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<DnsRecordCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
