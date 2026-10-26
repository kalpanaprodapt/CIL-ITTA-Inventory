package com.uniti.cil.itta.inventory.geography.spec;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.BuildingSearchCriteria;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

/** JPA specifications backing Building search. */
public final class BuildingSpecifications {

    private BuildingSpecifications() {
    }

    public static Specification<Building> tenant(String tenantId) {
        return (root, query, cb) -> cb.equal(root.get("tenantId"), tenantId);
    }

    public static Specification<Building> freeText(String text) {
        return (root, query, cb) -> {
            String like = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("code")), like),
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("description")), like));
        };
    }

    public static Specification<Building> statusIn(List<LifecycleStatus> values) {
        return (root, query, cb) -> root.get("status").in(values);
    }

    public static Specification<Building> codeContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("code")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Building> nameContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Building> externalRefContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("externalRef")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Building> siteIdEquals(UUID value) {
        return (root, query, cb) -> cb.equal(root.get("siteId"), value);
    }

    public static Specification<Building> floorsMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("floors"), value);
    }

    public static Specification<Building> floorsMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("floors"), value);
    }

    public static Specification<Building> yearBuiltMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("yearBuilt"), value);
    }

    public static Specification<Building> yearBuiltMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("yearBuilt"), value);
    }

    public static Specification<Building> ownershipIn(List<BuildingOwnership> values) {
        return (root, query, cb) -> root.get("ownership").in(values);
    }

    public static Specification<Building> leaseExpiryFrom(LocalDate value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<LocalDate>get("leaseExpiry"), value);
    }

    public static Specification<Building> leaseExpiryTo(LocalDate value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<LocalDate>get("leaseExpiry"), value);
    }

    public static Specification<Building> securityLevelMin(Integer value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.<Integer>get("securityLevel"), value);
    }

    public static Specification<Building> securityLevelMax(Integer value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.<Integer>get("securityLevel"), value);
    }

    public static Specification<Building> lifecycleStageIn(List<BuildingLifecycleStage> values) {
        return (root, query, cb) -> root.get("lifecycleStage").in(values);
    }

    public static Specification<Building> sourceSystemContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("sourceSystem")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Building> regionContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("region")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Building> ownerContains(String value) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner")), "%" + value.toLowerCase() + "%");
    }

    public static Specification<Building> criticalityIn(List<BuildingCriticality> values) {
        return (root, query, cb) -> root.get("criticality").in(values);
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    public static Specification<Building> fromCriteria(String tenantId, BuildingSearchCriteria criteria) {
        Specification<Building> spec = tenant(tenantId);
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
        if (criteria.getSiteId() != null) {
            spec = spec.and(siteIdEquals(criteria.getSiteId()));
        }
        if (criteria.getFloorsMin() != null) {
            spec = spec.and(floorsMin(criteria.getFloorsMin()));
        }
        if (criteria.getFloorsMax() != null) {
            spec = spec.and(floorsMax(criteria.getFloorsMax()));
        }
        if (criteria.getYearBuiltMin() != null) {
            spec = spec.and(yearBuiltMin(criteria.getYearBuiltMin()));
        }
        if (criteria.getYearBuiltMax() != null) {
            spec = spec.and(yearBuiltMax(criteria.getYearBuiltMax()));
        }
        if (criteria.getOwnership() != null && !criteria.getOwnership().isEmpty()) {
            spec = spec.and(ownershipIn(criteria.getOwnership()));
        }
        if (criteria.getLeaseExpiryFrom() != null) {
            spec = spec.and(leaseExpiryFrom(criteria.getLeaseExpiryFrom()));
        }
        if (criteria.getLeaseExpiryTo() != null) {
            spec = spec.and(leaseExpiryTo(criteria.getLeaseExpiryTo()));
        }
        if (criteria.getSecurityLevelMin() != null) {
            spec = spec.and(securityLevelMin(criteria.getSecurityLevelMin()));
        }
        if (criteria.getSecurityLevelMax() != null) {
            spec = spec.and(securityLevelMax(criteria.getSecurityLevelMax()));
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
