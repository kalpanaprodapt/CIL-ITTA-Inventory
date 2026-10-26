package com.uniti.cil.itta.inventory.virtual.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.K8sClusterSearchCriteria;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing K8sCluster search. */
public final class K8sClusterSpecifications {

    private K8sClusterSpecifications() {
    }

    public static Specification<K8sCluster> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<K8sCluster> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<K8sCluster> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<K8sCluster> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<K8sCluster> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<K8sCluster> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<K8sCluster> clusterNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("clusterName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<K8sCluster> clusterVersionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("clusterVersion")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<K8sCluster> nodeCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("nodeCount"), value);
    }

    public static Specification<K8sCluster> nodeCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("nodeCount"), value);
    }

    public static Specification<K8sCluster> cloudAccountIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("cloudAccountId"), value);
    }

    public static Specification<K8sCluster> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<K8sCluster> apiEndpointContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("apiEndpoint")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<K8sCluster> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<K8sCluster> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<K8sCluster> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<K8sCluster> lifecycleStageIn(List<K8sClusterLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<K8sCluster> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<K8sCluster> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<K8sCluster> fromCriteria(String tenantId, K8sClusterSearchCriteria criteria) {
        Specification<K8sCluster> spec = tenant(tenantId);
        if (criteria == null) {
            return spec;
        }
        if (hasText(criteria.getQ())) {
            spec = spec.and(freeText(criteria.getQ().trim()));
        }
        if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
            spec = spec.and(statusIn(criteria.getStatus()));
        }
        if (hasText(criteria.getCode())) {
            spec = spec.and(codeContains(criteria.getCode().trim()));
        }
        if (hasText(criteria.getName())) {
            spec = spec.and(nameContains(criteria.getName().trim()));
        }
        if (hasText(criteria.getExternalRef())) {
            spec = spec.and(externalRefContains(criteria.getExternalRef().trim()));
        }
        if (hasText(criteria.getClusterName())) {
            spec = spec.and(clusterNameContains(criteria.getClusterName().trim()));
        }
        if (hasText(criteria.getClusterVersion())) {
            spec = spec.and(clusterVersionContains(criteria.getClusterVersion().trim()));
        }
        if (criteria.getNodeCountMin() != null) {
            spec = spec.and(nodeCountMin(criteria.getNodeCountMin()));
        }
        if (criteria.getNodeCountMax() != null) {
            spec = spec.and(nodeCountMax(criteria.getNodeCountMax()));
        }
        if (criteria.getCloudAccountId() != null) {
            spec = spec.and(cloudAccountIdEquals(criteria.getCloudAccountId()));
        }
        if (hasText(criteria.getRegion())) {
            spec = spec.and(regionContains(criteria.getRegion().trim()));
        }
        if (hasText(criteria.getApiEndpoint())) {
            spec = spec.and(apiEndpointContains(criteria.getApiEndpoint().trim()));
        }
        if (criteria.getEffectiveToFrom() != null) {
            spec = spec.and(effectiveToFrom(criteria.getEffectiveToFrom()));
        }
        if (criteria.getEffectiveToTo() != null) {
            spec = spec.and(effectiveToTo(criteria.getEffectiveToTo()));
        }
        if (hasText(criteria.getLabels())) {
            spec = spec.and(labelsContains(criteria.getLabels().trim()));
        }
        if (criteria.getLifecycleStage() != null && !criteria.getLifecycleStage().isEmpty()) {
            spec = spec.and(lifecycleStageIn(criteria.getLifecycleStage()));
        }
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
        }
        if (hasText(criteria.getOwner())) {
            spec = spec.and(ownerContains(criteria.getOwner().trim()));
        }
        return spec;
    }
}
