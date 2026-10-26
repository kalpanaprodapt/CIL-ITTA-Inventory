package com.uniti.cil.itta.inventory.facility.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Rack Unit search endpoint. */
public class RackUnitSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID rackId;
    private Integer unitNumberMin;
    private Integer unitNumberMax;
    private Boolean occupied;
    private UUID mountedDeviceId;
    private List<RackUnitFace> face;
    private String sourceSystem;
    private String region;
    private String owner;
    private List<RackUnitCriticality> criticality;
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
    public UUID getRackId() { return rackId; }
    public void setRackId(UUID rackId) { this.rackId = rackId; }
    public Integer getUnitNumberMin() { return unitNumberMin; }
    public void setUnitNumberMin(Integer unitNumberMin) { this.unitNumberMin = unitNumberMin; }
    public Integer getUnitNumberMax() { return unitNumberMax; }
    public void setUnitNumberMax(Integer unitNumberMax) { this.unitNumberMax = unitNumberMax; }
    public Boolean getOccupied() { return occupied; }
    public void setOccupied(Boolean occupied) { this.occupied = occupied; }
    public UUID getMountedDeviceId() { return mountedDeviceId; }
    public void setMountedDeviceId(UUID mountedDeviceId) { this.mountedDeviceId = mountedDeviceId; }
    public List<RackUnitFace> getFace() { return face; }
    public void setFace(List<RackUnitFace> face) { this.face = face; }
    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<RackUnitCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<RackUnitCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
