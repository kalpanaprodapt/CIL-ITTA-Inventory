package com.uniti.cil.itta.inventory.capacity.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.ThresholdPolicySearchCriteria;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ThresholdPolicy search. */
public final class ThresholdPolicySpecifications {

    private ThresholdPolicySpecifications() {
    }

    public static Specification<ThresholdPolicy> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ThresholdPolicy> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ThresholdPolicy> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ThresholdPolicy> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ThresholdPolicy> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ThresholdPolicy> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ThresholdPolicy> policyNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("policyName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ThresholdPolicy> metricContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("metric")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ThresholdPolicy> warnPctMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("warnPct"), value);
    }

    public static Specification<ThresholdPolicy> warnPctMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("warnPct"), value);
    }

    public static Specification<ThresholdPolicy> critPctMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("critPct"), value);
    }

    public static Specification<ThresholdPolicy> critPctMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("critPct"), value);
    }

    public static Specification<ThresholdPolicy> evaluationWindowMinutesMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("evaluationWindowMinutes"), value);
    }

    public static Specification<ThresholdPolicy> evaluationWindowMinutesMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("evaluationWindowMinutes"), value);
    }

    public static Specification<ThresholdPolicy> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ThresholdPolicy> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ThresholdPolicy> criticalityIn(List<ThresholdPolicyCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<ThresholdPolicy> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ThresholdPolicy> fromCriteria(String tenantId, ThresholdPolicySearchCriteria criteria) {
        Specification<ThresholdPolicy> spec = tenant(tenantId);
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
        if (hasText(criteria.getMetric())) {
            spec = spec.and(metricContains(criteria.getMetric().trim()));
        }
        if (criteria.getWarnPctMin() != null) {
            spec = spec.and(warnPctMin(criteria.getWarnPctMin()));
        }
        if (criteria.getWarnPctMax() != null) {
            spec = spec.and(warnPctMax(criteria.getWarnPctMax()));
        }
        if (criteria.getCritPctMin() != null) {
            spec = spec.and(critPctMin(criteria.getCritPctMin()));
        }
        if (criteria.getCritPctMax() != null) {
            spec = spec.and(critPctMax(criteria.getCritPctMax()));
        }
        if (criteria.getEvaluationWindowMinutesMin() != null) {
            spec = spec.and(evaluationWindowMinutesMin(criteria.getEvaluationWindowMinutesMin()));
        }
        if (criteria.getEvaluationWindowMinutesMax() != null) {
            spec = spec.and(evaluationWindowMinutesMax(criteria.getEvaluationWindowMinutesMax()));
        }
        if (hasText(criteria.getRegion())) {
            spec = spec.and(regionContains(criteria.getRegion().trim()));
        }
        if (hasText(criteria.getOwner())) {
            spec = spec.and(ownerContains(criteria.getOwner().trim()));
        }
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        if (hasText(criteria.getCostCenter())) {
            spec = spec.and(costCenterContains(criteria.getCostCenter().trim()));
        }
        return spec;
    }
}
