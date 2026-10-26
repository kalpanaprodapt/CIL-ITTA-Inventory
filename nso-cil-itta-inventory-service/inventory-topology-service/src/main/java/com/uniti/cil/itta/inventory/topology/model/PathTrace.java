package com.uniti.cil.itta.inventory.topology.model;

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
 * Path Trace - Topology and Links domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_path_trace",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_path_trace_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_path_trace_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_path_trace_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_path_trace_ext", columnList = "external_ref")
        })
public class PathTrace extends BaseEntity {

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

    @Column(name = "source_ref", nullable = false, length = 255)
    private String sourceRef;

    @Column(name = "target_ref", nullable = false, length = 255)
    private String targetRef;

    @Column(name = "hop_count", nullable = true)
    private Integer hopCount;

    @Column(name = "total_latency_ms", nullable = true, precision = 18, scale = 4)
    private BigDecimal totalLatencyMs;

    @Column(name = "trace_method", nullable = true, length = 255)
    private String traceMethod;

    @Column(name = "traced_at", nullable = true)
    private Instant tracedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private PathTraceLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private PathTraceCriticality criticality;

    public PathTrace() {
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

    public String getSourceRef() { return sourceRef; }
    public void setSourceRef(String sourceRef) { this.sourceRef = sourceRef; }

    public String getTargetRef() { return targetRef; }
    public void setTargetRef(String targetRef) { this.targetRef = targetRef; }

    public Integer getHopCount() { return hopCount; }
    public void setHopCount(Integer hopCount) { this.hopCount = hopCount; }

    public BigDecimal getTotalLatencyMs() { return totalLatencyMs; }
    public void setTotalLatencyMs(BigDecimal totalLatencyMs) { this.totalLatencyMs = totalLatencyMs; }

    public String getTraceMethod() { return traceMethod; }
    public void setTraceMethod(String traceMethod) { this.traceMethod = traceMethod; }

    public Instant getTracedAt() { return tracedAt; }
    public void setTracedAt(Instant tracedAt) { this.tracedAt = tracedAt; }

    public PathTraceLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(PathTraceLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public PathTraceCriticality getCriticality() { return criticality; }
    public void setCriticality(PathTraceCriticality criticality) { this.criticality = criticality; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PathTrace other)) {
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
        return "PathTrace{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "sourceRef=" + sourceRef + ", " +
                "targetRef=" + targetRef + ", " +
                "hopCount=" + hopCount + ", " +
                "totalLatencyMs=" + totalLatencyMs + ", " +
                "traceMethod=" + traceMethod + ", " +
                "tracedAt=" + tracedAt + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + "}";
    }
}
