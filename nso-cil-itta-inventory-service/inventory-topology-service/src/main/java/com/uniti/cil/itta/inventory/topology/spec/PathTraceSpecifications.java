package com.uniti.cil.itta.inventory.topology.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.PathTraceSearchCriteria;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing PathTrace search. */
public final class PathTraceSpecifications {

    private PathTraceSpecifications() {
    }

    public static Specification<PathTrace> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<PathTrace> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<PathTrace> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<PathTrace> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PathTrace> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PathTrace> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PathTrace> sourceRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PathTrace> targetRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("targetRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PathTrace> hopCountMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("hopCount"), value);
    }

    public static Specification<PathTrace> hopCountMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("hopCount"), value);
    }

    public static Specification<PathTrace> totalLatencyMsMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("totalLatencyMs"), value);
    }

    public static Specification<PathTrace> totalLatencyMsMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("totalLatencyMs"), value);
    }

    public static Specification<PathTrace> traceMethodContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("traceMethod")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PathTrace> tracedAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("tracedAt"), value);
    }

    public static Specification<PathTrace> tracedAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("tracedAt"), value);
    }

    public static Specification<PathTrace> lifecycleStageIn(List<PathTraceLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<PathTrace> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PathTrace> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PathTrace> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PathTrace> criticalityIn(List<PathTraceCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<PathTrace> fromCriteria(String tenantId, PathTraceSearchCriteria criteria) {
        Specification<PathTrace> spec = tenant(tenantId);
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
        if (hasText(criteria.getSourceRef())) {
            spec = spec.and(sourceRefContains(criteria.getSourceRef().trim()));
        }
        if (hasText(criteria.getTargetRef())) {
            spec = spec.and(targetRefContains(criteria.getTargetRef().trim()));
        }
        if (criteria.getHopCountMin() != null) {
            spec = spec.and(hopCountMin(criteria.getHopCountMin()));
        }
        if (criteria.getHopCountMax() != null) {
            spec = spec.and(hopCountMax(criteria.getHopCountMax()));
        }
        if (criteria.getTotalLatencyMsMin() != null) {
            spec = spec.and(totalLatencyMsMin(criteria.getTotalLatencyMsMin()));
        }
        if (criteria.getTotalLatencyMsMax() != null) {
            spec = spec.and(totalLatencyMsMax(criteria.getTotalLatencyMsMax()));
        }
        if (hasText(criteria.getTraceMethod())) {
            spec = spec.and(traceMethodContains(criteria.getTraceMethod().trim()));
        }
        if (criteria.getTracedAtFrom() != null) {
            spec = spec.and(tracedAtFrom(criteria.getTracedAtFrom()));
        }
        if (criteria.getTracedAtTo() != null) {
            spec = spec.and(tracedAtTo(criteria.getTracedAtTo()));
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
