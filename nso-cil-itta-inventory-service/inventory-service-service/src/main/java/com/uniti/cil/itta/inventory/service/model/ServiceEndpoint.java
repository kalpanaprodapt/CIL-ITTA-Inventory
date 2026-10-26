package com.uniti.cil.itta.inventory.service.model;

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
 * Service Endpoint - Services domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_service_endpoint",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_service_endpoint_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_service_endpoint_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_service_endpoint_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_service_endpoint_ext", columnList = "external_ref")
        })
public class ServiceEndpoint extends BaseEntity {

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

    @Column(name = "service_id", nullable = false)
    private UUID serviceId;

    @Column(name = "site_id", nullable = true)
    private UUID siteId;

    @Column(name = "port_id", nullable = true)
    private UUID portId;

    @Enumerated(EnumType.STRING)
    @Column(name = "endpoint_role", nullable = true, length = 40)
    private ServiceEndpointEndpointRole endpointRole;

    @Column(name = "handoff", nullable = true, length = 255)
    private String handoff;

    @Column(name = "notes", nullable = true, length = 4000)
    private String notes;

    @Column(name = "effective_from", nullable = true)
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private ServiceEndpointLifecycleStage lifecycleStage;

    public ServiceEndpoint() {
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

    public UUID getServiceId() { return serviceId; }
    public void setServiceId(UUID serviceId) { this.serviceId = serviceId; }

    public UUID getSiteId() { return siteId; }
    public void setSiteId(UUID siteId) { this.siteId = siteId; }

    public UUID getPortId() { return portId; }
    public void setPortId(UUID portId) { this.portId = portId; }

    public ServiceEndpointEndpointRole getEndpointRole() { return endpointRole; }
    public void setEndpointRole(ServiceEndpointEndpointRole endpointRole) { this.endpointRole = endpointRole; }

    public String getHandoff() { return handoff; }
    public void setHandoff(String handoff) { this.handoff = handoff; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDate getEffectiveFrom() { return effectiveFrom; }
    public void setEffectiveFrom(LocalDate effectiveFrom) { this.effectiveFrom = effectiveFrom; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public ServiceEndpointLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(ServiceEndpointLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceEndpoint other)) {
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
        return "ServiceEndpoint{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "serviceId=" + serviceId + ", " +
                "siteId=" + siteId + ", " +
                "portId=" + portId + ", " +
                "endpointRole=" + endpointRole + ", " +
                "handoff=" + handoff + ", " +
                "notes=" + notes + ", " +
                "effectiveFrom=" + effectiveFrom + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + "}";
    }
}
