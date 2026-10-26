package com.uniti.cil.itta.inventory.change.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.ChangeRequestSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing ChangeRequest search. */
public final class ChangeRequestSpecifications {

    private ChangeRequestSpecifications() {
    }

    public static Specification<ChangeRequest> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<ChangeRequest> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<ChangeRequest> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<ChangeRequest> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ChangeRequest> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ChangeRequest> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ChangeRequest> changeNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("changeNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ChangeRequest> changeTypeIn(List<ChangeRequestChangeType> values) {
        return (root, query, cb) -> root.get("changeType").in(values);
    }

    public static Specification<ChangeRequest> riskLevelIn(List<ChangeRequestRiskLevel> values) {
        return (root, query, cb) -> root.get("riskLevel").in(values);
    }

    public static Specification<ChangeRequest> plannedStartFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("plannedStart"), value);
    }

    public static Specification<ChangeRequest> plannedStartTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("plannedStart"), value);
    }

    public static Specification<ChangeRequest> plannedEndFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("plannedEnd"), value);
    }

    public static Specification<ChangeRequest> plannedEndTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("plannedEnd"), value);
    }

    public static Specification<ChangeRequest> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ChangeRequest> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<ChangeRequest> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ChangeRequest> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<ChangeRequest> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<ChangeRequest> lifecycleStageIn(List<ChangeRequestLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<ChangeRequest> fromCriteria(String tenantId, ChangeRequestSearchCriteria criteria) {
        Specification<ChangeRequest> spec = tenant(tenantId);
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
        if (hasText(criteria.getChangeNumber())) {
            spec = spec.and(changeNumberContains(criteria.getChangeNumber().trim()));
        }
        if (criteria.getChangeType() != null && !criteria.getChangeType().isEmpty()) {
            spec = spec.and(changeTypeIn(criteria.getChangeType()));
        }
        if (criteria.getRiskLevel() != null && !criteria.getRiskLevel().isEmpty()) {
            spec = spec.and(riskLevelIn(criteria.getRiskLevel()));
        }
        if (criteria.getPlannedStartFrom() != null) {
            spec = spec.and(plannedStartFrom(criteria.getPlannedStartFrom()));
        }
        if (criteria.getPlannedStartTo() != null) {
            spec = spec.and(plannedStartTo(criteria.getPlannedStartTo()));
        }
        if (criteria.getPlannedEndFrom() != null) {
            spec = spec.and(plannedEndFrom(criteria.getPlannedEndFrom()));
        }
        if (criteria.getPlannedEndTo() != null) {
            spec = spec.and(plannedEndTo(criteria.getPlannedEndTo()));
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
