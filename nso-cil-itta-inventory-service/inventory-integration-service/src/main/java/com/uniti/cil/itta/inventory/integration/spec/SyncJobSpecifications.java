package com.uniti.cil.itta.inventory.integration.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.SyncJobSearchCriteria;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing SyncJob search. */
public final class SyncJobSpecifications {

    private SyncJobSpecifications() {
    }

    public static Specification<SyncJob> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<SyncJob> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<SyncJob> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<SyncJob> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncJob> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncJob> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncJob> endpointIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("endpointId"), value);
    }

    public static Specification<SyncJob> directionIn(List<SyncJobDirection> values) {
        return (root, query, cb) -> root.get("direction").in(values);
    }

    public static Specification<SyncJob> scheduleCronContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("scheduleCron")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncJob> lastRunAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("lastRunAt"), value);
    }

    public static Specification<SyncJob> lastRunAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("lastRunAt"), value);
    }

    public static Specification<SyncJob> lastStatusIn(List<SyncJobLastStatus> values) {
        return (root, query, cb) -> root.get("lastStatus").in(values);
    }

    public static Specification<SyncJob> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncJob> criticalityIn(List<SyncJobCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<SyncJob> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<SyncJob> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<SyncJob> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<SyncJob> fromCriteria(String tenantId, SyncJobSearchCriteria criteria) {
        Specification<SyncJob> spec = tenant(tenantId);
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
        if (criteria.getEndpointId() != null) {
            spec = spec.and(endpointIdEquals(criteria.getEndpointId()));
        }
        if (criteria.getDirection() != null && !criteria.getDirection().isEmpty()) {
            spec = spec.and(directionIn(criteria.getDirection()));
        }
        if (hasText(criteria.getScheduleCron())) {
            spec = spec.and(scheduleCronContains(criteria.getScheduleCron().trim()));
        }
        if (criteria.getLastRunAtFrom() != null) {
            spec = spec.and(lastRunAtFrom(criteria.getLastRunAtFrom()));
        }
        if (criteria.getLastRunAtTo() != null) {
            spec = spec.and(lastRunAtTo(criteria.getLastRunAtTo()));
        }
        if (criteria.getLastStatus() != null && !criteria.getLastStatus().isEmpty()) {
            spec = spec.and(lastStatusIn(criteria.getLastStatus()));
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
