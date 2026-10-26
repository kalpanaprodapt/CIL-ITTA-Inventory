package com.uniti.cil.itta.inventory.facility.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.PowerFeedSearchCriteria;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing PowerFeed search. */
public final class PowerFeedSpecifications {

    private PowerFeedSpecifications() {
    }

    public static Specification<PowerFeed> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<PowerFeed> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<PowerFeed> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<PowerFeed> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerFeed> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerFeed> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerFeed> roomIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("roomId"), value);
    }

    public static Specification<PowerFeed> feedTypeIn(List<PowerFeedFeedType> values) {
        return (root, query, cb) -> root.get("feedType").in(values);
    }

    public static Specification<PowerFeed> voltageMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("voltage"), value);
    }

    public static Specification<PowerFeed> voltageMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("voltage"), value);
    }

    public static Specification<PowerFeed> amperageMin(BigDecimal value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<BigDecimal>get("amperage"), value);
    }

    public static Specification<PowerFeed> amperageMax(BigDecimal value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<BigDecimal>get("amperage"), value);
    }

    public static Specification<PowerFeed> phaseContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("phase")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerFeed> breakerLabelContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("breakerLabel")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerFeed> redundancyGroupContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("redundancyGroup")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerFeed> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerFeed> criticalityIn(List<PowerFeedCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<PowerFeed> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<PowerFeed> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<PowerFeed> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<PowerFeed> fromCriteria(String tenantId, PowerFeedSearchCriteria criteria) {
        Specification<PowerFeed> spec = tenant(tenantId);
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
        if (criteria.getRoomId() != null) {
            spec = spec.and(roomIdEquals(criteria.getRoomId()));
        }
        if (criteria.getFeedType() != null && !criteria.getFeedType().isEmpty()) {
            spec = spec.and(feedTypeIn(criteria.getFeedType()));
        }
        if (criteria.getVoltageMin() != null) {
            spec = spec.and(voltageMin(criteria.getVoltageMin()));
        }
        if (criteria.getVoltageMax() != null) {
            spec = spec.and(voltageMax(criteria.getVoltageMax()));
        }
        if (criteria.getAmperageMin() != null) {
            spec = spec.and(amperageMin(criteria.getAmperageMin()));
        }
        if (criteria.getAmperageMax() != null) {
            spec = spec.and(amperageMax(criteria.getAmperageMax()));
        }
        if (hasText(criteria.getPhase())) {
            spec = spec.and(phaseContains(criteria.getPhase().trim()));
        }
        if (hasText(criteria.getBreakerLabel())) {
            spec = spec.and(breakerLabelContains(criteria.getBreakerLabel().trim()));
        }
        if (hasText(criteria.getRedundancyGroup())) {
            spec = spec.and(redundancyGroupContains(criteria.getRedundancyGroup().trim()));
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
