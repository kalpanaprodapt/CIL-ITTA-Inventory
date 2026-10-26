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
 * Service Instance - Services domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_service_instance",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_service_instance_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_service_instance_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_service_instance_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_service_instance_ext", columnList = "external_ref")
        })
public class ServiceInstance extends BaseEntity {

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

    @Column(name = "catalog_item_id", nullable = false)
    private UUID catalogItemId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "activated_on", nullable = true)
    private LocalDate activatedOn;

    @Column(name = "bandwidth_mbps", nullable = true)
    private Long bandwidthMbps;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_state", nullable = true, length = 40)
    private ServiceInstanceServiceState serviceState;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private ServiceInstanceLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "criticality", nullable = true, length = 40)
    private ServiceInstanceCriticality criticality;

    public ServiceInstance() {
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

    public UUID getCatalogItemId() { return catalogItemId; }
    public void setCatalogItemId(UUID catalogItemId) { this.catalogItemId = catalogItemId; }

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }

    public LocalDate getActivatedOn() { return activatedOn; }
    public void setActivatedOn(LocalDate activatedOn) { this.activatedOn = activatedOn; }

    public Long getBandwidthMbps() { return bandwidthMbps; }
    public void setBandwidthMbps(Long bandwidthMbps) { this.bandwidthMbps = bandwidthMbps; }

    public ServiceInstanceServiceState getServiceState() { return serviceState; }
    public void setServiceState(ServiceInstanceServiceState serviceState) { this.serviceState = serviceState; }

    public ServiceInstanceLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(ServiceInstanceLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public ServiceInstanceCriticality getCriticality() { return criticality; }
    public void setCriticality(ServiceInstanceCriticality criticality) { this.criticality = criticality; }

    public boolean isActive() {
        return status == LifecycleStatus.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceInstance other)) {
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
        return "ServiceInstance{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "catalogItemId=" + catalogItemId + ", " +
                "customerId=" + customerId + ", " +
                "activatedOn=" + activatedOn + ", " +
                "bandwidthMbps=" + bandwidthMbps + ", " +
                "serviceState=" + serviceState + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "region=" + region + ", " +
                "owner=" + owner + ", " +
                "criticality=" + criticality + "}";
    }
}
