package com.uniti.cil.itta.inventory.circuit.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitOrderSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing CircuitOrder search. */
public final class CircuitOrderSpecifications {

    private CircuitOrderSpecifications() {
    }

    public static Specification<CircuitOrder> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<CircuitOrder> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<CircuitOrder> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<CircuitOrder> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitOrder> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitOrder> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitOrder> circuitIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("circuitId"), value);
    }

    public static Specification<CircuitOrder> orderNumberContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("orderNumber")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitOrder> orderedOnFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("orderedOn"), value);
    }

    public static Specification<CircuitOrder> orderedOnTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("orderedOn"), value);
    }

    public static Specification<CircuitOrder> requestedDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("requestedDate"), value);
    }

    public static Specification<CircuitOrder> requestedDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("requestedDate"), value);
    }

    public static Specification<CircuitOrder> committedDateFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("committedDate"), value);
    }

    public static Specification<CircuitOrder> committedDateTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("committedDate"), value);
    }

    public static Specification<CircuitOrder> orderStatusIn(List<CircuitOrderOrderStatus> values) {
        return (root, query, cb) -> root.get("orderStatus").in(values);
    }

    public static Specification<CircuitOrder> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<CircuitOrder> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<CircuitOrder> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<CircuitOrder> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<CircuitOrder> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitOrder> lifecycleStageIn(List<CircuitOrderLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<CircuitOrder> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<CircuitOrder> fromCriteria(String tenantId, CircuitOrderSearchCriteria criteria) {
        Specification<CircuitOrder> spec = tenant(tenantId);
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
        if (criteria.getCircuitId() != null) {
            spec = spec.and(circuitIdEquals(criteria.getCircuitId()));
        }
        if (hasText(criteria.getOrderNumber())) {
            spec = spec.and(orderNumberContains(criteria.getOrderNumber().trim()));
        }
        if (criteria.getOrderedOnFrom() != null) {
            spec = spec.and(orderedOnFrom(criteria.getOrderedOnFrom()));
        }
        if (criteria.getOrderedOnTo() != null) {
            spec = spec.and(orderedOnTo(criteria.getOrderedOnTo()));
        }
        if (criteria.getRequestedDateFrom() != null) {
            spec = spec.and(requestedDateFrom(criteria.getRequestedDateFrom()));
        }
        if (criteria.getRequestedDateTo() != null) {
            spec = spec.and(requestedDateTo(criteria.getRequestedDateTo()));
        }
        if (criteria.getCommittedDateFrom() != null) {
            spec = spec.and(committedDateFrom(criteria.getCommittedDateFrom()));
        }
        if (criteria.getCommittedDateTo() != null) {
            spec = spec.and(committedDateTo(criteria.getCommittedDateTo()));
        }
        if (criteria.getOrderStatus() != null && !criteria.getOrderStatus().isEmpty()) {
            spec = spec.and(orderStatusIn(criteria.getOrderStatus()));
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
