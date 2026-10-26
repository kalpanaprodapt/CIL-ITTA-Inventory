package com.uniti.cil.itta.inventory.cabling.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.CableSearchCriteria;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Cable search. */
public final class CableSpecifications {

    private CableSpecifications() {
    }

    public static Specification<Cable> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Cable> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Cable> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Cable> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Cable> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Cable> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Cable> cableTypeIn(List<CableCableType> values) {
        return (root, query, cb) -> root.get("cableType").in(values);
    }

    public static Specification<Cable> lengthMMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("lengthM"), value);
    }

    public static Specification<Cable> lengthMMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("lengthM"), value);
    }

    public static Specification<Cable> aEndPortIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("aEndPortId"), value);
    }

    public static Specification<Cable> zEndPortIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("zEndPortId"), value);
    }

    public static Specification<Cable> colorContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("color")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Cable> labelContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("label")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Cable> criticalityIn(List<CableCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<Cable> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Cable> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Cable> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<Cable> effectiveToFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    public static Specification<Cable> effectiveToTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveTo"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Cable> fromCriteria(String tenantId, CableSearchCriteria criteria) {
        Specification<Cable> spec = tenant(tenantId);
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
        if (criteria.getCableType() != null && !criteria.getCableType().isEmpty()) {
            spec = spec.and(cableTypeIn(criteria.getCableType()));
        }
        if (criteria.getLengthMMin() != null) {
            spec = spec.and(lengthMMin(criteria.getLengthMMin()));
        }
        if (criteria.getLengthMMax() != null) {
            spec = spec.and(lengthMMax(criteria.getLengthMMax()));
        }
        if (criteria.getAEndPortId() != null) {
            spec = spec.and(aEndPortIdEquals(criteria.getAEndPortId()));
        }
        if (criteria.getZEndPortId() != null) {
            spec = spec.and(zEndPortIdEquals(criteria.getZEndPortId()));
        }
        if (hasText(criteria.getColor())) {
            spec = spec.and(colorContains(criteria.getColor().trim()));
        }
        if (hasText(criteria.getLabel())) {
            spec = spec.and(labelContains(criteria.getLabel().trim()));
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
        if (criteria.getEffectiveToFrom() != null) {
            spec = spec.and(effectiveToFrom(criteria.getEffectiveToFrom()));
        }
        if (criteria.getEffectiveToTo() != null) {
            spec = spec.and(effectiveToTo(criteria.getEffectiveToTo()));
        }
        return spec;
    }
}
