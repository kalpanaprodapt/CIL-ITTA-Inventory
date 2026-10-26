package com.uniti.cil.itta.inventory.alarm.dto;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Query-string criteria accepted by the Ticket Link search endpoint. */
public class TicketLinkSearchCriteria {

    private String q;
    private List<LifecycleStatus> status;
    private String code;
    private String name;
    private String externalRef;
    private UUID alarmId;
    private List<TicketLinkTicketSystem> ticketSystem;
    private String ticketNumber;
    private Instant linkedAtFrom;
    private Instant linkedAtTo;
    private String owner;
    private List<TicketLinkCriticality> criticality;
    private String costCenter;
    private LocalDate effectiveFromFrom;
    private LocalDate effectiveFromTo;

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
    public UUID getAlarmId() { return alarmId; }
    public void setAlarmId(UUID alarmId) { this.alarmId = alarmId; }
    public List<TicketLinkTicketSystem> getTicketSystem() { return ticketSystem; }
    public void setTicketSystem(List<TicketLinkTicketSystem> ticketSystem) { this.ticketSystem = ticketSystem; }
    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }
    public Instant getLinkedAtFrom() { return linkedAtFrom; }
    public void setLinkedAtFrom(Instant linkedAtFrom) { this.linkedAtFrom = linkedAtFrom; }
    public Instant getLinkedAtTo() { return linkedAtTo; }
    public void setLinkedAtTo(Instant linkedAtTo) { this.linkedAtTo = linkedAtTo; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public List<TicketLinkCriticality> getCriticality() { return criticality; }
    public void setCriticality(List<TicketLinkCriticality> criticality) { this.criticality = criticality; }
    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }
    public LocalDate getEffectiveFromFrom() { return effectiveFromFrom; }
    public void setEffectiveFromFrom(LocalDate effectiveFromFrom) { this.effectiveFromFrom = effectiveFromFrom; }
    public LocalDate getEffectiveFromTo() { return effectiveFromTo; }
    public void setEffectiveFromTo(LocalDate effectiveFromTo) { this.effectiveFromTo = effectiveFromTo; }}
