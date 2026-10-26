package com.uniti.cil.itta.inventory.facility.model;

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
 * Power Feed - Facility domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_power_feed",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_power_feed_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_power_feed_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_power_feed_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_power_feed_ext", columnList = "external_ref")
        })
public class PowerFeed extends BaseEntity {

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

    @Column(name = "room_id", nullable = false)
    private UUID roomId;

    @Enumerated(EnumType.STRING)
    @Column(name = "feed_type", nullable = true, length = 40)
    private PowerFeedFeedType feedType;

    @Column(name = "voltage", nullable = true, precision = 18, scale = 4)
    private BigDecimal voltage;

    @Column(name = "amperage", nullable = true, precision = 18, scale = 4)
    private BigDecimal amperage;

    @Column(name = "phase", nullable = true, length = 255)
    private String phase;

    @Column(name = "breaker_label", nullable = true, length = 255)
    private String breakerLabel;

    @Column(name = "redundancy_group", nullable = true, length = 255)
    private String redundancyGroup;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private PowerFeedCriticality criticality;

    @Column(name = "cost_center", nullable = true, length = 255)
    private String costCenter;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    public PowerFeed() {
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

    public UUID getRoomId() { return roomId; }
    public void setRoomId(UUID roomId) { this.roomId = roomId; }

    public PowerFeedFeedType getFeedType() { return feedType; }
    public void setFeedType(PowerFeedFeedType feedType) { this.feedType = feedType; }

    public BigDecimal getVoltage() { return voltage; }
    public void setVoltage(BigDecimal voltage) { this.voltage = voltage; }

    public BigDecimal getAmperage() { return amperage; }
    public void setAmperage(BigDecimal amperage) { this.amperage = amperage; }

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }

    public String getBreakerLabel() { return breakerLabel; }
    public void setBreakerLabel(String breakerLabel) { this.breakerLabel = breakerLabel; }

    public String getRedundancyGroup() { return redundancyGroup; }
    public void setRedundancyGroup(String redundancyGroup) { this.redundancyGroup = redundancyGroup; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public PowerFeedCriticality getCriticality() { return criticality; }
    public void setCriticality(PowerFeedCriticality criticality) { this.criticality = criticality; }

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
        if (!(o instanceof PowerFeed other)) {
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
        return "PowerFeed{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "roomId=" + roomId + ", " +
                "feedType=" + feedType + ", " +
                "voltage=" + voltage + ", " +
                "amperage=" + amperage + ", " +
                "phase=" + phase + ", " +
                "breakerLabel=" + breakerLabel + ", " +
                "redundancyGroup=" + redundancyGroup + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + ", " +
                "costCenter=" + costCenter + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + "}";
    }
}
