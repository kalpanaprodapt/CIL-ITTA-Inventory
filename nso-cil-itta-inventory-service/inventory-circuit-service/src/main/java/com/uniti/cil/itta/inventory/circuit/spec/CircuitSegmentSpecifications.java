package com.uniti.cil.itta.inventory.circuit.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitSegmentSearchCriteria;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing CircuitSegment search. */
public final class CircuitSegmentSpecifications {

    private CircuitSegmentSpecifications() {
    }

    public static Specification<CircuitSegment> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<CircuitSegment> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<CircuitSegment> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<CircuitSegment> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitSegment> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitSegment> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitSegment> circuitIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("circuitId"), value);
    }

    public static Specification<CircuitSegment> segmentOrderMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("segmentOrder"), value);
    }

    public static Specification<CircuitSegment> segmentOrderMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("segmentOrder"), value);
    }

    public static Specification<CircuitSegment> aTerminationIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("aTerminationId"), value);
    }

    public static Specification<CircuitSegment> zTerminationIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("zTerminationId"), value);
    }

    public static Specification<CircuitSegment> latencyMsMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("latencyMs"), value);
    }

    public static Specification<CircuitSegment> latencyMsMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("latencyMs"), value);
    }

    public static Specification<CircuitSegment> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitSegment> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitSegment> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<CircuitSegment> criticalityIn(List<CircuitSegmentCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<CircuitSegment> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<CircuitSegment> fromCriteria(String tenantId, CircuitSegmentSearchCriteria criteria) {
        Specification<CircuitSegment> spec = tenant(tenantId);
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
        if (criteria.getSegmentOrderMin() != null) {
            spec = spec.and(segmentOrderMin(criteria.getSegmentOrderMin()));
        }
        if (criteria.getSegmentOrderMax() != null) {
            spec = spec.and(segmentOrderMax(criteria.getSegmentOrderMax()));
        }
        if (criteria.getATerminationId() != null) {
            spec = spec.and(aTerminationIdEquals(criteria.getATerminationId()));
        }
        if (criteria.getZTerminationId() != null) {
            spec = spec.and(zTerminationIdEquals(criteria.getZTerminationId()));
        }
        if (criteria.getLatencyMsMin() != null) {
            spec = spec.and(latencyMsMin(criteria.getLatencyMsMin()));
        }
        if (criteria.getLatencyMsMax() != null) {
            spec = spec.and(latencyMsMax(criteria.getLatencyMsMax()));
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
        if (hasText(criteria.getCostCenter())) {
            spec = spec.and(costCenterContains(criteria.getCostCenter().trim()));
        }
        return spec;
    }
}
