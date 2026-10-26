package com.uniti.cil.itta.inventory.radio.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.BackhaulLinkSearchCriteria;
import com.uniti.cil.itta.inventory.radio.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing BackhaulLink search. */
public final class BackhaulLinkSpecifications {

    private BackhaulLinkSpecifications() {
    }

    public static Specification<BackhaulLink> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<BackhaulLink> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<BackhaulLink> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<BackhaulLink> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BackhaulLink> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BackhaulLink> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BackhaulLink> baseStationIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("baseStationId"), value);
    }

    public static Specification<BackhaulLink> linkMediumIn(List<BackhaulLinkLinkMedium> values) {
        return (root, query, cb) -> root.get("linkMedium").in(values);
    }

    public static Specification<BackhaulLink> capacityMbpsMin(Long value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Long>get("capacityMbps"), value);
    }

    public static Specification<BackhaulLink> capacityMbpsMax(Long value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Long>get("capacityMbps"), value);
    }

    public static Specification<BackhaulLink> latencyMsMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("latencyMs"), value);
    }

    public static Specification<BackhaulLink> latencyMsMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("latencyMs"), value);
    }

    public static Specification<BackhaulLink> circuitRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("circuitRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BackhaulLink> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<BackhaulLink> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<BackhaulLink> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<BackhaulLink> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<BackhaulLink> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<BackhaulLink> labelsContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("labels")), "%" + value.toLowerCase() + "%");
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<BackhaulLink> fromCriteria(String tenantId, BackhaulLinkSearchCriteria criteria) {
        Specification<BackhaulLink> spec = tenant(tenantId);
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
        if (criteria.getBaseStationId() != null) {
            spec = spec.and(baseStationIdEquals(criteria.getBaseStationId()));
        }
        if (criteria.getLinkMedium() != null && !criteria.getLinkMedium().isEmpty()) {
            spec = spec.and(linkMediumIn(criteria.getLinkMedium()));
        }
        if (criteria.getCapacityMbpsMin() != null) {
            spec = spec.and(capacityMbpsMin(criteria.getCapacityMbpsMin()));
        }
        if (criteria.getCapacityMbpsMax() != null) {
            spec = spec.and(capacityMbpsMax(criteria.getCapacityMbpsMax()));
        }
        if (criteria.getLatencyMsMin() != null) {
            spec = spec.and(latencyMsMin(criteria.getLatencyMsMin()));
        }
        if (criteria.getLatencyMsMax() != null) {
            spec = spec.and(latencyMsMax(criteria.getLatencyMsMax()));
        }
        if (hasText(criteria.getCircuitRef())) {
            spec = spec.and(circuitRefContains(criteria.getCircuitRef().trim()));
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
