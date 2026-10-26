package com.uniti.cil.itta.inventory.circuit.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitTerminationSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing CircuitTermination search. */
public final class CircuitTerminationSpecifications {

    private CircuitTerminationSpecifications() {
    }

    public static Specification<CircuitTermination> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<CircuitTermination> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<CircuitTermination> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<CircuitTermination> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitTermination> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitTermination> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitTermination> circuitIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("circuitId"), value);
    }

    public static Specification<CircuitTermination> sideIn(List<CircuitTerminationSide> values) {
        return (root, query, cb) -> root.get("side").in(values);
    }

    public static Specification<CircuitTermination> portIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("portId"), value);
    }

    public static Specification<CircuitTermination> xconnectIdContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("xconnectId")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitTermination> patchInfoContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("patchInfo")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitTermination> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitTermination> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<CircuitTermination> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<CircuitTermination> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<CircuitTermination> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<CircuitTermination> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<CircuitTermination> fromCriteria(String tenantId, CircuitTerminationSearchCriteria criteria) {
        Specification<CircuitTermination> spec = tenant(tenantId);
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
        if (criteria.getSide() != null && !criteria.getSide().isEmpty()) {
            spec = spec.and(sideIn(criteria.getSide()));
        }
        if (criteria.getPortId() != null) {
            spec = spec.and(portIdEquals(criteria.getPortId()));
        }
        if (hasText(criteria.getXconnectId())) {
            spec = spec.and(xconnectIdContains(criteria.getXconnectId().trim()));
        }
        if (hasText(criteria.getPatchInfo())) {
            spec = spec.and(patchInfoContains(criteria.getPatchInfo().trim()));
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
