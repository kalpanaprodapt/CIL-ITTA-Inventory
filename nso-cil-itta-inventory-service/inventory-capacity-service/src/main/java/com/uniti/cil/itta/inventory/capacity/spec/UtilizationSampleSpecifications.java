package com.uniti.cil.itta.inventory.capacity.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.UtilizationSampleSearchCriteria;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing UtilizationSample search. */
public final class UtilizationSampleSpecifications {

    private UtilizationSampleSpecifications() {
    }

    public static Specification<UtilizationSample> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<UtilizationSample> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<UtilizationSample> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<UtilizationSample> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<UtilizationSample> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<UtilizationSample> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<UtilizationSample> targetRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("targetRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<UtilizationSample> metricContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("metric")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<UtilizationSample> sampledAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("sampledAt"), value);
    }

    public static Specification<UtilizationSample> sampledAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("sampledAt"), value);
    }

    public static Specification<UtilizationSample> valuePctMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("valuePct"), value);
    }

    public static Specification<UtilizationSample> valuePctMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("valuePct"), value);
    }

    public static Specification<UtilizationSample> peakEquals(Boolean value) {
        return (root, query, cb) -> cb.equal(root.get("peak"), value);
    }

    public static Specification<UtilizationSample> lifecycleStageIn(List<UtilizationSampleLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<UtilizationSample> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<UtilizationSample> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<UtilizationSample> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<UtilizationSample> criticalityIn(List<UtilizationSampleCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<UtilizationSample> fromCriteria(String tenantId, UtilizationSampleSearchCriteria criteria) {
        Specification<UtilizationSample> spec = tenant(tenantId);
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
        if (hasText(criteria.getTargetRef())) {
            spec = spec.and(targetRefContains(criteria.getTargetRef().trim()));
        }
        if (hasText(criteria.getMetric())) {
            spec = spec.and(metricContains(criteria.getMetric().trim()));
        }
        if (criteria.getSampledAtFrom() != null) {
            spec = spec.and(sampledAtFrom(criteria.getSampledAtFrom()));
        }
        if (criteria.getSampledAtTo() != null) {
            spec = spec.and(sampledAtTo(criteria.getSampledAtTo()));
        }
        if (criteria.getValuePctMin() != null) {
            spec = spec.and(valuePctMin(criteria.getValuePctMin()));
        }
        if (criteria.getValuePctMax() != null) {
            spec = spec.and(valuePctMax(criteria.getValuePctMax()));
        }
        if (criteria.getPeak() != null) {
            spec = spec.and(peakEquals(criteria.getPeak()));
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
        if (criteria.getCriticality() != null && !criteria.getCriticality().isEmpty()) {
            spec = spec.and(criticalityIn(criteria.getCriticality()));
        }
        return spec;
    }
}
