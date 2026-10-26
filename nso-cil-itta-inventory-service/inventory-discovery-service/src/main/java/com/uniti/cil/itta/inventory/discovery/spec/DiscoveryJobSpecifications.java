package com.uniti.cil.itta.inventory.discovery.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.DiscoveryJobSearchCriteria;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing DiscoveryJob search. */
public final class DiscoveryJobSpecifications {

    private DiscoveryJobSpecifications() {
    }

    public static Specification<DiscoveryJob> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<DiscoveryJob> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<DiscoveryJob> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<DiscoveryJob> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryJob> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryJob> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryJob> profileIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("profileId"), value);
    }

    public static Specification<DiscoveryJob> startedAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("startedAt"), value);
    }

    public static Specification<DiscoveryJob> startedAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("startedAt"), value);
    }

    public static Specification<DiscoveryJob> finishedAtFrom(Instant value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Instant>get("finishedAt"), value);
    }

    public static Specification<DiscoveryJob> finishedAtTo(Instant value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Instant>get("finishedAt"), value);
    }

    public static Specification<DiscoveryJob> jobStateIn(List<DiscoveryJobJobState> values) {
        return (root, query, cb) -> root.get("jobState").in(values);
    }

    public static Specification<DiscoveryJob> assetsFoundMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("assetsFound"), value);
    }

    public static Specification<DiscoveryJob> assetsFoundMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("assetsFound"), value);
    }

    public static Specification<DiscoveryJob> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryJob> criticalityIn(List<DiscoveryJobCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    public static Specification<DiscoveryJob> costCenterContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("costCenter")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<DiscoveryJob> effectiveFromFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    public static Specification<DiscoveryJob> effectiveFromTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("effectiveFrom"), value);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<DiscoveryJob> fromCriteria(String tenantId, DiscoveryJobSearchCriteria criteria) {
        Specification<DiscoveryJob> spec = tenant(tenantId);
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
        if (criteria.getProfileId() != null) {
            spec = spec.and(profileIdEquals(criteria.getProfileId()));
        }
        if (criteria.getStartedAtFrom() != null) {
            spec = spec.and(startedAtFrom(criteria.getStartedAtFrom()));
        }
        if (criteria.getStartedAtTo() != null) {
            spec = spec.and(startedAtTo(criteria.getStartedAtTo()));
        }
        if (criteria.getFinishedAtFrom() != null) {
            spec = spec.and(finishedAtFrom(criteria.getFinishedAtFrom()));
        }
        if (criteria.getFinishedAtTo() != null) {
            spec = spec.and(finishedAtTo(criteria.getFinishedAtTo()));
        }
        if (criteria.getJobState() != null && !criteria.getJobState().isEmpty()) {
            spec = spec.and(jobStateIn(criteria.getJobState()));
        }
        if (criteria.getAssetsFoundMin() != null) {
            spec = spec.and(assetsFoundMin(criteria.getAssetsFoundMin()));
        }
        if (criteria.getAssetsFoundMax() != null) {
            spec = spec.and(assetsFoundMax(criteria.getAssetsFoundMax()));
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
