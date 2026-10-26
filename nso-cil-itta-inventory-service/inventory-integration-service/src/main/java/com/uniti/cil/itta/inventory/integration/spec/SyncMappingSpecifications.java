package com.uniti.cil.itta.inventory.integration.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.SyncMappingSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing SyncMapping search. */
public final class SyncMappingSpecifications {

    private SyncMappingSpecifications() {
    }

    public static Specification<SyncMapping> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<SyncMapping> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<SyncMapping> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<SyncMapping> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncMapping> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncMapping> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncMapping> jobIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("jobId"), value);
    }

    public static Specification<SyncMapping> sourceFieldContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceField")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncMapping> targetFieldContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("targetField")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncMapping> transformContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("transform")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncMapping> requiredEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("required"), value);
    }

    public static Specification<SyncMapping> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncMapping> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<SyncMapping> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<SyncMapping> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<SyncMapping> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<SyncMapping> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<SyncMapping> fromCriteria(String tenantId, SyncMappingSearchCriteria criteria) {
        Specification<SyncMapping> spec = tenant(tenantId);
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
        if (criteria.getJobId() != null) {
            spec = spec.and(jobIdEquals(criteria.getJobId()));
        }
        if (hasText(criteria.getSourceField())) {
            spec = spec.and(sourceFieldContains(criteria.getSourceField().trim()));
        }
        if (hasText(criteria.getTargetField())) {
            spec = spec.and(targetFieldContains(criteria.getTargetField().trim()));
        }
        if (hasText(criteria.getTransform())) {
            spec = spec.and(transformContains(criteria.getTransform().trim()));
        }
        if (criteria.getRequired() != null) {
            spec = spec.and(requiredEquals(criteria.getRequired()));
        }
        if (hasText(criteria.getCostCenter())) {
            spec = spec.and(costCenterContains(criteria.getCostCenter().trim()));
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
        return spec;
    }
}
