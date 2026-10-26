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
 * Event Source - Alarms and Events domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_event_source",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_event_source_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_event_source_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_event_source_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_event_source_ext", columnList = "external_ref")
        })
public class EventSource extends BaseEntity {

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

    @Column(name = "source_name", nullable = false, length = 255)
    private String sourceName;

    @Enumerated(EnumType.STRING)
    @Column(name = "protocol", nullable = true, length = 40)
    private EventSourceProtocol protocol;

    @Column(name = "endpoint", nullable = true, length = 255)
    private String endpoint;

    @Column(name = "heartbeat_seconds", nullable = true)
    private Integer heartbeatSeconds;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private EventSourceLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public EventSource() {
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

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public EventSourceProtocol getProtocol() { return protocol; }
    public void setProtocol(EventSourceProtocol protocol) { this.protocol = protocol; }

    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }

    public Integer getHeartbeatSeconds() { return heartbeatSeconds; }
    public void setHeartbeatSeconds(Integer heartbeatSeconds) { this.heartbeatSeconds = heartbeatSeconds; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public EventSourceLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(EventSourceLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventSource other)) {
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
        return "EventSource{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "sourceName=" + sourceName + ", " +
                "protocol=" + protocol + ", " +
                "endpoint=" + endpoint + ", " +
                "heartbeatSeconds=" + heartbeatSeconds + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + "}";
    }
}
