package com.uniti.cil.itta.inventory.software.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.LicenseKeySearchCriteria;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing LicenseKey search. */
public final class LicenseKeySpecifications {

    private LicenseKeySpecifications() {
    }

    public static Specification<LicenseKey> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<LicenseKey> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<LicenseKey> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<LicenseKey> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicenseKey> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicenseKey> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicenseKey> poolIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("poolId"), value);
    }

    public static Specification<LicenseKey> keyValueContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("keyValue")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicenseKey> featureContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("feature")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicenseKey> expiresOnFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("expiresOn"), value);
    }

    public static Specification<LicenseKey> expiresOnTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("expiresOn"), value);
    }

    public static Specification<LicenseKey> assignedDeviceIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("assignedDeviceId"), value);
    }

    public static Specification<LicenseKey> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<LicenseKey> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<LicenseKey> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<LicenseKey> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<LicenseKey> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<LicenseKey> lifecycleStageIn(List<LicenseKeyLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<LicenseKey> fromCriteria(String tenantId, LicenseKeySearchCriteria criteria) {
        Specification<LicenseKey> spec = tenant(tenantId);
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
        if (criteria.getPoolId() != null) {
            spec = spec.and(poolIdEquals(criteria.getPoolId()));
        }
        if (hasText(criteria.getKeyValue())) {
            spec = spec.and(keyValueContains(criteria.getKeyValue().trim()));
        }
        if (hasText(criteria.getFeature())) {
            spec = spec.and(featureContains(criteria.getFeature().trim()));
        }
        if (criteria.getExpiresOnFrom() != null) {
            spec = spec.and(expiresOnFrom(criteria.getExpiresOnFrom()));
        }
        if (criteria.getExpiresOnTo() != null) {
            spec = spec.and(expiresOnTo(criteria.getExpiresOnTo()));
        }
        if (criteria.getAssignedDeviceId() != null) {
            spec = spec.and(assignedDeviceIdEquals(criteria.getAssignedDeviceId()));
        }
        if (criteria.getEffectiveFromFrom() != null) {
            spec = spec.and(effectiveFromFrom(criteria.getEffectiveFromFrom()));
        }
        if (criteria.getEffectiveFromTo() != null) {
            spec = spec.and(effectiveFromTo(criteria.getEffectiveFromTo()));
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
        return spec;
    }
}
