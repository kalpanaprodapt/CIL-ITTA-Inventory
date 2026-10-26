package com.uniti.cil.itta.inventory.device.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Line Card search endpoint. */
public class LineCardSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID chassisId;
    private Integer slotNumberMin;
    private Integer slotNumberMax;
    private String cardType;
    private Integer portCountMin;
    private Integer portCountMax;
    private String serialNumber;
    private String firmware;
    private String region;
    private String owner;
    private List<LineCardCriticality> criticality;
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
    public UUID getChassisId() { return chassisId; }
    public void setChassisId(UUID chassisId) { this.chassisId = chassisId; }
    public Integer getSlotNumberMin() { return slotNumberMin; }
    public void setSlotNumberMin(Integer slotNumberMin) { this.slotNumberMin = slotNumberMin; }
    public Integer getSlotNumberMax() { return slotNumberMax; }
    public void setSlotNumberMax(Integer slotNumberMax) { this.slotNumberMax = slotNumberMax; }
    public String getCardType() { return cardType; }
    public void setCardType(String cardType) { this.cardType = cardType; }
    public Integer getPortCountMin() { return portCountMin; }
    public void setPortCountMin(Integer portCountMin) { this.portCountMin = portCountMin; }
    public Integer getPortCountMax() { return portCountMax; }
    public void setPortCountMax(Integer portCountMax) { this.portCountMax = portCountMax; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public String getFirmware() { return firmware; }
    public void setFirmware(String firmware) { this.firmware = firmware; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<LineCardCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<LineCardCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }}
