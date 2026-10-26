package com.uniti.cil.itta.inventory.change.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.FieldTaskSearchCriteria;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing FieldTask search. */
public final class FieldTaskSpecifications {

    private FieldTaskSpecifications() {
    }

    public static Specification<FieldTask> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<FieldTask> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<FieldTask> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<FieldTask> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FieldTask> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FieldTask> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FieldTask> workOrderIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("workOrderId"), value);
    }

    public static Specification<FieldTask> taskNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("taskName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FieldTask> sequenceMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("sequence"), value);
    }

    public static Specification<FieldTask> sequenceMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("sequence"), value);
    }

    public static Specification<FieldTask> durationMinutesMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("durationMinutes"), value);
    }

    public static Specification<FieldTask> durationMinutesMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("durationMinutes"), value);
    }

    public static Specification<FieldTask> completedOnFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("completedOn"), value);
    }

    public static Specification<FieldTask> completedOnTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("completedOn"), value);
    }

    public static Specification<FieldTask> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FieldTask> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<FieldTask> criticalityIn(List<FieldTaskCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<FieldTask> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<FieldTask> fromCriteria(String tenantId, FieldTaskSearchCriteria criteria) {
        Specification<FieldTask> spec = tenant(tenantId);
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
        if (criteria.getWorkOrderId() != null) {
            spec = spec.and(workOrderIdEquals(criteria.getWorkOrderId()));
        }
        if (hasText(criteria.getTaskName())) {
            spec = spec.and(taskNameContains(criteria.getTaskName().trim()));
        }
        if (criteria.getSequenceMin() != null) {
            spec = spec.and(sequenceMin(criteria.getSequenceMin()));
        }
        if (criteria.getSequenceMax() != null) {
            spec = spec.and(sequenceMax(criteria.getSequenceMax()));
        }
        if (criteria.getDurationMinutesMin() != null) {
            spec = spec.and(durationMinutesMin(criteria.getDurationMinutesMin()));
        }
        if (criteria.getDurationMinutesMax() != null) {
            spec = spec.and(durationMinutesMax(criteria.getDurationMinutesMax()));
        }
        if (criteria.getCompletedOnFrom() != null) {
            spec = spec.and(completedOnFrom(criteria.getCompletedOnFrom()));
        }
        if (criteria.getCompletedOnTo() != null) {
            spec = spec.and(completedOnTo(criteria.getCompletedOnTo()));
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
