package com.uniti.cil.itta.inventory.mpls.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.QosPolicySearchCriteria;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing QosPolicy search. */
public final class QosPolicySpecifications {

    private QosPolicySpecifications() {
    }

    public static Specification<QosPolicy> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<QosPolicy> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<QosPolicy> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<QosPolicy> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<QosPolicy> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<QosPolicy> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<QosPolicy> policyNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("policyName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<QosPolicy> classCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("classCount"), value);
    }

    public static Specification<QosPolicy> classCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("classCount"), value);
    }

    public static Specification<QosPolicy> cirMbpsMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("cirMbps"), value);
    }

    public static Specification<QosPolicy> cirMbpsMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("cirMbps"), value);
    }

    public static Specification<QosPolicy> pirMbpsMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("pirMbps"), value);
    }

    public static Specification<QosPolicy> pirMbpsMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("pirMbps"), value);
    }

    public static Specification<QosPolicy> markingSchemeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("markingScheme")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<QosPolicy> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<QosPolicy> lifecycleStageIn(List<QosPolicyLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<QosPolicy> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<QosPolicy> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<QosPolicy> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<QosPolicy> fromCriteria(String tenantId, QosPolicySearchCriteria criteria) {
        Specification<QosPolicy> spec = tenant(tenantId);
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
        if (hasText(criteria.getPolicyName())) {
            spec = spec.and(policyNameContains(criteria.getPolicyName().trim()));
        }
        if (criteria.getClassCountMin() != null) {
            spec = spec.and(classCountMin(criteria.getClassCountMin()));
        }
        if (criteria.getClassCountMax() != null) {
            spec = spec.and(classCountMax(criteria.getClassCountMax()));
        }
        if (criteria.getCirMbpsMin() != null) {
            spec = spec.and(cirMbpsMin(criteria.getCirMbpsMin()));
        }
        if (criteria.getCirMbpsMax() != null) {
            spec = spec.and(cirMbpsMax(criteria.getCirMbpsMax()));
        }
        if (criteria.getPirMbpsMin() != null) {
            spec = spec.and(pirMbpsMin(criteria.getPirMbpsMin()));
        }
        if (criteria.getPirMbpsMax() != null) {
            spec = spec.and(pirMbpsMax(criteria.getPirMbpsMax()));
        }
        if (hasText(criteria.getMarkingScheme())) {
            spec = spec.and(markingSchemeContains(criteria.getMarkingScheme().trim()));
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
        if (hasText(criteria.getRegion())) {
            spec = spec.and(regionContains(criteria.getRegion().trim()));
        }
        if (hasText(criteria.getOwner())) {
            spec = spec.and(ownerContains(criteria.getOwner().trim()));
        }
        return spec;
    }
}
