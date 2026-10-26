package com.uniti.cil.itta.inventory.virtual.model;

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
 * K8s Cluster - Virtual and Cloud domain of the ITTA/CIL network inventory.
 */
@Entity
@Table(name = "inv_k8s_cluster",
        uniqueConstraints = @UniqueConstraint(name = "uk_inv_k8s_cluster_tenant_code", columnNames = {"tenant_id", "code"}),
        indexes = {
                @Index(name = "ix_inv_k8s_cluster_status", columnList = "tenant_id,status"),
                @Index(name = "ix_inv_k8s_cluster_name", columnList = "tenant_id,name"),
                @Index(name = "ix_inv_k8s_cluster_ext", columnList = "external_ref")
        })
public class K8sCluster extends BaseEntity {

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

    @Column(name = "cluster_name", nullable = false, length = 255)
    private String clusterName;

    @Column(name = "cluster_version", nullable = false, length = 255)
    private String clusterVersion;

    @Column(name = "node_count", nullable = false)
    private Integer nodeCount;

    @Column(name = "cloud_account_id", nullable = true)
    private UUID cloudAccountId;

    @Column(name = "region", nullable = true, length = 255)
    private String region;

    @Column(name = "api_endpoint", nullable = true, length = 255)
    private String apiEndpoint;

    @Column(name = "effective_to", nullable = true)
    private LocalDate effectiveTo;

    @Column(name = "labels", nullable = true, length = 255)
    private String labels;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_stage", nullable = true, length = 40)
    private K8sClusterLifecycleStage lifecycleStage;

    @Column(name = "source_system", nullable = true, length = 255)
    private String sourceSystem;

    @Column(name = "owner", nullable = true, length = 255)
    private String owner;

    public K8sCluster() {
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

    public String getClusterName() { return clusterName; }
    public void setClusterName(String clusterName) { this.clusterName = clusterName; }

    public String getClusterVersion() { return clusterVersion; }
    public void setClusterVersion(String clusterVersion) { this.clusterVersion = clusterVersion; }

    public Integer getNodeCount() { return nodeCount; }
    public void setNodeCount(Integer nodeCount) { this.nodeCount = nodeCount; }

    public UUID getCloudAccountId() { return cloudAccountId; }
    public void setCloudAccountId(UUID cloudAccountId) { this.cloudAccountId = cloudAccountId; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getApiEndpoint() { return apiEndpoint; }
    public void setApiEndpoint(String apiEndpoint) { this.apiEndpoint = apiEndpoint; }

    public LocalDate getEffectiveTo() { return effectiveTo; }
    public void setEffectiveTo(LocalDate effectiveTo) { this.effectiveTo = effectiveTo; }

    public String getLabels() { return labels; }
    public void setLabels(String labels) { this.labels = labels; }

    public K8sClusterLifecycleStage getLifecycleStage() { return lifecycleStage; }
    public void setLifecycleStage(K8sClusterLifecycleStage lifecycleStage) { this.lifecycleStage = lifecycleStage; }

    public String getSourceSystem() { return sourceSystem; }
    public void setSourceSystem(String sourceSystem) { this.sourceSystem = sourceSystem; }

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
        if (!(o instanceof K8sCluster other)) {
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
        return "K8sCluster{" +
                "id=" + getId() + ", " +
                "code=" + code + ", " +
                "name=" + name + ", " +
                "description=" + description + ", " +
                "status=" + status + ", " +
                "externalRef=" + externalRef + ", " +
                "clusterName=" + clusterName + ", " +
                "clusterVersion=" + clusterVersion + ", " +
                "nodeCount=" + nodeCount + ", " +
                "cloudAccountId=" + cloudAccountId + ", " +
                "region=" + region + ", " +
                "apiEndpoint=" + apiEndpoint + ", " +
                "effectiveTo=" + effectiveTo + ", " +
                "labels=" + labels + ", " +
                "lifecycleStage=" + lifecycleStage + ", " +
                "sourceSystem=" + sourceSystem + ", " +
                "owner=" + owner + "}";
    }
}
