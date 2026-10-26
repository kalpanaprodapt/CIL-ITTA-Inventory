package com.uniti.cil.itta.inventory.ipam.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.IpPoolSearchCriteria;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing IpPool search. */
public final class IpPoolSpecifications {

    private IpPoolSpecifications() {
    }

    public static Specification<IpPool> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<IpPool> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<IpPool> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<IpPool> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpPool> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpPool> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpPool> subnetIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("subnetId"), value);
    }

    public static Specification<IpPool> rangeStartContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("rangeStart")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpPool> rangeEndContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("rangeEnd")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpPool> poolTypeIn(List<IpPoolPoolType> values) {
        return (root, query, cb) -> root.get("poolType").in(values);
    }

    public static Specification<IpPool> leaseSecondsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("leaseSeconds"), value);
    }

    public static Specification<IpPool> leaseSecondsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("leaseSeconds"), value);
    }

    public static Specification<IpPool> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<IpPool> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<IpPool> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<IpPool> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<IpPool> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<IpPool> lifecycleStageIn(List<IpPoolLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<IpPool> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<IpPool> fromCriteria(String tenantId, IpPoolSearchCriteria criteria) {
        Specification<IpPool> spec = tenant(tenantId);
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
        if (criteria.getSubnetId() != null) {
            spec = spec.and(subnetIdEquals(criteria.getSubnetId()));
        }
        if (hasText(criteria.getRangeStart())) {
            spec = spec.and(rangeStartContains(criteria.getRangeStart().trim()));
        }
        if (hasText(criteria.getRangeEnd())) {
            spec = spec.and(rangeEndContains(criteria.getRangeEnd().trim()));
        }
        if (criteria.getPoolType() != null && !criteria.getPoolType().isEmpty()) {
            spec = spec.and(poolTypeIn(criteria.getPoolType()));
        }
        if (criteria.getLeaseSecondsMin() != null) {
            spec = spec.and(leaseSecondsMin(criteria.getLeaseSecondsMin()));
        }
        if (criteria.getLeaseSecondsMax() != null) {
            spec = spec.and(leaseSecondsMax(criteria.getLeaseSecondsMax()));
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
        if (hasText(criteria.getSourceSystem())) {
            spec = spec.and(sourceSystemContains(criteria.getSourceSystem().trim()));
        }
        return spec;
    }
}
