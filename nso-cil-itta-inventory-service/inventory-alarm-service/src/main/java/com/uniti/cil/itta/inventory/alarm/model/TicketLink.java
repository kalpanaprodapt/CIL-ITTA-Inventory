package com.uniti.cil.itta.inventory.alarm.model;

import com.uniti.cil.itta.inventory.common.model.BaseEntity;
import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/**
 * Ticket Link - Alarms and Events domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_ticket_link",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_ticket_link_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_ticket_link_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_ticket_link_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_ticket_link_ext", columnList = "external_ref")
        })
public class TicketLink extends BaseEntity {

    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", nullable = true, length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LifecycleStatus status;

    @Column(name = "external_ref", nullable = true, length = 255)
    private String externalRef;

    @Column(name = "alarm_id", nullable = false)
    private UUID alarmId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_system", nullable = true, length = 40)
    private TicketLinkTicketSystem ticketSystem;

    @Column(name = "ticket_number", nullable = false, length = 255)
    private String ticketNumber;

    @Column(name = "linked_at", nullable = true)
    private Instant linkedAt;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private TicketLinkCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    public TicketLink() {
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LifecycleStatus getStatus() { return status; }
    public void setStatus(LifecycleStatus status) { this.status = status; }

    public String getExternalRef() { return externalRef; }
    public void setExternalRef(String externalRef) { this.externalRef = externalRef; }

    public UUID getAlarmId() { return alarmId; }
    public void setAlarmId(UUID alarmId) { this.alarmId = alarmId; }

    public TicketLinkTicketSystem getTicketSystem() { return ticketSystem; }
    public void setTicketSystem(TicketLinkTicketSystem ticketSystem) { this.ticketSystem = ticketSystem; }

    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    public Instant getLinkedAt() { return linkedAt; }
    public void setLinkedAt(Instant linkedAt) { this.linkedAt = linkedAt; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public TicketLinkCriticality getCriticality() { return criticality; }
    public void setCriticality(TicketLinkCriticality criticality) { this.criticality = criticality; }

    public String getCostCenter() { return costCenter; }
    public void setCostCenter(String costCenter) { this.costCenter = costCenter; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketLink other)) {
            return false;
        }
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "TicketLink{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "alarmId=" + alarmId + ", " +
                "ticketSystem=" + ticketSystem + ", " +
                "ticketNumber=" + ticketNumber + ", " +
                "linkedAt=" + linkedAt + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + "}";
    }
}
