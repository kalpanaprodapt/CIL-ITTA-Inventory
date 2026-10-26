package com.uniti.cil.itta.inventory.customer.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.SlaProfileSearchCriteria;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing SlaProfile search. */
public final class SlaProfileSpecifications {

    private SlaProfileSpecifications() {
    }

    public static Specification<SlaProfile> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<SlaProfile> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<SlaProfile> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<SlaProfile> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SlaProfile> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SlaProfile> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SlaProfile> profileNameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("profileName")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SlaProfile> availabilityPctMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("availabilityPct"), value);
    }

    public static Specification<SlaProfile> availabilityPctMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("availabilityPct"), value);
    }

    public static Specification<SlaProfile> mttrMinutesMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("mttrMinutes"), value);
    }

    public static Specification<SlaProfile> mttrMinutesMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("mttrMinutes"), value);
    }

    public static Specification<SlaProfile> latencyMsMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("latencyMs"), value);
    }

    public static Specification<SlaProfile> latencyMsMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("latencyMs"), value);
    }

    public static Specification<SlaProfile> jitterMsMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("jitterMs"), value);
    }

    public static Specification<SlaProfile> jitterMsMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("jitterMs"), value);
    }

    public static Specification<SlaProfile> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SlaProfile> criticalityIn(List<SlaProfileCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<SlaProfile> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SlaProfile> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<SlaProfile> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<SlaProfile> fromCriteria(String tenantId, SlaProfileSearchCriteria criteria) {
        Specification<SlaProfile> spec = tenant(tenantId);
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
        if (hasText(criteria.getProfileName())) {
            spec = spec.and(profileNameContains(criteria.getProfileName().trim()));
        }
        if (criteria.getAvailabilityPctMin() != null) {
            spec = spec.and(availabilityPctMin(criteria.getAvailabilityPctMin()));
        }
        if (criteria.getAvailabilityPctMax() != null) {
            spec = spec.and(availabilityPctMax(criteria.getAvailabilityPctMax()));
        }
        if (criteria.getMttrMinutesMin() != null) {
            spec = spec.and(mttrMinutesMin(criteria.getMttrMinutesMin()));
        }
        if (criteria.getMttrMinutesMax() != null) {
            spec = spec.and(mttrMinutesMax(criteria.getMttrMinutesMax()));
        }
        if (criteria.getLatencyMsMin() != null) {
            spec = spec.and(latencyMsMin(criteria.getLatencyMsMin()));
        }
        if (criteria.getLatencyMsMax() != null) {
            spec = spec.and(latencyMsMax(criteria.getLatencyMsMax()));
        }
        if (criteria.getJitterMsMin() != null) {
            spec = spec.and(jitterMsMin(criteria.getJitterMsMin()));
        }
        if (criteria.getJitterMsMax() != null) {
            spec = spec.and(jitterMsMax(criteria.getJitterMsMax()));
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
        if (criteria.getEffectiveFromFrom() != null) {
            spec = spec.and(effectiveFromFrom(criteria.getEffectiveFromFrom()));
        }
        if (criteria.getEffectiveFromTo() != null) {
            spec = spec.and(effectiveFromTo(criteria.getEffectiveFromTo()));
        }
        return spec;
    }
}
