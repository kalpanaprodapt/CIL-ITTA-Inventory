package com.uniti.cil.itta.inventory.switching.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.RouteTargetSearchCriteria;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing RouteTarget search. */
public final class RouteTargetSpecifications {

    private RouteTargetSpecifications() {
    }

    public static Specification<RouteTarget> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<RouteTarget> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<RouteTarget> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<RouteTarget> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RouteTarget> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RouteTarget> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RouteTarget> asnMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("asn"), value);
    }

    public static Specification<RouteTarget> asnMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("asn"), value);
    }

    public static Specification<RouteTarget> assignedNumberMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("assignedNumber"), value);
    }

    public static Specification<RouteTarget> assignedNumberMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("assignedNumber"), value);
    }

    public static Specification<RouteTarget> rtTypeIn(List<RouteTargetRtType> values) {
        return (root, query, cb) -> root.get("rtType").in(values);
    }

    public static Specification<RouteTarget> vrfIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("vrfId"), value);
    }

    public static Specification<RouteTarget> lifecycleStageIn(List<RouteTargetLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<RouteTarget> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RouteTarget> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RouteTarget> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<RouteTarget> criticalityIn(List<RouteTargetCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<RouteTarget> fromCriteria(String tenantId, RouteTargetSearchCriteria criteria) {
        Specification<RouteTarget> spec = tenant(tenantId);
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
        if (criteria.getAsnMin() != null) {
            spec = spec.and(asnMin(criteria.getAsnMin()));
        }
        if (criteria.getAsnMax() != null) {
            spec = spec.and(asnMax(criteria.getAsnMax()));
        }
        if (criteria.getAssignedNumberMin() != null) {
            spec = spec.and(assignedNumberMin(criteria.getAssignedNumberMin()));
        }
        if (criteria.getAssignedNumberMax() != null) {
            spec = spec.and(assignedNumberMax(criteria.getAssignedNumberMax()));
        }
        if (criteria.getRtType() != null && !criteria.getRtType().isEmpty()) {
            spec = spec.and(rtTypeIn(criteria.getRtType()));
        }
        if (criteria.getVrfId() != null) {
            spec = spec.and(vrfIdEquals(criteria.getVrfId()));
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
