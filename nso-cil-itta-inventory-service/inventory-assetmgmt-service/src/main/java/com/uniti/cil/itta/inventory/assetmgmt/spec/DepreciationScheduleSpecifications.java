package com.uniti.cil.itta.inventory.assetmgmt.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.DepreciationScheduleSearchCriteria;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing DepreciationSchedule search. */
public final class DepreciationScheduleSpecifications {

    private DepreciationScheduleSpecifications() {
    }

    public static Specification<DepreciationSchedule> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<DepreciationSchedule> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<DepreciationSchedule> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<DepreciationSchedule> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DepreciationSchedule> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DepreciationSchedule> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DepreciationSchedule> assetIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("assetId"), value);
    }

    public static Specification<DepreciationSchedule> methodIn(List<DepreciationScheduleMethod> values) {
        return (root, query, cb) -> root.get("method").in(values);
    }

    public static Specification<DepreciationSchedule> usefulLifeMonthsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("usefulLifeMonths"), value);
    }

    public static Specification<DepreciationSchedule> usefulLifeMonthsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("usefulLifeMonths"), value);
    }

    public static Specification<DepreciationSchedule> salvageValueMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("salvageValue"), value);
    }

    public static Specification<DepreciationSchedule> salvageValueMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("salvageValue"), value);
    }

    public static Specification<DepreciationSchedule> startDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("startDate"), value);
    }

    public static Specification<DepreciationSchedule> startDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("startDate"), value);
    }

    public static Specification<DepreciationSchedule> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<DepreciationSchedule> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<DepreciationSchedule> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<DepreciationSchedule> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<DepreciationSchedule> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DepreciationSchedule> lifecycleStageIn(List<DepreciationScheduleLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<DepreciationSchedule> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<DepreciationSchedule> fromCriteria(String tenantId, DepreciationScheduleSearchCriteria criteria) {
        Specification<DepreciationSchedule> spec = tenant(tenantId);
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
        if (criteria.getAssetId() != null) {
            spec = spec.and(assetIdEquals(criteria.getAssetId()));
        }
        if (criteria.getMethod() != null && !criteria.getMethod().isEmpty()) {
            spec = spec.and(methodIn(criteria.getMethod()));
        }
        if (criteria.getUsefulLifeMonthsMin() != null) {
            spec = spec.and(usefulLifeMonthsMin(criteria.getUsefulLifeMonthsMin()));
        }
        if (criteria.getUsefulLifeMonthsMax() != null) {
            spec = spec.and(usefulLifeMonthsMax(criteria.getUsefulLifeMonthsMax()));
        }
        if (criteria.getSalvageValueMin() != null) {
            spec = spec.and(salvageValueMin(criteria.getSalvageValueMin()));
        }
        if (criteria.getSalvageValueMax() != null) {
            spec = spec.and(salvageValueMax(criteria.getSalvageValueMax()));
        }
        if (criteria.getStartDateFrom() != null) {
            spec = spec.and(startDateFrom(criteria.getStartDateFrom()));
        }
        if (criteria.getStartDateTo() != null) {
            spec = spec.and(startDateTo(criteria.getStartDateTo()));
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
